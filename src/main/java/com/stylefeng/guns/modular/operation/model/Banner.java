package com.stylefeng.guns.modular.operation.model;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.core.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 广告管理实体类
 *
 * @author 
 * @Date 2018-03-15 17:58:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("banner")
public class Banner extends BaseModel<Banner> {
    /**
    * 
    */
    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;

    /**
    * 名称
    */
    @TableField("BANNER_NAME")
    private String bannerName;
    /**
    * 名称
    */
    @TableField("BANNER_NAME_ID")
    private String bannerNameID;
    /**
    * 名称
    */
    @TableField("BANNER_NAME_TC")
    private String bannerNameTC;
    /**
    * 名称
    */
    @TableField("BANNER_NAME_MY")
    private String bannerNameMY;
    /**
    * 名称
    */
    @TableField("BANNER_NAME_VI")
    private String bannerNameVI;

    /**
    * 名称（英文）
    */
    @TableField("banner_name_en")
    private String bannerNameEN;

    /**
    * 图片
    */
    @TableField("BANNER_IMGURL")
    private String bannerImgurl;
    /**
    * 图片
    */
    @TableField("BANNER_IMGURL_ID")
    private String bannerImgurlID;
    /**
    * 图片
    */
    @TableField("BANNER_IMGURL_MY")
    private String bannerImgurlMY;
    /**
    * 图片
    */
    @TableField("BANNER_IMGURL_VI")
    private String bannerImgurlVI;

    /**
    * 图片
    */
    @TableField("BANNER_IMGURL_EN")
    private String bannerImgurlEN;
    /**
    * 图片
    */
    @TableField("BANNER_IMGURL_TC")
    private String bannerImgurlTC;

    /**
    * 内容
    */
    @TableField("CONTENT")
    private String content;
    /**
    * 内容
    */
    @TableField("CONTENT_ID")
    private String contentID;
    /**
    * 内容
    */
    @TableField("CONTENT_MY")
    private String contentMY;

    /**
    * 内容
    */
    @TableField("CONTENT_EN")
    private String contentEN;

    /**
    * 内容
    */
    @TableField("CONTENT_VI")
    private String contentVI;

    /**
    * 内容
    */
    @TableField("CONTENT_TC")
    private String contentTC;

    /**
    * 
    */
    @TableField("BANNER_ORDER")
    private Integer bannerOrder;

    /**
    * 是否跳转
    */
    @TableField("IS_GO")
    private Integer isGo;

    /**
    * 状态
    */
    @TableField("STATUS")
    private Integer status;

    /**
    * 类型
    */
    @TableField("BANNER_PLACE")
    private Integer bannerPlace;

    /**
    * 创建时间
    */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
    * 链接
    */
    @TableField("URL")
    private String url;

}