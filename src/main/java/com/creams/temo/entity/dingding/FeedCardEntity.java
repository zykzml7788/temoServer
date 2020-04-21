package com.creams.temo.entity.dingding;

import com.alibaba.fastjson.JSONObject;
import com.creams.temo.util.DingdingUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FeedCardEntity {
    @ApiModelProperty(hidden = true)
    private String msgType;

    // links
    private List<LinksEntity> links;

    @ApiModelProperty(hidden = true)
    public String getMsgType() {
        return "feedCard";
    }

    @ApiModelProperty(hidden = true)
    public String getJSONObjectString(String keysWord) {

        // text类型
        JSONObject feedCardContent = new JSONObject();
        List<LinksEntity> links = new ArrayList<LinksEntity>();
        for (int i=0;i<this.getLinks().size();i++){
            // 添加默认的keysWord
            this.getLinks().get(i).setTitle(DingdingUtils.appendString(keysWord, this.getLinks().get(i).getTitle()));
            links.add(this.getLinks().get(i));
        }
        if(this.getLinks().size()>0){
            feedCardContent.put("links", links);
        }

        JSONObject json = new JSONObject();
        json.put("msgtype", this.getMsgType());
        json.put("feedCard", feedCardContent);

        return json.toJSONString();
    }

}
