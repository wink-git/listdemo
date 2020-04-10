package com.wink.service.impl;

import com.wink.dao.UserDao;
import com.wink.dao.impl.UserDaoImpl;
import com.wink.domain.PageBean;
import com.wink.domain.User;
import com.wink.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();


    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.deleteById(Integer.parseInt(id));
    }

    @Override
    public User findUserById(String id) {
        return dao.findUserById(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public void delSelectedUser(String[] uids) {
        if(uids != null && uids.length >0){
            //1.遍历数组
            for(String id: uids){
                //2.调用dao删除
                dao.deleteById(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(int currentPage, int rows, Map<String, String[]> condition) {

        //处理分页图标第一页空指针问题
        if(currentPage <= 0){
            currentPage = 1;
        }
        //1.创建空的PageBean对象
        PageBean<User> pb = new PageBean<User>();

        //2.设置参数
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);

        //3.调用dao查询总记录数
        int totalCount = dao.finTotalCount(condition);
        pb.setTotalCount(totalCount);

        //4.调用dao查询List集合,计算开始的记录索引
        int start = (currentPage-1)*rows;
        List<User> list = dao.findByPage(start,rows,condition);
        pb.setList(list);

        //5.计算总页码
        int totalPage = totalCount % rows == 0 ? (totalCount/rows) :(totalCount/rows + 1);
        pb.setTotalPage(totalPage);

        return pb;

    }

}
