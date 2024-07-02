package com.stone.model.wemedia.vo;

import com.stone.model.wemedia.pojos.WmNews;
import lombok.Data;

@Data
public class WmNewsVo extends WmNews {
    /**
     * 作者名称
     */
    private String authorName;
}
