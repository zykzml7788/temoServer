package com.creams.temo.service;

import com.creams.temo.entity.dingding.DingdingEntity;
import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.mapper.DingdingMapper;
import com.creams.temo.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DingdingService {

    @Autowired
    DingdingMapper dingdingMapper;

    /**
     * 查询所有钉钉机器人
     * @return
     */
    public List<DingdingEntity> queryAllDingding(){
        return dingdingMapper.queryAllDingding();
    }

    /**
     * 根据描述模糊查询钉钉机器人
     * @param desc
     * @return
     */
    public List<DingdingEntity> queryDingding(String desc){
        return dingdingMapper.queryDingding(desc);
    }

    /**
     * 查询单个机器人信息
     * @param descId
     * @return
     */
    public DingdingEntity queryDingdingBydescId(String descId){
        return dingdingMapper.queryDingdingBydescId(descId);
    }

    /**
     * 修改钉钉机器人
     * @param dingdingEntity
     * @return
     */
    @Transactional
    public boolean updateDingding(DingdingEntity dingdingEntity){
        if (StringUtils.isEmpty(dingdingMapper.queryDingdingBydescId(dingdingEntity.getDescId()))){
            return false;
        }else {
            return dingdingMapper.updateDingding(dingdingEntity);
        }
    }

    /**
     * 添加钉钉机器人
     * @param
     * @return
     */

    @Transactional
    public boolean addDingding(DingdingEntity dingdingEntity){
        dingdingEntity.setDescId(StringUtil.uuid());
        UserEntity user = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        dingdingEntity.setCreater(user.getUserName());
        return dingdingMapper.addDingding(dingdingEntity);
    }

    /**
     * 删除钉钉机器人
     * @param descId
     * @return
     */
    @Transactional
    public boolean deleteDingding(String descId){
        if (StringUtils.isEmpty(dingdingMapper.queryDingdingBydescId(descId))){
            return false;
        }else {
            return dingdingMapper.deleteDingding(descId);
        }
    }
}
