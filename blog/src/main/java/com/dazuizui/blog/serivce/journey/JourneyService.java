package com.dazuizui.blog.serivce.journey;

import com.dazuizui.api.pojo.Journey;
import com.dazuizui.api.pojo.bo.JourneyBo;
import com.dazuizui.api.pojo.vo.JourneyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public interface JourneyService {
    /**
     * 获取指定类型私有的旅程碑
     *
     * @param type
     * @return
     */
    public List<JourneyVo> queryPrivateDataOfJourney(String type);

    /**
     * 添加旅程碑
     * @param journey
     * @return
     */
    public int addJourneyDate(@RequestBody Journey journey);

    /**
     * 获取指定类型的旅程碑
     * @param type
     * @return
     */
    public List<JourneyVo> queryAllDataOfJourney(@Param("type")String type);

    /**
     * 通过id删除指定旅程碑
     * @param id
     * @return
     */
    public String deleteTheJournerMonumentById(Integer id);


    /**
     * 通过id获取指定旅程碑
     * @param id
     * @return
     */
    public String getTheJourneyMounmentById(@Param("id")Integer id);


    public String updateTheJourneyMounenmentById(@RequestBody JourneyBo journeyBo);
}
