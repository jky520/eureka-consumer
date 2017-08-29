package com.jky.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jky.product.entity.Product;

@RestController
public class ProductController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping("/product/{id}")
	public Product findById(@PathVariable("id") int pid) {
		System.out.println(pid+"---------------------");
		return this.restTemplate.getForObject("http://provider-service/soa/product/"+pid, Product.class);
	}
	
	/**
	 * 查询provider-service服务的信息并返回
	 * @return provider-service服务的信息
	 */
	@GetMapping("/user-instance")
	public List<ServiceInstance> showInfo() {
		return this.discoveryClient.getInstances("provider-service");
	}
}
