package com.creams.temo.entity.dingding;

import com.alibaba.fastjson.JSONObject;
import com.creams.temo.util.DingdingUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MarkdownEntity {

    @ApiModelProperty(hidden = true)
    private String msgType;

    // 显示标题
    private String title;

    // 显示内容
    private String content;

    // 是否at所有人
    private Boolean isAtAll;

    // 被@人的手机号(在content里添加@人的手机号)
    private List<String> atMobiles;

    @ApiModelProperty(hidden = true)
    public String getMsgType() {
        return "markdown";
    }

    @ApiModelProperty(hidden = true)
    public String getJSONObjectString(String keysWord) {
        // markdown类型
        JSONObject markdownContent = new JSONObject();
        markdownContent.put("title", DingdingUtils.appendString(keysWord, this.getTitle()));
        markdownContent.put("text", this.getContent());

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
        json.put("markdown", markdownContent);
        json.put("at", atMobile);
        return json.toJSONString();
    }


}
