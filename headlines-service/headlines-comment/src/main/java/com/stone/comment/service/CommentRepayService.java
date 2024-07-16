package com.stone.comment.service;

import com.stone.model.comment.dtos.CommentRepaySaveDto;
import com.stone.model.common.dtos.ResponseResult;

public interface CommentRepayService {
    /**
     * 保存回复
     *
     * @param dto
     * @return
     */
    ResponseResult saveCommentRepay(CommentRepaySaveDto dto);
}
