package com.dazuizui.blog.controller.journey;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.pojo.Journey;
import com.dazuizui.api.pojo.bo.JourneyBo;
import com.dazuizui.blog.serivce.journey.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yida Yang
 * 旅程杯控制器
 */
@RestController
public class JourneyController {
    @Autowired
    private JourneyService journeyService;
    /**
     * 添加旅程碑
     * @return
     */
    @PostMapping("/blog/admin/jouyner/add")
    public String addJourneyDate(@RequestParam("Idemtoken")String Idemtoken, @RequestBody Journey journey){
        return JSONArray.toJSONString(journeyService.addJourneyDate(journey));
    }

    /**
     * 查询AboutDazui的旅程碑
     */
    @GetMapping("/blog/all/getalljouyneyofpublic")
    public String queryAllDataOfJourney(){
        return JSONArray.toJSONString(journeyService.queryAllDataOfJourney("system"));
    }

    /**
     * 查询AboutDazui的旅程碑
     */
    @PostMapping("/blog/all/getprivatejouyneyofpublic")
    public String queryPrivateDataOfJourney(@RequestParam("type")String type){
        System.out.println(type);
        return JSONArray.toJSONString(journeyService.queryPrivateDataOfJourney(type));
    }


    /**
     * 删除指定旅程碑 通过id
     * @param token 管理员token
     * @param id    旅程碑id
     * @return
     */
    @PostMapping("/blog/admin/deleteTheMonumentByid")
    public String deleteTheJournerMonumentById(@RequestParam("token")String token,@RequestParam("id")Integer id){
        return journeyService.deleteTheJournerMonumentById(id);
    }

    /**
     * 通过id获取指定旅程碑信息
     * @param token
     * @param id
     * @return
     */
    @GetMapping("/blog/admin/getTheJourneyMounmentByid")
    public String getTheJourneyMounmentById(@RequestParam("token")String token,@RequestParam("id")Integer id){
        return journeyService.getTheJourneyMounmentById(id);
    }

    @PostMapping("/blog/amin/updateTheJourneyMounmentById")
    public String updateTheJourneyMounenmentById(@RequestParam("token")String token,@RequestBody JourneyBo journeyBo){
        System.out.println(journeyBo);
        return journeyService.updateTheJourneyMounenmentById(journeyBo);
    }
}
