package com.hfcsbc.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
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
        String authorizationHeader = "Authorization";
        HttpServletRequest request = ctx.getRequest();
        Enumeration<String> authorization = request.getHeaders(authorizationHeader);

        System.out.println("<<<<<<<<<<<<<<<<<");
        System.out.println(request.getRequestURI());

        Map<String, String> zuulRequestHeaders = ctx.getZuulRequestHeaders();
        if(authorization.hasMoreElements()){
            String token = authorization.nextElement();
            System.out.println(">>>>>>>>>>>>>>>>>>>>");
            System.out.println(token);
            zuulRequestHeaders.put(authorizationHeader, token);
        }
        return null;
    }
}
