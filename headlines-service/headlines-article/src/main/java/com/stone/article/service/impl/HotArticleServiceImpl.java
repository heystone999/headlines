package com.stone.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.stone.apis.wemedia.IWemediaClient;
import com.stone.article.mapper.ApArticleMapper;
import com.stone.article.service.HotArticleService;
import com.stone.common.constants.ArticleConstants;
import com.stone.common.redis.CacheService;
import com.stone.model.article.pojos.ApArticle;
import com.stone.model.article.vos.HotArticleVo;
import com.stone.model.common.dtos.ResponseResult;
import com.stone.model.wemedia.pojos.WmChannel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class HotArticleServiceImpl implements HotArticleService {
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private IWemediaClient wemediaClient;
    @Autowired
    private CacheService cacheService;

    /**
     * 计算热点文章
     */
    @Override
    public void computeHotArticle() {
        // 查询前5天的文章数据
        Date dateParam = DateTime.now().minusDays(5).toDate();
        List<ApArticle> apArticleList = apArticleMapper.findArticleListByLast5days(dateParam);

        // 计算文章分值
        List<HotArticleVo> hotArticleVoList = computeHotArticle(apArticleList);

        // 为每个channel缓存30条分值较高的文章
        cacheTagToRedis(hotArticleVoList);
    }

    /**
     * 计算文章分值
     *
     * @param apArticleList
     * @return
     */
    private List<HotArticleVo> computeHotArticle(List<ApArticle> apArticleList) {
        List<HotArticleVo> hotArticleVoList = new ArrayList<>();
        if (apArticleList != null && apArticleList.size() > 0) {
            for (ApArticle apArticle : apArticleList) {
                HotArticleVo hot = new HotArticleVo();
                BeanUtils.copyProperties(apArticle, hot);
                Integer score = computeScore(apArticle);
                hot.setScore(score);
                hotArticleVoList.add(hot);
            }
        }
        return hotArticleVoList;
    }

    /**
     * 计算文章的具体分值
     *
     * @param apArticle
     * @return
     */
    private Integer computeScore(ApArticle apArticle) {
        Integer score = 0;
        if (apArticle.getLikes() != null) {
            score += apArticle.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }
        if (apArticle.getViews() != null) {
            score += apArticle.getViews();
        }
        if (apArticle.getComment() != null) {
            score += apArticle.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }
        if (apArticle.getCollection() != null) {
            score += apArticle.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }
        return score;
    }

    /**
     * 为每个channel缓存30条分值较高的文章
     *
     * @param hotArticleVoList
     */
    private void cacheTagToRedis(List<HotArticleVo> hotArticleVoList) {
        ResponseResult responseResult = wemediaClient.getChannels();
        if (responseResult.getCode().equals(200)) {
            String channelJson = JSON.toJSONString(responseResult.getData());
            List<WmChannel> wmChannels = JSON.parseArray(channelJson, WmChannel.class);
            // 检索出每个频道的文章
            if (wmChannels != null && wmChannels.size() > 0) {
                for (WmChannel wmChannel : wmChannels) {
                    List<HotArticleVo> hotArticleVos = hotArticleVoList.stream().filter(x -> x.getChannelId() != null && x.getChannelId().equals(wmChannel.getId())).collect(Collectors.toList());
                    // 给文章进行排序，取30条分值较高的文章存入redis  key：频道id   value：30条分值较高的文章
                    sortAndCache(hotArticleVos, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + wmChannel.getId());
                }
            }
        }
        // 设置推荐数据
        // 给文章进行排序，取30条分值较高的文章存入redis  key：频道id   value：30条分值较高的文章
        sortAndCache(hotArticleVoList, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG);
    }

    /**
     * 排序并且缓存数据
     *
     * @param hotArticleVos
     * @param key
     */
    private void sortAndCache(List<HotArticleVo> hotArticleVos, String key) {
        hotArticleVos = hotArticleVos.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
        if (hotArticleVos.size() > 30) {
            hotArticleVos = hotArticleVos.subList(0, 30);
        }
        cacheService.set(key, JSON.toJSONString(hotArticleVos));
    }
}
