package com.creams.temo.util;

import com.alibaba.fastjson.JSONObject;
import com.creams.temo.entity.dingding.TextEntity;
import okhttp3.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DingdingUtils {
    private static OkHttpClient client = new OkHttpClient();
    /**
     * 发送钉钉消息
     * @param jsonString 消息内容
     * @param webhook 钉钉自定义机器人webhook
     * @return
     */
    public static  boolean sendToDingding(String jsonString, String webhook) {
        try{
            String type = "application/json; charset=utf-8";
            RequestBody body = RequestBody.create(MediaType.parse(type), jsonString);
            Request.Builder builder = new Request.Builder().url(webhook);
            builder.addHeader("Content-Type", type).post(body);

            Request request = builder.build();
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(String.format("send ding message:%s", string));
            JSONObject res = JSONObject.parseObject(string);
            return res.getIntValue("errcode") == 0;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加钉钉授权关键字
     * @param str
     * @return
     */
    public static String appendString(String keyword, String str){
        StringBuffer stringBuffer = new StringBuffer(keyword);
        return String.valueOf(stringBuffer.append(str));
    }

    public static void main(String[] args){
        String  WEBHOOK = "https://oapi.dingtalk.com/robot/send?access_token=9a26f57b418834f320ed8cc5036dd8e15ba5e8f9a2985cee87ad8bc45bd2ff9d";

        TextEntity textEntity = new TextEntity();

        String a = "我就是我, 是不一样的烟火@17826826147-teemo";
        textEntity.setContent(a);
        textEntity.setIsAtAll(false);
        List<String> list = new ArrayList<>();
        list.add("17826826147");
        textEntity.setAtMobiles(list);

        DingdingUtils.sendToDingding(textEntity.getJSONObjectString("teemo-"),WEBHOOK);
        System.out.println( DingdingUtils.appendString("teemo-", "test"));

   }
}
