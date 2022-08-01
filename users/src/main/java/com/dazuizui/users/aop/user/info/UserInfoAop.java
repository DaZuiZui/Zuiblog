package com.dazuizui.users.aop.user.info;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserInfoAop {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 非幂等性问题代码增强解决
     * Solve user register non-idempotent vinita
     */
    @Before("execution(* com.dazuizui.users.controller.userinfo.UserInfoController.userUpdateInfo(..))")
    public void solveUserRegisterNonIdempotentVinita(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        //System.err.println((String) args[1]);
        System.out.println("token:"+token);
        boolean b = redisTemplate.delete(token);
        if (!b){
            throw new Exception("idempotency");
        }
    }
}
