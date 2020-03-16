package com.creams.temo.util;

import com.creams.temo.entity.sys.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class ShiroUtils {
//    /**  加密算法 */
//    public final static String hashAlgorithmName = "SHA-256";
//    /**  哈希次数 */
//    public final static int hashIterations = 2;

    /**
     * @param password 需要加密的密码
     * @param salt hash的盐，需要加进一起加密的数据
     * @return
     */
    public static String sha256(String password, String salt) {
        return new Sha256Hash(password, salt).toString();

    }
    // 获取一个测试账号 admin
    public static void main(String[] args) {
        // 3743a4c09a17e6f2829febd09ca54e627810001cf255ddcae9dabd288a949c4a
        System.out.println("admin" + sha256("123456","admin")) ;
        System.out.println("jelly" + sha256("123456", "jelly"));
    }
    /**
     * 获取会话
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
    /**
     * Subject：主体，代表了当前“用户”
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }
    public static UserEntity getUserEntity() {
        return (UserEntity)SecurityUtils.getSubject().getPrincipal();
    }
    public static String getUserId() {
        return getUserEntity().getUserId();
    }
    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }
    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }
    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }
}
