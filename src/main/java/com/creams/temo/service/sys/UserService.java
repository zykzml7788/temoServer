package com.creams.temo.service.sys;

import com.creams.temo.entity.sys.UserEntity;
import com.creams.temo.entity.sys.request.UserRequest;
import com.creams.temo.mapper.sys.UserMapper;
import com.creams.temo.util.ShiroUtils;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 添加用户
     * @param user
     */
    public boolean addUser(UserRequest user){
        if (StringUtils.isEmpty(user)){
            return false;
        }else {
            String userId = StringUtil.uuid();
            user.setUserId(userId);
            userMapper.addUser(user);
        }
        return true;
    }

    /**
     * 分页查询所有用户
     * @return
     */
    public PageInfo<UserEntity> queryUsers(Integer page){
        PageHelper.startPage(page, 10);
        List<UserEntity> users = userMapper.queryUsers();
        return new PageInfo<>(users);
    }

    /**
     *  根据用户id查询用户
     * @return
     */
    public UserEntity queryUsersById(String userId){
        return userMapper.queryUserById(userId);
    }


    /**
     *  根据用户name查询用户
     * @return
     */
    public UserEntity queryUsersByName(String userName){
        return userMapper.queryUserByName(userName);
    }

    /**
     * 修改用户密码
     * @param userId
     * @return
     */
    public boolean updateUserPwd(String userId, String pwd){
        if (userId.isEmpty() || "".equals(userId)){
            return false;
        }else {
            String userName = userMapper.queryUserById(userId).getUserName();
            if (userName.isEmpty() || "".equals(userName)){
                return false;
            }else {
                String ShaPwd = ShiroUtils.sha256(pwd, userName);
                return userMapper.updateUserPwd(userId, ShaPwd);
            }
        }
    }



    /**
     * 设置用户状态
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean setUserStatus(String userId, Integer status){
        if (userId.isEmpty() || "".equals(userId)){
            return false;
        }else {
            userMapper.setUserStatus(userId, status);
        }
        return true;
    }

    /**
     * 修改用户
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserRequest user){
        if (StringUtils.isEmpty(user)) {
            return false;
        }else {
            return userMapper.updateUser(user);
        }
    }
}
