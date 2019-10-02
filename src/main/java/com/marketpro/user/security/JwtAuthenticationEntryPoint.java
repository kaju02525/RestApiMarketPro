package com.marketpro.user.security;

import com.google.gson.Gson;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Map<String,Object> map=new HashMap<>();
        map.put("status",false);
        map.put("message","Unauthorized Please try again!!");
        String jsonLoginRepose=new Gson().toJson(map);

        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().print(jsonLoginRepose);
    }
}

