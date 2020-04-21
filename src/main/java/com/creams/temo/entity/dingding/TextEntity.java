package com.creams.temo.entity.dingding;

import com.alibaba.fastjson.JSONObject;
import com.creams.temo.util.DingdingUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class TextEntity {
    @ApiModelProperty(hidden = true)
    private String msgType;

    @ApiModelProperty("发送内容")
    private String content;

    @ApiModelProperty("是否艾特所有人")
    private Boolean isAtAll;

    @ApiModelProperty("被@人的手机号(在text里添加@人的手机号)")
    private List<String> atMobiles;

    @ApiModelProperty(hidden = true)
    public String getMsgType() {
        return "text";
    }

    @ApiModelProperty(hidden = true)
    public String getJSONObjectString(String keysWord) {
        // text类型
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", DingdingUtils.appendString(keysWord, this.getContent()));
        // at some body
        JSONObject atMobile = new JSONObject();
        if(this.getAtMobiles().size() > 0){
            List<String> mobiles = new ArrayList<String>();
            for (int i=0;i<this.getAtMobiles().size();i++){
                mobiles.add(this.getAtMobiles().get(i));
            }
            if(mobiles.size()>0){
                atMobile.put("atMobiles", mobiles);
            }
            atMobile.put("isAtAll", this.getIsAtAll());
        }

        JSONObject json = new JSONObject();
        json.put("msgtype", this.getMsgType());
        json.put("text", jsonObject);
        json.put("at", atMobile);
        // System.out.println(json.toJSONString());
        return json.toJSONString();
    }

}
