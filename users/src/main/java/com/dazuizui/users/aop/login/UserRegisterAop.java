package com.dazuizui.users.aop.login;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Yida Yang
 * 此类是为Aop代码增强类为用户注册模块服务
 */
@Aspect
@Component
public class UserRegisterAop {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 非幂等性问题代码增强解决
     * Solve user register non-idempotent vinita
     */
    @Before("execution(* com.dazuizui.users.controller.login.UserRegisterController.registerUser(..))")
    public void solveUserRegisterNonIdempotentVinita(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        String token = (String) args[1];
        System.out.println(token);
        boolean b = redisTemplate.delete(token);
        if (!b){
            throw new Exception("idempotency");
        }
    }
}
