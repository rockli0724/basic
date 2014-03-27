package com.lj.basic.dao;

import org.springframework.stereotype.Repository;

import com.lj.basic.model.User;


/**
 * 方法全部不要， 因为在basedao里面已经有了。这里实质上是测试basedao
 * @author Administrator
 *
 */
@Repository("userDao") //注入
public class UserDao extends BaseDao<User> implements IUserDao {
	
}
