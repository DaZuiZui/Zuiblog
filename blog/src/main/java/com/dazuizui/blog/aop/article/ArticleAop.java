package com.dazuizui.blog.aop.article;

import com.dazuizui.api.jwt.JwtUtil;
import com.dazuizui.api.pojo.Article;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ArticleAop {
    @Autowired
    private RedisTemplate redisTemplate;

    @Before("execution(* com.dazuizui.blog.controller.aticle.AticleController.queryArticleByUsernameAndTitle(..))")
    public void queryArticleByUsernameAndTitle(JoinPoint joinPoint){
        //获取token
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        //解析token
        String username = JwtUtil.analysisJWT(token);
        String usernameInRequest = (String) args[2];
        if (username.equals(usernameInRequest)){
            System.out.println("是你的文章");
        }else{
            System.out.println("不是你的文章");
            new Exception("非法访问");
        }
    }

    @Before("execution(* com.dazuizui.blog.controller.blogging.BloggingController.WritingABlog(..))")
    public void WritingABlog(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        //System.err.println((String) args[1]);
        System.out.println("token:"+token);
        boolean b = redisTemplate.delete(token);
        if (!b){
            throw new Exception("idempotency");
        }
    }

    @Before("execution(* com.dazuizui.blog.controller.blogging.BloggingController.updateTheArticle(..))")
    public void updateTheArticle(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        String token = (String) args[0];
        //System.err.println((String) args[1]);
        System.out.println("token:"+token);
        boolean b = redisTemplate.delete(token);
        if (!b){
            throw new Exception("idempotency");
        }

        //判断是否本人或者管理员操作
        token = (String) args[1];
        Article article = (Article) args[5];
        String username = JwtUtil.analysisJWT(token);
        if (!username.equals(article.getAuthor())){
            String role = JwtUtil.analysisJWTForGetTheRole(token);
            if (role.equals("admin")){

            }else{
                throw new Exception("非法操作");
            }
        }
    }
}
