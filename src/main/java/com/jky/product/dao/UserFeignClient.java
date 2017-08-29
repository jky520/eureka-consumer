package com.jky.product.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jky.product.config.FeignConfiguration;
import com.jky.product.dao.impl.FeignClientFallback;
import com.jky.product.entity.User;

import feign.Param;
import feign.RequestLine;

/*
 * feign接口
 */
//@FeignClient(name = "PROVIDER-SERVICE", url = "http://localhost:8888/") // 这个做法的需要等待PROVIDER-SERVICE注册成功后才生效
//@FeignClient(name = "PROVIDER-SERVICE", configuration = FeignConfiguration.class)// configuration指定配置类
@FeignClient(name = "PROVIDER-SERVICE", fallback = FeignClientFallback.class)
public interface UserFeignClient {

	/**
	 * 
	 * /soa/user/{id} 是服务端的路径
	 * 使用feign自带的注解@RequestLine
	 * @param id
	 * @return 用户信息
	 */
	@RequestMapping(value = "/soa/user/{id}", method = RequestMethod.GET) // 这是springMVC的注解
	public User findById(@PathVariable("id") int id); //这上面是@PathVariable
	//====================================
	//@RequestLine("GET /soa/user/{id}")
	//public User findById(@Param("id") int id); // //这里是@Param不要弄错了
}
