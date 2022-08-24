package com.tensquare.user.service;


import com.tensquare.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class PermissionService  {
    @Autowired
    HttpServletRequest request;
    @Autowired
    JwtUtil jwtUtil;


    public List<String> queryPermission() {
         String header = request.getHeader("Authorization");
        if (header == null || "".equals(header)) {
            return null;
        }
        if (!header.startsWith("bearer ")) {
            return  null;
        }
        String token = header.substring(7);
        Claims claims = null;
        List<String> roles = null;
        try {
            claims = jwtUtil.parseJWT(token);
          roles    =(List<String>) claims.get("roles");

        }catch (JwtException e) {
            throw new JwtException("token已过期");
        }catch (Exception e){
            throw  new JwtException("权限认证失败，请重新认证");
        }

        return roles;
    }
}
