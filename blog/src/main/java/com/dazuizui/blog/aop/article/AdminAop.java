package com.dazuizui.blog.aop.article;

import com.dazuizui.api.jwt.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Aspect
@Component
public class AdminAop {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 鉴权是否为管理员
     * @param joinPoint
     */
    @Before("execution(* com.dazuizui.blog.controller.aticle.AticleManagementController.*(..))")
    public void checkAdminRole(JoinPoint joinPoint) throws Exception {
        //获取token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[1];
        //解析token
        String role = JwtUtil.analysisJWTForGetTheRole(token);
        System.out.println(role);
        //判断是否为管理员
        if (!role.equals("admin")){
           throw new Exception("非管理员操作");
        }
    }

    /**
     * 删除指定文章非幂等性处理
     * @param joinPoint
     * @return
     */
    @Before("execution(* com.dazuizui.blog.controller.aticle.AticleManagementController.removeTheAriticle(..))")
    public String removeTheAriticle(JoinPoint joinPoint) throws Exception {
        //获取token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        boolean b = redisTemplate.delete(token);
        if (!b){
            throw new Exception("idempotency");
        }
        return "删除成功";
    }
}