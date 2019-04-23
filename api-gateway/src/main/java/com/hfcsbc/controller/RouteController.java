package com.hfcsbc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

@RestController
@RequestMapping("/dyn-routes")
public class RouteController {
    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ZuulHandlerMapping zuulHandlerMapping;

    @Autowired
    private DiscoveryClientRouteLocator discoveryClientRouteLocator;

    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    public void addRoute(@RequestParam String path) {

        String url = "http://www.baidu.com";
        String id1 = path != null ? path.trim() : "xxx";
        zuulProperties.getRoutes().put(id1,
                new ZuulProperties.ZuulRoute(id1, String.format("/%s/**", id1),
                        null, url, true, false, new HashSet<>()));

//        zuulHandlerMapping.setDirty(true);
        // 立即生效，没有下面这行代码最终也会生效，但是会有延迟
        publisher.publishEvent(new RoutesRefreshedEvent(discoveryClientRouteLocator));
    }
}
