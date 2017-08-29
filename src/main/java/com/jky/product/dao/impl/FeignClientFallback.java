package com.jky.product.dao.impl;

import org.springframework.stereotype.Component;

import com.jky.product.dao.UserFeignClient;
import com.jky.product.entity.User;

/**
 * 回退的类FeignClientFallback需要实现Feign Client接口
 * FeignClientFallback 也可以是public class，没有区别
 * @author @DT人 2017年7月19日 下午7:36:01
 */
@Component
public class FeignClientFallback implements UserFeignClient {

	@Override
	public User findById(int id) {
		User user = new User();
		user.setId(-1);
		user.setAge(0);
		user.setUsername("默认用户");
		user.setPassword("123456");
		return user;
	}

}
