package com.stone.comment.service.impl;

import com.alibaba.fastjson.JSON;
import com.stone.apis.article.IArticleClient;
import com.stone.apis.user.IUserClient;
import com.stone.comment.pojos.ApComment;
import com.stone.comment.service.CommentService;
import com.stone.model.article.pojos.ApArticleConfig;
import com.stone.model.comment.dtos.CommentSaveDto;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.common.enums.AppHttpCodeEnum;
import com.stone.model.user.pojos.ApUser;
import com.stone.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private IArticleClient articleClient;
    @Autowired
    private IUserClient userClient;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存评论
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
        if (dto == null || StringUtils.isBlank(dto.getContent()) || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        // 校验文章是否可以评论
        if (!checkParam(dto.getArticleId())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "该文章评论权限已关闭");
        }

        if (dto.getContent().length() > 140) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "评论内容不能超过140字");
        }

        // 判断是否登录
        ApUser user = AppThreadLocalUtil.getUser();
        if (user == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        // 保存
        ApUser dbUser = userClient.findUserById(user.getId());
        if (dbUser == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "当前登录信息有误");
        }
        ApComment apComment = new ApComment();
        apComment.setAuthorId(user.getId());
        apComment.setContent(dto.getContent());
        apComment.setCreatedTime(new Date());
        apComment.setEntryId(dto.getArticleId());
        apComment.setImage(dbUser.getImage());
        apComment.setAuthorName(dbUser.getName());
        apComment.setLikes(0);
        apComment.setReply(0);
        apComment.setType((short) 0);
        apComment.setFlag((short) 0);
        mongoTemplate.save(apComment);

        // TODO: 发送消息给Kafka进行聚合
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * 校验文章是否可以评论
     *
     * @param articleId
     * @return
     */
    private boolean checkParam(Long articleId) {
        ResponseResult configResult = articleClient.findArticleConfigByArticleId(articleId);
        if (!configResult.getCode().equals(200) || configResult.getData() == null) {
            return false;
        }

        ApArticleConfig apArticleConfig = JSON.parseObject(JSON.toJSONString(configResult.getData()), ApArticleConfig.class);
        if (apArticleConfig == null || !apArticleConfig.getIsComment()) {
            return false;
        }
        return true;
    }

}