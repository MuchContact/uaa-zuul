package com.hfcsbc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Enumeration;
import java.util.Map;

@Component
public class HeaderZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String authorization1 = "Authorization";
        Enumeration<String> authorization = ctx.getRequest().getHeaders(authorization1);
        Map<String, String> zuulRequestHeaders = ctx.getZuulRequestHeaders();
        if(authorization.hasMoreElements()){
            String token = authorization.nextElement();
            zuulRequestHeaders.put(authorization1, token);
        }
        return null;
    }
}
