package com.dazuizui.blog.redis.blogging;

import com.dazuizui.api.pojo.Article;
import io.lettuce.core.ScriptOutputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class BloggingRedis {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 修改共有的官方list集合中的数据，更改名字
     */
    public String updateNameInAnArticle(String oldname,String newname){
        String username = null;
        int count = 0;
        //获取共有list集合数据
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public",0,-1);
        //进行修改
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getName().equals(oldname)){
                //进行更新
                arrayList.get(i).setName(newname);
                redisTemplate.opsForList().set("dazuiblog:articlelist:Public",i,arrayList.get(i));

                //更新map集合
                addAnArticleToRedis(arrayList.get(i));
                if (username ==null){
                    username = arrayList.get(i).getAuthor();
                }
                count++;
                /**
                 * 留言：后续有需要在进行更新，暂时这个样子
                 */
            }
        }

        System.out.println("已经修改" + count + "篇");
        return username;
    }

    //添加一个博文到redis map集合
    public void addAnArticleToRedis(Article article){
        redisTemplate.opsForHash().put("dazuiblog:blog:article:"+article.getPrivacy(),article.getTitle()+":"+article.getAuthor(),article);
    }



    //添加到个人分类
    public void addDatatoRedisByUsername(Article article){
        //redisTemplate.opsForHash().put("dazuiblog:blog:article:"+article.getAuthor()+":"+article.getPrivacy()+":"+article.getTechnical(),article.getTitle(),article);
        redisTemplate.opsForValue().set("dazuiblog:blog:article:"+article.getAuthor()+":"+article.getPrivacy()+":"+article.getTechnical()+":"+article.getTitle(),article,60*60*24, TimeUnit.SECONDS);
    }



    /**
     * Query wherther dada exists in redis
     * Return false if it exists , Return true if it does not
     * @param article
     * @return
     */
    public boolean QueryWhertherDataExistsInRedis(Article article){
        //查看用户是否发布此标题的文章
        boolean switchs =  redisTemplate.opsForHash().hasKey("dazuiblog:blog:article:"+article.getPrivacy(),article.getTitle()+":"+article.getAuthor());
        return switchs;
    }

    /**
     * add a data to redis list
     */
    public void addPublicDataToRedisList(Article article){
        redisTemplate.opsForList().leftPush("dazuiblog:articlelist:"+article.getPrivacy(),article);
    }



    /**
     * 用户发布的所有文章
     * @param article
     */
    public void addDatatoRedisUsernameList(Article article){
        redisTemplate.opsForList().leftPush("dazuiblog:blog:articlelist:"+article.getAuthor(),article);
    }



    public void countOfAriticle(){

        stringRedisTemplate.opsForValue().increment("dazuiblog:article:public:count",1);
    }

    /**
     * 按个人分类文件夹内的文章
     */
    public void deleteUserUpArticlesByBlogfileclass(String author,String blogfileclass){
        //redisTemplate.opsForHash().get("dazuiblog:blog:article:userfile:"+article.getAuthor()+":public:"+article.getTechnical())
        //删除个人分类
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:userfile:"+author,blogfileclass);
    }


    /**
     * get some article
     */

    public ArrayList<Article> getSomePublicArticle(){
       return (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public",0,-1);
    }


    /**
     * 添加个人分类 文件夹
     */
    public void addBlogfileclassInRedis(Article article){
        //判断该文件夹是否存在
        boolean switchs =  redisTemplate.opsForHash().hasKey("dazuiblog:blog:article:userfile:"+article.getAuthor(),article.getBlogfileclass());
        //添加自己的创建的文件夹列表
        //redisTemplate.opsForHash().put("dazuiblog:blog:article:userfile:"+article.getAuthor(),article.getBlogfileclass());

        //添加到文件夹内的数据
        if (switchs){
           ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForHash().get("dazuiblog:blog:article:"+article.getAuthor(),article.getBlogfileclass());
            System.out.println("添加的文件"+"dazuiblog:blog:article:userfile:"+article.getAuthor());
           redisTemplate.opsForHash().put("dazuiblog:blog:article:userfile:"+article.getAuthor(),article.getBlogfileclass(),arrayList);
        }else{
            ArrayList<Article> arrayList = new ArrayList<>();
            arrayList.add(article);
            System.out.println("添加的文件"+"dazuiblog:blog:article:userfile:"+article.getAuthor());
            redisTemplate.opsForHash().put("dazuiblog:blog:article:userfile:"+article.getAuthor(),article.getBlogfileclass(),arrayList);
        }

        System.err.println("添加成功");
    }



    /**
     * 删除redis中的map集合数据
     * @param author
     * @param blogfileclass
     * @return
     */
    public void deleteTheFolders(String author,String blogfileclass) {
        //删除map集合
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:userfile:" + author, blogfileclass);
        //删除list集合的公开数据
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public", 0, -1);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAuthor().equals(author) && arrayList.get(i).getBlogfileclass().equals(blogfileclass)) {
                //删除官方分类
                DeleteOfficialCategory(arrayList.get(i));
                //删除公开数据
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Public",i,arrayList.get(i));
                System.err.println("公开数据删除成功");
                //删除个人map集合
                redisTemplate.opsForHash().delete("dazuiblog:blog:article:"+arrayList.get(i).getAuthor()+":"+arrayList.get(i).getPrivacy()+":"+arrayList.get(i).getTechnical(),arrayList.get(i).getTitle()+":"+arrayList.get(i).getAuthor());
            }
        }

        //删除list集合的私有数据
        arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Private", 0, -1);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAuthor().equals(arrayList) && arrayList.get(i).getBlogfileclass().equals(blogfileclass)) {
                //删除官方分类
                DeleteOfficialCategory(arrayList.get(i));
                //删除公开数据
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Private", i, arrayList.get(i));
                //删除个人map集合
                redisTemplate.opsForHash().delete("dazuiblog:blog:article:"+arrayList.get(i).getAuthor()+":"+arrayList.get(i).getPrivacy()+":"+arrayList.get(i).getTechnical(),arrayList.get(i).getTitle());
                //删除官方分类list集合指定数据
                DeleteOfficialCategory(arrayList.get(i));
            }
        }
    }



    /**
     * 删除官方分类 list集合
     */
    public void DeleteOfficialCategory(Article article){
        //获取官方分类list集合所有数据
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:article:"+article.getTechnical()+":"+article.getPrivacy(),0,-1);
        //如果找到指定数据移除
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getTechnical().equals(article.getTechnical())){
                redisTemplate.opsForList().remove("dazuiblog:blog:article:"+article.getTechnical()+":"+article.getPrivacy(),i,arrayList.get(i));
            }
        }
    }

    /**
     * 删除list中指定分类
     * @param article
     */
    public void deleteAnArticleToRedisByCategorylist(Article article){
        redisTemplate.opsForList().range("dazuiblog:blog:article:"+article.getPrivacy()+":"+article.getTechnical(),0,-1);
    }


    /**
     * 删除个人发布数据list集合
     */
    public void removeUserDateList(String author,String blogfileclass){
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:articlelist:"+author,0,-1);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAuthor().equals(author) && arrayList.get(i).getBlogfileclass().equals(blogfileclass)){
                redisTemplate.opsForList().remove("dazuiblog:blog:articlelist:"+author,i,arrayList.get(i));
            }
        }
    }

    /**
     * 删除官方list集合
     */
    public void removelistdatainredis(String author,String title){
        /**
         * 删除公开数据
         */
        //获取数据从redis
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public",0,-1);
        //删除指定数据
        for (int i = 0; i < arrayList.size(); i++) {
            if (author.equals(arrayList.get(i).getAuthor()) && title.equals(arrayList.get(i).getTitle())){
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Public",i,arrayList.get(i));
                //删除官方文件夹的 分类
                removeAnArticleToRedisByCategory(arrayList.get(i));
                //删除个人map集合
                removeDatatoRedisByUsername(arrayList.get(i));
                //删除个人分类
                deleteBlogfileclassInRedis(arrayList.get(i));
            }
        }

        /**
         * 删除私有数据
         */
        //获取数据从redis
        arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Private",0,-1);
        //删除指定数据
        for (int i = 0; i < arrayList.size(); i++) {
            if (author.equals(arrayList.get(i).getAuthor()) && title.equals(arrayList.get(i).getTitle())){
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Private",i,arrayList.get(i));
                //删除官方文件夹的 分类
                removeAnArticleToRedisByCategory(arrayList.get(i));
                //删除个人map集合
                removeDatatoRedisByUsername(arrayList.get(i));
                //删除个人分类
                deleteBlogfileclassInRedis(arrayList.get(i));
            }
        }
    }


    /**
     * 删除数据在个人分类
     */
    public void deleteBlogfileclassInRedis(Article article) {
        //获取数据
        ArrayList<Article> arrayList= (ArrayList<Article>) redisTemplate.opsForHash().get("dazuiblog:blog:article:userfile:"+article.getAuthor(),article.getBlogfileclass());
        //删除指定数据
       if (arrayList != null){
           for (int i = 0; i < arrayList.size(); i++) {
               if (article.getAuthor().equals(arrayList.get(i).getAuthor()) && article.getTitle().equals(arrayList.get(i).getTitle())){
                   arrayList.remove(i);
               }
           }

           //添加到数据
           redisTemplate.opsForHash().put("dazuiblog:blog:article:userfile:"+article.getAuthor(),article.getBlogfileclass(),arrayList);

       }

     }

    /**
     * 删除数据在用户list集合
     * @param author
     * @param title
     */
    public void removeDatatoRedisUsernameList(String author,String title){
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:articlelist:"+author,0,-1);
        for (int i = 0; i < arrayList.size(); i++) {
            if (author.equals(arrayList.get(i).getAuthor()) && title.equals(arrayList.get(i).getTitle())){
                redisTemplate.opsForList().remove("dazuiblog:blog:articlelist:"+author,i,arrayList.get(i));
            }
        }
    }

    /**
     * 删除官方map集合中的数据
     */
    public void removeAnArticleToRedis(String author,String title){
        //删除公开数据
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:Public",title+":"+author);
        //删除私有数据
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:Private",title+":"+author);
    }


    /**
     * 删除官方分类list集合中的数据
     */
    public void delettheAricleOfficialCategory(Article article){
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:article:"+article.getTechnical()+":"+article.getPrivacy(),0,-1);
        //如果找到指定数据移除
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(article)){
                redisTemplate.opsForList().remove("dazuiblog:blog:article:"+article.getPrivacy()+":"+article.getTechnical(),i,arrayList.get(i));
                break;
            }
        }
    }

    /**
     * 删除数据在个人map集合
     * @param article
     */
    public void removeDatatoRedisByUsername(Article article){
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:"+article.getAuthor()+":"+article.getPrivacy()+":"+article.getTechnical(),article.getTitle());
    }

    /**
     * 删除数据在官方分类文件夹
     * @param article
     */
    public void removeAnArticleToRedisByCategory(Article article){
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:"+article.getPrivacy()+":"+article.getTechnical(),article.getTitle());
    }


    /*******************************************************************************************************************
     * 修改区域
     ********************************************************************************************************************/

    /**
     * 修改官方map集合
     * @param article
     */
    public void updateAnArticleToRedis(String title,Article article){
        //删除建添加建
        Long switchs = redisTemplate.opsForHash().delete("dazuiblog:blog:article:Public",title+":"+article.getAuthor());
        System.out.println(switchs+"\n\n\n");
        if (switchs == 1){
            redisTemplate.opsForHash().delete("dazuiblog:blog:article:Private",title+":"+article.getAuthor());
        }
        System.out.println("修改的key"+"dazuiblog:blog:article:"+article.getPrivacy());
        //添加数据
        redisTemplate.opsForHash().put("dazuiblog:blog:article:"+article.getPrivacy(),article.getTitle()+":"+article.getAuthor(),article);
    }

    /**
     * 修改官方list集合
     */
    public void updatePublicDataToRedisList(String author,String title,Article article){

        /**
         * 修改公开数据
         */
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Public",0,-1);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAuthor().equals(author) && arrayList.get(i).getTitle().equals(title)){
                //删除指定下表的数据
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Public",i,arrayList.get(i));
                //在指定下标数据
                redisTemplate.opsForList().leftPush("dazuiblog:articlelist:Public",article);
                //删除官方分类
                updateAnArticleToRedisByCategory(arrayList.get(i),article);
                //删除以及更新个人map集合
                updateDatatoRedisByUsername(arrayList.get(i),article);
                //修改文件夹
                updateBlogfileclassInRedis(arrayList.get(i),article);
                System.out.println("成功");
                //修改官方分类list集合中的数据
                UpdateOfficialCategory(author,title,arrayList.get(i));
                System.out.println("voer");
                return;
            }
        }


        /**
         * 修改私有数据
         */
        arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:articlelist:Private",0,-1);
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAuthor().equals(author) && arrayList.get(i).getTitle().equals(title)){
                //删除指定下表的数据
                redisTemplate.opsForList().remove("dazuiblog:articlelist:Private",i,arrayList.get(i));
                //在指定下标数据
                redisTemplate.opsForList().leftPush("dazuiblog:articlelist:Private",article);
                //删除官方分类
                updateAnArticleToRedisByCategory(arrayList.get(i),article);
                //删除以及更新个人map集合
                updateDatatoRedisByUsername(arrayList.get(i),article);
                //修改文件夹
                updateBlogfileclassInRedis(arrayList.get(i),article);
                //修改官方分类list集合中的数据
                UpdateOfficialCategory(author,title,arrayList.get(i));
            }
        }
    }

    /**
     * 修改官方分类 list集合
     */
    public void UpdateOfficialCategory(String author,String title,Article article){
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:article:"+article.getTechnical()+":"+article.getPrivacy(),0,-1);
        //如果找到指定数据移除
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAuthor().equals(author) && arrayList.get(i).getTitle().equals(title)){
                Long switches = redisTemplate.opsForList().remove("dazuiblog:blog:article:Public:"+article.getPrivacy(),i,arrayList.get(i));
                if (switches == 0){
                    redisTemplate.opsForList().remove("dazuiblog:blog:article:Private:"+article.getPrivacy(),i,arrayList.get(i));
                }

                redisTemplate.opsForList().leftPush("dazuiblog:blog:article:"+article.getTechnical()+":"+article.getPrivacy(),article);
                break;
            }
        }
    }


    /**
     * 修改文件夹
     * @param oldArticle
     */
    public void updateBlogfileclassInRedis(Article oldArticle,Article newAricle){
        //获取文件夹内的数据
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForHash().get("dazuiblog:blog:article:userfile:"+oldArticle.getAuthor(),oldArticle.getBlogfileclass());
        System.err.println(arrayList+"\n\n\n");
        if (arrayList != null){
            for (int i = 0; i < arrayList.size(); i++) {
                //查询指定数据
                if (arrayList.get(i).getAuthor().equals(oldArticle.getAuthor()) && arrayList.get(i).getTitle().equals(oldArticle.getTitle())){
                    arrayList.remove(i);
                    //更新redis
                    redisTemplate.opsForHash().put("dazuiblog:blog:article:userfile:"+oldArticle.getAuthor(),oldArticle.getBlogfileclass(),arrayList);
                    break;
                }
            }
        }

        //添加新的数据到redis个人分类文件夹
        addBlogfileclassInRedis(newAricle);
    }

    /**
     * 修改个人发布的list数据
     * @param article
     */
    public void updateDatatoRedisUsernameList(String author,String title,Article article){
        ArrayList<Article> arrayList = (ArrayList<Article>) redisTemplate.opsForList().range("dazuiblog:blog:articlelist:"+author,0,-1);

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getAuthor().equals(author) && arrayList.get(i).getTitle().equals(title)) {
                System.err.println("符合标题"+arrayList.get(i).getTitle());

                //删除指定下表的数据
                redisTemplate.opsForList().remove("dazuiblog:blog:articlelist:"+arrayList.get(i).getAuthor(),i,arrayList.get(i));
                System.out.println("1232");
                //在指定下标添加数据
                redisTemplate.opsForList().leftPush("dazuiblog:blog:articlelist:"+arrayList.get(i).getAuthor(),article);
                System.out.println("修改成功");
            }
        }
    }


    /**
     * 修改用户map集合
     * @param oldArticle
     */
    public void updateDatatoRedisByUsername(Article oldArticle,Article newArticle){
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:"+oldArticle.getAuthor()+":"+oldArticle.getPrivacy()+":"+oldArticle.getTechnical(),oldArticle.getTitle());
        redisTemplate.opsForHash().put("dazuiblog:blog:article:"+newArticle.getAuthor()+":"+newArticle.getPrivacy()+":"+newArticle.getTechnical(),newArticle.getTitle(),newArticle);
    }


    /**
     * 修改官方分类 map集合
     * @param article
     */
    public void updateAnArticleToRedisByCategory(Article article,Article newarticle){
        redisTemplate.opsForHash().delete("dazuiblog:blog:article:"+article.getPrivacy()+":"+article.getTechnical(),article.getTitle());
        redisTemplate.opsForHash().put("dazuiblog:blog:article:"+newarticle.getPrivacy()+":"+newarticle.getTechnical(),newarticle.getTitle(),newarticle);
    }
}
