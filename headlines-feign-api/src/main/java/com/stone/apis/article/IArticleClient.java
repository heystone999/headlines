package com.stone.apis.article;

import com.stone.apis.article.fallback.IArticleClientFallback;
import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.article.dtos.ArticleDto;
import com.stone.model.comment.dtos.CommentConfigDto;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "headlines-article", fallback = IArticleClientFallback.class)
public interface IArticleClient {
    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto);

    @GetMapping("/api/v1/article/findArticleConfigByArticleId/{articleId}")
    ResponseResult findArticleConfigByArticleId(@PathVariable("articleId") Long articleId);

    @PostMapping("/api/v1/article/findNewsComments")
    PageResponseResult findNewsComments(@RequestBody ArticleCommentDto dto);

    @PostMapping("/api/v1/article/updateCommentStatus")
    ResponseResult updateCommentStatus(@RequestBody CommentConfigDto dto);
}
