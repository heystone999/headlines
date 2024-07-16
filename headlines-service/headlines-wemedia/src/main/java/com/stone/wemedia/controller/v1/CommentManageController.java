package com.stone.wemedia.controller.v1;

import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.wemedia.service.CommentManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment/manage")
public class CommentManageController {
    @Autowired
    private CommentManageService commentManageService;

    @PostMapping("/find_news_comments")
    public PageResponseResult findNewsComments(@RequestBody ArticleCommentDto dto) {
        return commentManageService.findNewsComments(dto);
    }
}
