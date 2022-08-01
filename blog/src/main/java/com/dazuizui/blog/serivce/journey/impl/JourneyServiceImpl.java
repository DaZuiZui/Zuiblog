package com.dazuizui.blog.serivce.journey.impl;

import com.alibaba.fastjson.JSONArray;
import com.dazuizui.api.pojo.Journey;
import com.dazuizui.api.pojo.bo.JourneyBo;
import com.dazuizui.api.pojo.vo.JourneyVo;
import com.dazuizui.blog.mapper.journey.JourneyMapper;
import com.dazuizui.blog.serivce.journey.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JourneyServiceImpl implements JourneyService {
    @Autowired
    private JourneyMapper journeyMapper;

    /**
     * 添加旅程碑
     * @param journey
     * @return
     */
    @Override
    public int addJourneyDate(Journey journey) {
        //非空判断
        if (journey != null) {
            journey.setCreate_time(new Date());
            journey.setUpdate_time(new Date());
            return journeyMapper.addJourneyDate(journey);
        }
        return 0;
    }

    /**
     * 获取指定类型的旅程碑
     *
     * @param type
     * @return
     */
    @Override
    public List<JourneyVo> queryAllDataOfJourney(String type) {
        List<JourneyVo> system = journeyMapper.queryAllDataOfJourney("system");

        return system;
    }


    /**
     * 获取指定类型私有的旅程碑
     *
     * @param type
     * @return
     */
    @Override
    public List<JourneyVo> queryPrivateDataOfJourney(String type) {
        List<JourneyVo> system = journeyMapper.queryAllDataOfJourney(type);

        return system;
    }

    /**
     * 通过id删除指定旅程碑
     * @param id
     * @return
     */
    public String deleteTheJournerMonumentById(Integer id){
        return JSONArray.toJSONString(journeyMapper.deleteTheJournerMonumentById(id));
    }

    /**
     * 通过id获取指定旅程碑
     * @param id
     * @return
     */
    @Override
    public String getTheJourneyMounmentById(Integer id) {
        JourneyVo tmp = journeyMapper.getTheJourneyMounmentById(id);
        System.err.println(tmp.getJourneyorder());
        System.out.println(tmp);
        return JSONArray.toJSONString(tmp);
    }

    /**
     * 通过id修改博文
     * @param journeyBo
     * @return
     */
    @Override
    public String updateTheJourneyMounenmentById(JourneyBo journeyBo) {
        journeyBo.setUpdateTime(new Date());
        return JSONArray.toJSONString(journeyMapper.updateTheJourneyMounenmentById(journeyBo));
    }
}
