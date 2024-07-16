package com.stone.wemedia.service.impl;

import com.stone.apis.article.IArticleClient;
import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.wemedia.pojos.WmUser;
import com.stone.utils.thread.WmThreadLocalUtil;
import com.stone.wemedia.service.CommentManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentManageServiceImpl implements CommentManageService {
    @Autowired
    private IArticleClient articleClient;

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
}
