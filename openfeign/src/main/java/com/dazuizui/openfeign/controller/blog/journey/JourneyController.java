package com.dazuizui.openfeign.controller.blog.journey;

import com.dazuizui.api.pojo.Journey;
import com.dazuizui.api.pojo.bo.JourneyBo;
import com.dazuizui.openfeign.service.blog.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class JourneyController {
    @Autowired
    private BlogService blogServer;

    /**
     * 添加里程碑
     * @param Idemtoken
     * @param journey
     * @return
     */
    @PostMapping("/blog/admin/jouyner/add")
    public String addJourneyDate(@RequestParam("Idemtoken")String Idemtoken,@RequestBody Journey journey){
        return blogServer.addJourneyDate(Idemtoken, journey);
    }

    /**
     * 查询AboutDazui的旅程碑
     */
    @GetMapping("/blog/all/getalljouyneyofpublic")
    public String queryAllDataOfJourney(){
        return blogServer.queryAllDataOfJourney();
    }

    /**
     * 删除指定旅程碑 通过id
     * @param token 管理员toke
     * @param id    旅程碑id
     * @return
     */
    @PostMapping("/blog/admin/deleteTheMonumentByid")
    public String deleteTheJournerMonumentById(@RequestParam("token")String token,@RequestParam("id")Integer id){
        return blogServer.deleteTheJournerMonumentById(token,id);
    }


    /**
     * 查询指定旅程碑
     * @param token
     * @param id
     * @return
     */
    @GetMapping("/blog/admin/getTheJourneyMounmentByid")
    public String getTheJourneyMounmentById(@RequestParam("token")String token,@RequestParam("id")Integer id){
        return blogServer.getTheJourneyMounmentById(token, id);
    }

    @PostMapping("/blog/amin/updateTheJourneyMounmentById")
    public String updateTheJourneyMounenmentById(@RequestParam("token")String token, @RequestBody JourneyBo journeyBo){
        return blogServer.updateTheJourneyMounenmentById(token, journeyBo);
    }

    /**
     * 查询AboutDazui的旅程碑里面的私有信息
     */
    @PostMapping("/blog/all/getprivatejouyneyofpublic")
    public String queryPrivateDataOfJourney(@RequestParam("type")String type){
        return blogServer.queryPrivateDataOfJourney(type);
    }
}
