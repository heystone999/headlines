package com.stone.comment.service;

import com.stone.model.comment.dtos.CommentDto;
import com.stone.model.comment.dtos.CommentLikeDto;
import com.stone.model.comment.dtos.CommentSaveDto;
import com.stone.model.common.dtos.ResponseResult;

public interface CommentService {
    /**
     * 保存评论
     *
     * @param dto
     * @return
     */
    ResponseResult saveComment(CommentSaveDto dto);

    /**
     * 点赞
     *
     * @param dto
     * @return
     */
    ResponseResult like(CommentLikeDto dto);

    /**
     * 加载评论列表
     *
     * @param dto
     * @return
     */
    ResponseResult findByArticleId(CommentDto dto);
}
