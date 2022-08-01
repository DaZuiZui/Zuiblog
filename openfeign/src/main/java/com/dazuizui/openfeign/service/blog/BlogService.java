package com.dazuizui.openfeign.service.blog;

import com.dazuizui.api.pojo.Article;
import com.dazuizui.api.pojo.Journey;
import com.dazuizui.api.pojo.bo.JourneyBo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient(value = "blog")
public interface BlogService {

    /**
     * 查询AboutDazui的旅程碑
     */
    @PostMapping("/blog/all/getprivatejouyneyofpublic")
    public String queryPrivateDataOfJourney(@RequestParam("type")String type);
    @PostMapping("/blog/amin/updateTheJourneyMounmentById")
    public String updateTheJourneyMounenmentById(@RequestParam("token")String token, @RequestBody JourneyBo journeyBo);

    /**
     * 查询指定旅程碑
     * @param token
     * @param id
     * @return
     */
    @GetMapping("/blog/admin/getTheJourneyMounmentByid")
    public String getTheJourneyMounmentById(@RequestParam("token")String token,@RequestParam("id")Integer id);

    /**
     * 删除指定旅程碑 通过id
     * @param token 管理员token
     * @param id    旅程碑id
     * @return
     */
    @PostMapping("/blog/admin/deleteTheMonumentByid")
    public String deleteTheJournerMonumentById(@RequestParam("token")String token,@RequestParam("id")Integer id);

    /**
     * 查询AboutDazui的旅程碑
     */
    @GetMapping("/blog/all/getalljouyneyofpublic")
    public String queryAllDataOfJourney();

    /**
     * 添加里程碑
     * @param Idemtoken
     * @param journey
     * @return
     */
    @PostMapping("/blog/admin/jouyner/add")
    public String addJourneyDate(@RequestParam("Idemtoken")String Idemtoken, @RequestBody Journey journey);

    /**
     *
     * @param Idemtoken 幂等性
     * @param token     管理员token
     * @param author    文章作者
     * @param title     标题
     * @param technical 分类
     * @return
     */
    @PostMapping("/blog/admin/deletethearticle")
    public String removeTheAriticle(@RequestParam("Idemtoken")String Idemtoken,@RequestParam("token")String token,@RequestParam("author")String author,@RequestParam("title")String title,@RequestParam("technical")String technical);
    /**
     * 修改信息完成后 去博文里更改用户的用户名
     * @param token
     * @param name
     * @return
     */
    @PostMapping("/blog/user/pr/updateNameOfArticle")
    public String updateNameOfArticle(@RequestParam("token")String token,@RequestParam("name")String name);

    /**
     * 分页查询用户发布的文章
     * @param author
     * @return
     */
    @GetMapping("/blog/user/paingToGetArticleOfUser")
    public String pagingToGetArticleOfUser(@RequestParam("author")String author,@RequestParam("start")int start);

    /**
     * 获取用户所发布的文章数量
     */
    @GetMapping("/blog/user/getuserarticlenumbers")
    public String getUserArtileNumbers(@RequestParam("author")String author);

    /**
     * 修改博文的name
     */
    @PostMapping("/user/admin/updateBlogAricleName")
    public String updateBlogAritcleName(@RequestParam("oldname")String oldname,@RequestParam("newname")String newname);

    /**
     * 管理员删除指定文章
     * @param username
     * @param title
     * @param token
     * @return
     */
    @PostMapping("/blog/admin/deleteTheArticle")
    public String admindeleteTheArticle(@RequestParam("username") String username,@RequestParam("title") String title,@RequestParam("token") String token);


        /**
         * 管理员修改博文
         * @param token
         * @param title
         * @param article
         * @param author
         * @return
         */
    @PostMapping("/blog/admin/updateArticle")
    public String adminupdateTheArticle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestBody Article article,@RequestParam("author") String author);
        /**
         * 查询公开博文的数量
         * @return
         */
    @GetMapping("/blog/viewTheNumberOfpublicArticle")
    public String viewTheNumberOfPulbicArticle();

    /**
     * 分页查询数据
     * @param start  开始位置
     * @return  String.class
     */
    @GetMapping("/blog/getPaingQueryDate")
    public String PagingQueryDate(@RequestParam("start")int start);

    /**
     * the admin get all article
     * the admin 获取全部文章
     */
    @PostMapping("/blog/user/adminGetAllPublicArticle")
    public String getAllPublicArticle(@RequestParam("token") String token);

    /**
     * 查询用户发布的公开博文
     * Query a user's public blog posts
     */
    @GetMapping("/blog/user/getUsersPublicarticle")
    public String queryAUsersPublicArticle(@RequestParam("author") String author);

    /**
     * 模糊查询
     */
    @GetMapping("/blog/fuzzyquery")
    public String queryFuzzyQuery(@RequestParam("title") String title);

    /**
     * 修改博文
     */
    @PostMapping("/blog/user/updateTheArticle")
    public String updateTheArticle(@RequestParam("Idemtoken")String Idemtoken,@RequestParam("token")String token,@RequestParam("title") String title,@RequestParam("privacy")String privacy,@RequestParam("technical")String technical,@RequestBody Article article);

    @PostMapping("/blog/user/queryArticleByUsernameAndTitle")
    public String queryArticleByUsernameAndTitle(@RequestParam("token")String token,@RequestParam("title") String title,@RequestParam("author") String author,@RequestParam("technical") String technical  ,@RequestParam("id")Integer id);

    @PostMapping("/blog/user/addAnArticle")
    public String WritingABlog(@RequestParam("Idemtoken")String Idemtoken,@RequestBody Article article);

    @GetMapping("/blog/getSomeAricle")
    public String getSomeArticle();

    @PostMapping("/blog/user/getTheSpecifiedArticle")
    public String getsTheSpecifiedArticle(@RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("technical") String technical ,@RequestParam("id")String id);

    @PostMapping("/blog/user/getAllArticle")
    public String getAllArticle(@RequestParam("author") String author);

    @PostMapping("/blog/user/getAllFolders")
    public String getAllFolders(@RequestParam("author")String author);

    @PostMapping("/blog/user/deleteTheFolders")
    public String deleteTheFolders(@RequestParam("author") String author,@RequestParam("blogfileclass")String blogfileclass);

    /**
     *  删除指定文章
     */
    @PostMapping("blog/user/deleteTheArticle")
    public String deleteTheArticle(@RequestParam("token")String token,@RequestParam("title")String title);

    /**
     * 获取指定文件夹分类中的数据
     */
    @PostMapping("/blog/user/getTheFilesData")
    public String getTheFilesData(@RequestParam("token") String token , @RequestParam("blogfileclass") String blogfileclas);
}
