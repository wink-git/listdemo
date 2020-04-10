package com.wink.dao;

import com.wink.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 用户操作Dao
 */
public interface UserDao {

    public User findUserByUsernameAndPassword(String username,String password);

    void add(User user);

    void deleteById(int i);

    User findUserById(int id);

    void update(User user);

    /**
     * 查询总记录数
     * @return
     * @param condition
     */
    int finTotalCount(Map<String, String[]> condition);

    /**
     * 分页查询每页记录
     * @param start
     * @param rows
     * @param condition
     * @return
     */
    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
