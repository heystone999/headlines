package com.stone.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.stone.model.article.dtos.ArticleDto;
import com.stone.model.article.dtos.ArticleHomeDto;
import com.stone.model.article.dtos.ArticleInfoDto;
import com.stone.model.article.pojos.ApArticle;
import com.stone.model.common.dtos.ResponseResult;

public interface ApArticleService extends IService<ApArticle> {
    /**
     * 加载文章列表
     *
     * @param dto
     * @param type
     * @return
     */

    public ResponseResult load(ArticleHomeDto dto, Short type);

    /**
     * 保存app端相关文章
     *
     * @param dto
     * @return
     */
    public ResponseResult saveArticle(ArticleDto dto);

    /**
     * 加载文章详情 数据回显
     *
     * @param dto
     * @return
     */
    ResponseResult loadArticleBehavior(ArticleInfoDto dto);
}
