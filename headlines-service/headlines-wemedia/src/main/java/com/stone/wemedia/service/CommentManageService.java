package com.stone.wemedia.service;

import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.comment.dtos.CommentConfigDto;
import com.stone.model.comment.dtos.CommentLikeDto;
import com.stone.model.comment.dtos.CommentManageDto;
import com.stone.model.comment.dtos.CommentRepaySaveDto;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.common.dtos.ResponseResult;

public interface CommentManageService {
    /**
     * 查看文章评论列表
     *
     * @param dto
     * @return
     */
    PageResponseResult findNewsComments(ArticleCommentDto dto);

    /**
     * 打开或关闭评论
     *
     * @param dto
     * @return
     */
    ResponseResult updateCommentStatus(CommentConfigDto dto);

    /**
     * 查询评论列表
     *
     * @param dto
     * @return
     */
    ResponseResult list(CommentManageDto dto);

    /**
     * 回复评论
     *
     * @param dto
     * @return
     */
    ResponseResult saveCommentRepay(CommentRepaySaveDto dto);

    /**
     * like评论
     *
     * @param dto
     * @return
     */
    ResponseResult like(CommentLikeDto dto);
}
