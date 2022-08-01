package com.dazuizui.blog.mapper.journey;

import com.dazuizui.api.pojo.Journey;
import com.dazuizui.api.pojo.bo.JourneyBo;
import com.dazuizui.api.pojo.vo.JourneyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface JourneyMapper {
    /**
     * 添加旅程碑
     * @param journey
     * @return
     */
    public int addJourneyDate(Journey journey);

    /**
     * 查询指定类型的旅程碑
     * @param type
     * @return
     */
    public List<JourneyVo> queryAllDataOfJourney(@Param("type")String type);

    /**
     * 删除指定旅程碑通过id
     * @param id 旅程碑id
     * @return
     */
    public int deleteTheJournerMonumentById(@Param("id")Integer id);


    /**
     * 通过id获取指定旅程碑
     * @param id
     * @return
     */
    public JourneyVo getTheJourneyMounmentById(@Param("id")Integer id);

    /**
     * 通过id修改指定旅程碑
     * @param journeyBo
     * @return
     */
    public int updateTheJourneyMounenmentById(JourneyBo journeyBo);
}
