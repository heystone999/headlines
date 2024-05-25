package com.stone.apis.article;

import com.stone.apis.article.fallback.IArticleClientFallback;
import com.stone.model.article.dtos.ArticleDto;
import com.stone.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(value = "headlines-article", fallback = IArticleClientFallback.class)
public interface IArticleClient {
    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto);
}
