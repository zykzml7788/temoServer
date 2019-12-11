package com.creams.temo.controller;

import com.creams.temo.config.MyWebSocketHandler;
import com.creams.temo.service.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.websocket.Session;
import java.io.IOException;

@Controller
@RequestMapping("/checkcenter")
public class CheckCenterController {
    @Autowired
    private WebSocketServer webSocketServer;



    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav=new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }


    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public String pushToWeb(@PathVariable(required = false) String cid,String message) throws IOException {
        try {

            WebSocketServer.sendInfo(message,cid);

        } catch (IOException e) {
            e.printStackTrace();
            return (cid+"#"+e.getMessage());
        }
        return cid;
    }
}
