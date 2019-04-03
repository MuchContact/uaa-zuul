package com.hfcsbc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;

@Service
public class ZuulDynamicConfig {
    @Autowired
    private ZuulProperties zuulProperties;
    @Autowired
    private ZuulHandlerMapping zuulHandlerMapping;

    @Autowired
    private DiscoveryClientRouteLocator discoveryClientRouteLocator;

    @PostConstruct
    public void afterConstruct() {

        ZuulProperties.ZuulRoute route_cy = new ZuulProperties.ZuulRoute();
        route_cy.setId("erp_cy");
        route_cy.setPath("/vanke/**");
        route_cy.setStripPrefix(false);
        route_cy.setUrl("http://www.vanke.com");
        discoveryClientRouteLocator.addRoute(route_cy);

        String url = "http://www.baidu.com";
        String id1 = "111";
        zuulProperties.getRoutes().put(id1,
                new ZuulProperties.ZuulRoute(id1, String.format("/%s/**", id1),
                        null, url, true, false, new HashSet<>()));
        String id2 = "222";
        zuulProperties.getRoutes().put(id2,
                new ZuulProperties.ZuulRoute(id2, String.format("/%s/**", id2),
                        null, url, true, false, new HashSet<>()));

        zuulProperties.getRoutes().remove(id1);
        zuulHandlerMapping.setDirty(true);
    }
}
