package com.stone.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.stone.model.article.dtos.ArticleCommentDto;
import com.stone.model.article.dtos.ArticleHomeDto;
import com.stone.model.article.pojos.ApArticle;
import com.stone.model.article.vos.ArticleCommnetVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {
    /**
     * 加载文章列表
     *
     * @param dto
     * @param type 1 加载更多 2 加载最新
     * @return
     */
    public List<ApArticle> loadArticleList(ArticleHomeDto dto, Short type);

    public List<ApArticle> findArticleListByLast5days(@Param("dayParam") Date dayParam);

    List<ArticleCommnetVo> findNewsComments(@Param("dto") ArticleCommentDto dto);

    int findNewsCommentsCount(@Param("dto") ArticleCommentDto dto);

}
