package com.stone.wemedia.service;

import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.common.dtos.PageResponseResult;

public interface CommentManageService {
    /**
     * 查看文章评论列表
     *
     * @param dto
     * @return
     */
    PageResponseResult findNewsComments(ArticleCommentDto dto);
}
