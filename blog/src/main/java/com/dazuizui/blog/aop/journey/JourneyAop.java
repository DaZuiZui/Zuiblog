package com.dazuizui.blog.aop.journey;
import com.dazuizui.api.jwt.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class JourneyAop {
    @Autowired
    private RedisTemplate redisTemplate;

    @Before("execution(* com.dazuizui.blog.controller.journey.JourneyController.addJourneyDate(..))")
    public String addJourneyDate(JoinPoint joinPoint) throws Exception {
        //获取token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        boolean b = redisTemplate.delete(token);
        if (!b){
            throw new Exception("idempotency");
        }
        return "删除成功";
    }

    /**
     * 删除指定旅程碑，查看是否为管理员
     * @param joinPoint
     * @throws Exception
     */
    @Before("execution(* com.dazuizui.blog.controller.journey.JourneyController.deleteTheJournerMonumentById(..))")
    public void deleteTheJournerMonumentById(JoinPoint joinPoint) throws Exception {
        //获取token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        //解析token
        String role = JwtUtil.analysisJWTForGetTheRole(token);
        System.out.println(role);
        //判断是否为管理员
        if (!role.equals("admin")){
            throw new Exception("非管理员操作");
        }
    }

    /**
     * 删除指定旅程碑，查看是否为管理员
     * @param joinPoint
     * @throws Exception
     */
    @Before("execution(* com.dazuizui.blog.controller.journey.JourneyController.deleteTheJournerMonumentById(..))")
    public void getTheJourneyMounmentById(JoinPoint joinPoint) throws Exception {
        //获取token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        //解析token
        String role = JwtUtil.analysisJWTForGetTheRole(token);
        System.out.println(role);
        //判断是否为管理员
        if (!role.equals("admin")){
            throw new Exception("非管理员操作");
        }
    }

    /**
     * 修改旅程碑
     */
    @Before("execution(* com.dazuizui.blog.controller.journey.JourneyController.updateTheJourneyMounenmentById(..))")
    public void updateTheJourneyMounenmentById(JoinPoint joinPoint) throws Exception{
        //获取token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        //解析token
        String role = JwtUtil.analysisJWTForGetTheRole(token);
        System.out.println(role);
        //判断是否为管理员
        if (!role.equals("admin")){
            throw new Exception("非管理员操作");
        }
    }
}
