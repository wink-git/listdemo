package com.wink.service;
import com.wink.domain.PageBean;
import com.wink.domain.User;

import java.util.Map;

/**
 * 用户管理的业务接口
 */
public interface UserService {

    /**
     * 登录方法
     * @return
     */

    public User login(User user);

    /**
     * 保存user
     * @param user
     */
    void addUser(User user);

    /**
     * 根据id删除user
     * @param id
     */

    void deleteUser(String id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     *修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 批量删除用户
     * @param uids
     */
    void delSelectedUser(String[] uids);

    /**
     * 分页条件查询
     * @param currentPage
     * @param rows
     * @param condition
     * @return
     */
    PageBean<User> findUserByPage(int currentPage, int rows, Map<String, String[]> condition);
}
