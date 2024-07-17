package com.stone.wemedia.service.impl;

import com.stone.apis.article.IArticleClient;
import com.stone.apis.user.IUserClient;
import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.comment.dtos.CommentConfigDto;
import com.stone.model.comment.dtos.CommentLikeDto;
import com.stone.model.comment.dtos.CommentManageDto;
import com.stone.model.comment.dtos.CommentRepaySaveDto;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.common.enums.AppHttpCodeEnum;
import com.stone.model.user.pojos.ApUser;
import com.stone.model.wemedia.pojos.WmUser;
import com.stone.utils.thread.WmThreadLocalUtil;
import com.stone.wemedia.mapper.WmUserMapper;
import com.stone.wemedia.pojos.*;
import com.stone.wemedia.service.CommentManageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentManageServiceImpl implements CommentManageService {
    @Autowired
    private IArticleClient articleClient;
    @Autowired
    private WmUserMapper wmUserMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private IUserClient userClient;

    /**
     * 查看文章评论列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult findNewsComments(ArticleCommentDto dto) {
        WmUser user = WmThreadLocalUtil.getUser();
        dto.setWmUserId(user.getId());
        return articleClient.findNewsComments(dto);
    }

    /**
     * 打开或关闭评论
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult updateCommentStatus(CommentConfigDto dto) {
        WmUser wmUser = WmThreadLocalUtil.getUser();

        // app端用户id
        WmUser dbUser = wmUserMapper.selectById(wmUser.getId());
        Integer apUserId = dbUser.getApUserId();

        if (dto.getOperation() == 0) { // 关闭评论
            // 清空该文章的所有评论
            List<ApComment> apCommentList = mongoTemplate.find(Query.query(Criteria.where("entryId").is(dto.getArticleId()).and("authorId").is(apUserId)), ApComment.class);
            for (ApComment apComment : apCommentList) {
                List<ApCommentRepay> commentRepayList = mongoTemplate.find(Query.query(Criteria.where("commentId").is(apComment.getId()).and("authorId").is(apUserId)), ApCommentRepay.class);
                List<String> commentRepayIdList = commentRepayList.stream().map(ApCommentRepay::getId).distinct().collect(Collectors.toList());
                // 删除所有的评论回复like数据
                mongoTemplate.remove(Query.query(Criteria.where("commentRepayId").in(commentRepayIdList).and("authorId").is(apUserId)), ApCommentRepayLike.class);
                // 删除该评论的所有的回复内容
                mongoTemplate.remove(Query.query(Criteria.where("commentId").is(apComment.getId()).and("authorId").is(apUserId)), ApCommentRepay.class);
                // 删除评论的like
                mongoTemplate.remove(Query.query(Criteria.where("commentId").is(apComment.getId()).and("authorId").is(apUserId)), ApCommentLike.class);
            }
            // 删除评论
            mongoTemplate.remove(Query.query(Criteria.where("entryId").is(dto.getArticleId()).and("authorId").is(apUserId)), ApComment.class);
        }

        // 修改app文章的config配置
        return articleClient.updateCommentStatus(dto);
    }

    /**
     * 查询评论列表
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult list(CommentManageDto dto) {
        List<CommentRepayListVo> commentRepayListVoList = new ArrayList<>();
        Query query = Query.query(Criteria.where("entryId").is(dto.getArticleId()));
        // 这里减1是因为mongoDB skip(0)
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getSize());
        query.with(pageable);
        query.with(Sort.by(Sort.Direction.DESC, "createdTime"));

        // query
        List<ApComment> apCommentList = mongoTemplate.find(query, ApComment.class);
        for (ApComment apComment : apCommentList) {
            CommentRepayListVo vo = new CommentRepayListVo();
            vo.setApComments(apComment);
            Query query2 = Query.query(Criteria.where("commentId").is(apComment.getId()));
            query2.with(Sort.by(Sort.Direction.DESC, "createdTime"));
            List<ApCommentRepay> apCommentRepays = mongoTemplate.find(query2, ApCommentRepay.class);
            vo.setApCommentRepays(apCommentRepays);

            commentRepayListVoList.add(vo);
        }
        return ResponseResult.okResult(commentRepayListVoList);
    }

    /**
     * 回复评论
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveCommentRepay(CommentRepaySaveDto dto) {
        if (dto == null || StringUtils.isBlank(dto.getContent()) || dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        if (dto.getContent().length() > 140) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "评论内容不能超过140字");
        }

        WmUser wmUser = WmThreadLocalUtil.getUser();
        WmUser dbUser = wmUserMapper.selectById(wmUser.getId());
        if (dbUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        // 获取app端用户信息
        ApUser apUser = userClient.findUserById(dbUser.getApUserId());
        // 保存评论
        ApCommentRepay apCommentRepay = new ApCommentRepay();
        apCommentRepay.setAuthorId(apUser.getId());
        apCommentRepay.setAuthorName(apUser.getName());
        apCommentRepay.setContent(dto.getContent());
        apCommentRepay.setCreatedTime(new Date());
        apCommentRepay.setCommentId(dto.getCommentId());

        apCommentRepay.setUpdatedTime(new Date());
        apCommentRepay.setLikes(0);
        mongoTemplate.save(apCommentRepay);

        // 更新回复数量
        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);
        apComment.setReply(apComment.getReply() + 1);
        mongoTemplate.save(apComment);

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * like评论
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult like(CommentLikeDto dto) {
        if (dto == null || dto.getCommentId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApComment apComment = mongoTemplate.findById(dto.getCommentId(), ApComment.class);

        WmUser wmUser = WmThreadLocalUtil.getUser();
        WmUser dbUser = wmUserMapper.selectById(wmUser.getId());
        if (dbUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        // 获取app端用户信息
        ApUser apUser = userClient.findUserById(dbUser.getApUserId());

        // like
        if (apComment != null && dto.getOperation() == 0) {
            // 更新评论like数量
            apComment.setLikes(apComment.getLikes() + 1);
            mongoTemplate.save(apComment);

            // 保存评论like数据
            ApCommentLike apCommentLike = new ApCommentLike();
            apCommentLike.setCommentId(apComment.getId());
            apCommentLike.setAuthorId(apUser.getId());
            mongoTemplate.save(apCommentLike);
        } else { // 取消点赞
            // 更新评论like数量
            int tmp = apComment.getLikes() - 1;
            tmp = tmp < 1 ? 0 : tmp;
            apComment.setLikes(tmp);
            mongoTemplate.save(apComment);

            // 删除评论like
            Query query = Query.query(Criteria.where("commentId").is(apComment.getId()).and("authorId").is(apUser.getId()));
            mongoTemplate.remove(query, ApCommentLike.class);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("likes", apComment.getLikes());
        return ResponseResult.okResult(result);
    }
}
