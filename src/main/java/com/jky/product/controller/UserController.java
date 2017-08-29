package com.jky.product.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jky.product.dao.UserFeignClient;
import com.jky.product.entity.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private LoadBalancerClient loadbalancerClient;
	
	/**
	 * 让findById方法具有容差能力
	 * @param id
	 * @return
	 */
	/*@HystrixCommand(fallbackMethod = "findByIdFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
			@HystrixProperty(name = "metrics.rollingStats.timeoutInMilliseconds", value = "10000")
	}, threadPoolProperties = {
		@HystrixProperty(name = "coreSize", value = "1"),
		@HystrixProperty(name = "maxQueueSize", value = "10")
	})*/
	@HystrixCommand(fallbackMethod = "findByIdFallback")
	@GetMapping("/user/{id}")
	public User findById(@PathVariable("id") int id) {
		return this.restTemplate.getForObject("http://PROVIDER-SERVICE/soa/user/"+id, User.class);
	}
	
	public User findByIdFallback(int id) {
		User user = new User();
		user.setId(-1);
		user.setAge(0);
		user.setUsername("默认用户");
		user.setPassword("123456");
		return user;
	}
	//===================================
	/*@Value("${user.serviceUri}") // 获取配置文件自己的地址路径
	private String serviceUri;*/
	
	/*
	 * 这是ribbon的方式实现
	 * @Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/user/{id}")
	public User findById(@PathVariable("id") int id) {
		return this.restTemplate.getForObject("http://provider-service/soa/user/"+id, User.class);
	}*/
	
	//===================================
	
	/*
	 * feign方式实现
	 */
	/*@Autowired
	private UserFeignClient userFeignClient;
	
	@GetMapping("/user/{id}")
	public User findById(@PathVariable("id") int id) {
		return this.userFeignClient.findById(id);
	}*/
}
