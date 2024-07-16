package com.stone.article.feign;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.stone.apis.article.IArticleClient;
import com.stone.article.service.ApArticleConfigService;
import com.stone.article.service.ApArticleService;
import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.article.dtos.ArticleDto;
import com.stone.model.article.pojos.ApArticleConfig;
import com.stone.model.common.dtos.PageResponseResult;
import com.stone.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleClient implements IArticleClient {
    @Autowired
    private ApArticleService apArticleService;
    @Autowired
    private ApArticleConfigService apArticleConfigService;

    @PostMapping("/api/v1/article/save")
    @Override
    public ResponseResult saveArticle(@RequestBody ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }

    @GetMapping("/api/v1/article/findArticleConfigByArticleId/{articleId}")
    @Override
    public ResponseResult findArticleConfigByArticleId(@PathVariable("articleId") Long articleId) {
        ApArticleConfig apArticleConfig = apArticleConfigService.getOne(Wrappers.<ApArticleConfig>lambdaQuery().eq(ApArticleConfig::getArticleId, articleId));
        return ResponseResult.okResult(apArticleConfig);
    }

    @PostMapping("/api/v1/article/findNewsComments")
    @Override
    public PageResponseResult findNewsComments(ArticleCommentDto dto) {
        return apArticleService.findNewsComments(dto);
    }
}
