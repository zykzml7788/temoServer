//package com.creams.temo.controller;
//
//import com.creams.temo.entity.UserEntity;
//import com.creams.temo.util.RedisUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Date;
//
//@RequestMapping("/redis")
//@RestController
//public class RedisController {
//
//    private static int ExpireTime = 60;   // redis中存储的过期时间60s
//
//    @Autowired
//    private RedisUtil redisUtil;
//
//    @RequestMapping("set1")
//    public boolean redisset(String key, String value){
//        UserEntity userEntity =new UserEntity();
//        userEntity.setId(Long.valueOf(1));
//        userEntity.setGuid(String.valueOf(1));
//        userEntity.setName("zhangsan");
//        userEntity.setAge(String.valueOf(20));
//        userEntity.setCreateTime(new Date());
//
//        //return redisUtil.set(key,userEntity,ExpireTime);
//
//        return redisUtil.set(key,value);
//    }
//
//    @RequestMapping("get2")
//    public Object redisget(String key){
//        return redisUtil.get(key);
//    }
//
//    @RequestMapping("expire3")
//    public boolean expire(String key){
//        return redisUtil.expire(key,ExpireTime);
//    }
//}
