package com.creams.temo.controller;

import com.creams.temo.entity.JsonResult;
import com.creams.temo.entity.dingding.*;
import com.creams.temo.service.DingdingService;
import com.creams.temo.util.DingdingUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Api("DingdingController API")
@RequestMapping(value = "/Dingding")
public class DingdingController {


    @Autowired
    DingdingService dingdingService;

    @ApiOperation("查询所有钉钉机器人")
    @GetMapping(value = "/list")
    public JsonResult queryAllDingding() {

        try {
            return new JsonResult("操作成功", 200,  dingdingService.queryAllDingding(), true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("根据id查询钉钉机器人")
    @GetMapping(value = "/{DescId}")
    public JsonResult queryDingdingByDescId(@PathVariable(value = "DescId") String DescId) {

        try {
            return new JsonResult("操作成功", 200,  dingdingService.queryDingdingBydescId(DescId), true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("添加钉钉机器人")
    @PostMapping(value = "/")
    public JsonResult addDingding(@RequestBody DingdingEntity dingdingEntity) {

        try {
            dingdingService.addDingding(dingdingEntity);
            return new JsonResult("操作成功", 200, null , true);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("修改钉钉机器人")
    @PutMapping(value = "/update/")
    public JsonResult updateDingding(@RequestBody DingdingEntity dingdingEntity) {

        try {
            if (dingdingService.updateDingding(dingdingEntity)){
                return new JsonResult("操作成功", 200,  null, true);
            }else {
                return new JsonResult("该机器人不存在", 500, null, false);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }

    @ApiOperation("删除钉钉机器人")
    @PutMapping(value = "/{descId}")
    public JsonResult deleteDingding(@PathVariable("descId") String descId) {

        try {
            if (dingdingService.deleteDingding(descId)){
                return new JsonResult("操作成功", 200, null, true);
            }else {
                return new JsonResult("操作失败", 500, null, false);
            }

        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("操作失败", 500, null, false);
        }
    }



    @PostMapping(value = "/sendText/{descId}")
    public JsonResult sendTextMessage(@PathVariable("descId")String descId, @RequestBody TextEntity text) {

        try {
            if (StringUtils.isEmpty(text)){
                return new JsonResult("发送失败", 500, null, false);
            }else {
                String keysWord = dingdingService.queryDingdingBydescId(descId).getKeysWord();
                String webhook = dingdingService.queryDingdingBydescId(descId).getWebhook();
                System.out.println(webhook);
                DingdingUtils.sendToDingding(text.getJSONObjectString(keysWord),webhook);
                return new JsonResult("发送成功", 200, null, true);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("发送失败", 500, null, false);
        }
    }

    @PostMapping(value = "/sendLink/{descId}")
    public JsonResult sendLinkMessage(@PathVariable("descId")String descId, @RequestBody LinkEntity linkEntity) {
        try {
            if (StringUtils.isEmpty(linkEntity)){
                return new JsonResult("发送失败", 500, null, false);
            }else {
                String keysWord = dingdingService.queryDingdingBydescId(descId).getKeysWord();
                String webhook = dingdingService.queryDingdingBydescId(descId).getWebhook();
                DingdingUtils.sendToDingding(linkEntity.getJSONObjectString(keysWord), webhook);
                return new JsonResult("发送成功", 200, null, true);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("发送失败", 500, null, false);
        }
    }


    @PostMapping(value = "/sendMarkdown/{descId}")
    public JsonResult sendMarkdownMessage(@PathVariable("descId")String descId,@RequestBody MarkdownEntity markdownEntity) {
        try {
            if (StringUtils.isEmpty(markdownEntity)){
                return new JsonResult("发送失败", 500, null, false);
            }else {
                String keysWord = dingdingService.queryDingdingBydescId(descId).getKeysWord();
                String webhook = dingdingService.queryDingdingBydescId(descId).getWebhook();
                DingdingUtils.sendToDingding(markdownEntity.getJSONObjectString(keysWord), webhook);
                return new JsonResult("发送成功", 200, null, true);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("发送失败", 500, null, false);
        }

    }


    /**
     * @param feedCardEntity
     * @return
     */
    @PostMapping(value = "/sendFeedCard/{descId}")
    public JsonResult sendFeedCardMessage(@PathVariable("descId")String descId,@RequestBody FeedCardEntity feedCardEntity) {
        try {
            if (StringUtils.isEmpty(feedCardEntity)){
                return new JsonResult("发送失败", 500, null, false);
            }else {
                String keysWord = dingdingService.queryDingdingBydescId(descId).getKeysWord();
                String webhook = dingdingService.queryDingdingBydescId(descId).getWebhook();
                DingdingUtils.sendToDingding(feedCardEntity.getJSONObjectString(keysWord), webhook);
                return new JsonResult("发送成功", 200, null, true);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("发送失败", 500, null, false);
        }

    }
}
