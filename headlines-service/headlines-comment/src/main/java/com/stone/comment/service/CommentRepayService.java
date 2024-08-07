package com.stone.comment.service;

import com.stone.model.comment.dtos.CommentRepayDto;
import com.stone.model.comment.dtos.CommentRepayLikeDto;
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

    /**
     * like评论回复
     *
     * @param dto
     * @return
     */
    ResponseResult saveCommentRepayLike(CommentRepayLikeDto dto);

    /**
     * 查看更多回复内容
     *
     * @param dto
     * @return
     */
    ResponseResult loadCommentRepay(CommentRepayDto dto);
}
