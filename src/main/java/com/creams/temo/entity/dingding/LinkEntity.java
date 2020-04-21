package com.creams.temo.entity.dingding;

import com.alibaba.fastjson.JSONObject;
import com.creams.temo.util.DingdingUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LinkEntity {

    @ApiModelProperty(hidden = true)
    private String msgType;

    // 显示标题
    private String title;

    // 显示内容
    private String content;

    // icon url
    private String picUrl;

    // 内容对链接
    private String messageUrl;

    @ApiModelProperty(hidden = true)
    public String getMsgType() {
        return "link";
    }

    @ApiModelProperty(hidden = true)
    public String getJSONObjectString(String keysWord) {
        // text类型
        JSONObject linkContent = new JSONObject();
        linkContent.put("title", DingdingUtils.appendString(keysWord, this.title));
        linkContent.put("text", this.getContent());
        linkContent.put("picUrl", this.getPicUrl());
        linkContent.put("messageUrl", this.getMessageUrl());

        JSONObject json = new JSONObject();
        json.put("msgtype", this.getMsgType());
        json.put("link", linkContent);
        return json.toJSONString();
    }

}