package com.stone.model.article.dtos;

import com.stone.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class ArticleDto extends ApArticle {
    private String content;
}
