package com.chinobot.aiuas.bot_event.urgent.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_event.urgent.entity.Urgent;
import com.chinobot.aiuas.bot_event.urgent.entity.dto.HistoryUrgentDTO;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.HistoryUrgentVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.PersonListVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 紧急调度表 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-03-19
 */
public interface UrgentMapper extends IBaseMapper<Urgent> {

    /**
     * 分页
     * @Author: shizt
     * @Date: 2020/3/20 11:02
     */
    IPage<Map> getPage(Page page);

    /**
     * 详细
     * @Author: shizt
     * @Date: 2020/3/20 14:57
     */
    Map getInfo(@Param("urgentId") String urgentId);

    /**
     * 历史调度列表
     * @param page
     * @param dto
     * @return
     */
	IPage<HistoryUrgentVo> getHistoryUrgentList(Page page, @Param("p")HistoryUrgentDTO dto);

    /**
     * app 紧急调度列表
     * @Author: shizt
     * @Date: 2020/3/24 16:16
     */
    List<Map> getAppUrgentList(@Param("p") Map<String, String> param);

    /**
     * 紧急调度 航线
     * @Author: shizt
     * @Date: 2020/3/24 16:58
     */
    Map getRouteByUrgent(@Param("urgentId") String urgentId);

    /**
     * 获取紧急调度列表发起人
     * @param type
     * @return
     */
	List<PersonListVo> getPersons();
	
	/**
     * 获取紧急调度列表执行人
     * @param type
     * @return
     */
	List<PersonListVo> getDoPersons();
}
