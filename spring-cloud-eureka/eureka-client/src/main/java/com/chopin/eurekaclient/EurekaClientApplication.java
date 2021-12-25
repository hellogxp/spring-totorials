package com.chopin.eurekaclient;

import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

    @RestController
    class ServiceInstanceRestController {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());

        @Autowired
        private DiscoveryClient discoveryClient;

        @GetMapping("/service-instances")
        public List<ServiceInstance> serviceInstanceByApplicationName() {
            List<ServiceInstance> serviceInstanceList = this.discoveryClient.getInstances("service-provider");
            List<String> serviceList = serviceInstanceList.stream().map(ServiceInstance::getInstanceId).collect(
                Collectors.toList());
            logger.info(
                "Service instance amount: {}, serviceList: {}", serviceInstanceList.size(), serviceList);
            return this.discoveryClient.getInstances("service-provider");
        }
    }

}
