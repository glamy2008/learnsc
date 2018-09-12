package com.didispace.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class HelloController {

    private final Logger logger = Logger.getLogger(String.valueOf(getClass()));

    private final DiscoveryClient client;

    @Autowired
    public HelloController(DiscoveryClient client) {
        this.client = client;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String index() {

        client.getServices().forEach(id -> {
            client.getInstances(id).forEach(instance -> {
                logger.info("hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());

            });
        });
        return "Hello World!";
    }
}
