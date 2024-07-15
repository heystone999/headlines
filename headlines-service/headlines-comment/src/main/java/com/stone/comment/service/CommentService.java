package com.stone.comment.service;

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
}
