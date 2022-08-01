package com.dazuizui.blog.redis.Article;

import com.dazuizui.api.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 博文redis操作
 */
@Service
public class ArticleRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void removeTheArticle(String author, String title, String technical){
        redisTemplate.delete("dazuiblog:blog:article:"+author+":Public:"+technical+":"+title);
        redisTemplate.delete("dazuiblog:blog:article:"+author+":private:"+technical+":"+title);
    }

    public void setarticlesoftheauthor(String author,ArrayList<Article> list){
        for (Article article : list) {
            redisTemplate.opsForList().rightPush("dazuiblog:user:"+author+":articles", article);

        }
        redisTemplate.expire("dazuiblog:user:articles",60*60*24*15,TimeUnit.SECONDS);
    }


    /**
     * 获取自己发布的文章
     * @param author
     * @param list
     * @return
     */
    public ArrayList<Article>  getarticlesoftheauthor(String author){
        return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:user:"+author+":articles",0,-1);
    }


    /**
     * 酱数据库的用户发布的文件夹放入redis
     * @param author
     * @param list
     */
    public void setfolders(String author,ArrayList<Article> list){
        for (Article article : list) {
            redisTemplate.opsForList().rightPush("dazuiblog:user:"+author+":folders", article);
        }
        redisTemplate.expire("dazuiblog:user:folders",60*60*24*15,TimeUnit.SECONDS);
    }

    /**
     * 获取用户的文件夹
     * @param author
     * @return
     */
    public ArrayList<Article> getfolders(String author){
        return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:user:"+author+":folders",0,-1);
    }


    /**
     * 分页查询
     * @param start    开始位置，他的算法是start-1*10 比如传入1就是从0开始 传入2 就是从10开始 ，目前系统设置一页10个
     * @param end      结束位置 end*10
     * @return
     */
    public ArrayList<Article> getPaginQuery(int start,int end){
        return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public",start,end);
    }

    /**
     * 查询公开博文的数量
     * @return
     */
    public int viewTheNumberOfPulbicArticle(){
        return  Integer.valueOf(stringRedisTemplate.opsForValue().get("dazuiblog:article:public:count"));
    }

    /**
     * 用户发布的所有文章
     * @param author
     */
    public ArrayList<Article> getDatatoRedisUsernameList(String author){
        return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:articlelist:"+author,0,-1);
    }

    /**
     * 查看用户发布文章的数量
     * @return
     */
    public Long getUserArticleNumbers(String author){
        return redisTemplate.opsForList().size("dazuiblog:blog:articlelist:"+author+":Public");
    }

    /**
     * 获取指定用户redislist集合中的公开数据
     * @return
     */
    public ArrayList<Article> getPublicDataOnredisToIt(String author){
        return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:articlelist:"+author+":Public",0,-1);
    }

    /**
     * 添加公开数据到指定用户redislist集合
     * @param article
     * @return
     */
    public void addPublicDataOnredisToIt(Article article){
       redisTemplate.opsForList().rightPush("dazuiblog:blog:articlelist:"+article.getAuthor()+":Public",article);
    }

    //Query data to redis by username and title and username and techical
    public Article queryDatatoRedisByUsernameAndTitleAndTechical(String title, String author ,String technical){
        return (Article) redisTemplate.opsForHash().get("dazuiblog:blog:article:"+author+":Public:"+technical,title);
    }

    //Query data to redis by username and title and username and techical
    public Article getDatatoRedisByUsernameAndTitleAndTechical(String title, String author ,String technical){
        return (Article) redisTemplate.opsForValue().get("dazuiblog:blog:article:"+author+":Public:"+technical+":"+title);
    }

    //获取用户发布的全部文章
    public ArrayList<Article> getAllUserArticle(String author){
        return (ArrayList<Article>)redisTemplate.opsForList().range("dazuiblog:blog:articlelist:"+author,0,-1);
    }

    //通过username 和title获取指定文章
    public Article queryAnArticleToRedisByAuthorAndTitle(String author, String title){
        //查询公开数据
        Article article = (Article) redisTemplate.opsForHash().get("dazuiblog:blog:article:Public",title+":"+author);
        //如果公开没有查询到数据就去私有查询
        if (article == null) {
            article = (Article) redisTemplate.opsForHash().get("dazuiblog:blog:article:Private",title+":"+author);
        }
        return article;
    }

    /**
     * 获取自己创建的文件夹
     * @param author
     * @return
     */
    public ArrayList<String> getAllFolders(String author) {
        Set<String> set =  redisTemplate.opsForHash().keys("dazuiblog:blog:article:userfile:"+author);

        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<String> it=set.iterator();
        while (it.hasNext()){
            arrayList.add(it.next());
        }
        return arrayList;
    }

    /**
     * 查询文件夹中的数据
     * @param author            文章作者
     * @param blogfileclass     文件夹
     * @return
     */
    public ArrayList<Article> getBlogfileclassDataInRedis(String author,String blogfileclass){
        return (ArrayList<Article>) redisTemplate.opsForHash().get("dazuiblog:blog:article:userfile:"+author,blogfileclass);
    }

    /**
     * 模糊查询by title
     */
    public ArrayList<Article> queryFuzzyQuery(String title){
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan("dazuiblog:blog:article:Public",
                ScanOptions.scanOptions().match("*"+title+"*").count(1000).build());
        ArrayList<Article> arrayList = new ArrayList<>();

        //获取模糊查询出来的数据
        while (cursor.hasNext()) {
            Map.Entry<Object,Object> entry = cursor.next();
            //Object key = entry.getKey();
            Article article = (Article) entry.getValue();
            System.out.println(article);
            arrayList.add(article);
        }

        //关闭cursor
        try {
            cursor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

    /**
     * get all article
     * 获取公开全部文章
     */
    public ArrayList<Article> getPublicArticleDataToRedisList(){
        return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public",0,-1);
    }
}
