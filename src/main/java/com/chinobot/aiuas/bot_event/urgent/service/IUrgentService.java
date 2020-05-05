package com.chinobot.aiuas.bot_event.urgent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_event.urgent.entity.Urgent;
import com.chinobot.aiuas.bot_event.urgent.entity.dto.HistoryUrgentDTO;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.HistoryUrgentVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.PersonListVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.UrgentWorkStatusVo;
import com.chinobot.framework.web.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 紧急调度表 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-03-19
 */
public interface IUrgentService extends IBaseService<Urgent> {

    /**
     * 新增
     * @Author: shizt
     * @Date: 2020/3/19 10:54
     */
    String add(Urgent urgent) throws Exception;

    /**
     * 分页
     * @Author: shizt
     * @Date: 2020/3/20 11:03
     */
    IPage<Map> getPage(Page page);

    /**
     * 详细
     * @Author: shizt
     * @Date: 2020/3/20 14:35
     */
    Map getInfo(String urgentId);
    
    /**
     * 紧急调度修改状态
     * @Author: shizt
     * @Date: 2020/3/20 16:54
     */
    void editWorkStatus(UrgentWorkStatusVo urgentWorkStatusVo) throws Exception;

    /**
     * 历史调度列表
     * @param dto
     * @return
     */
	IPage<HistoryUrgentVo> getHistoryUrgentList(HistoryUrgentDTO dto);

    /**
     * app 紧急调度列表
     * @Author: shizt
     * @Date: 2020/3/24 16:15
     */
    List<Map> getAppUrgentList(Map<String, String> param);

    /**
     * 紧急调度 航线
     * @Author: shizt
     * @Date: 2020/3/24 16:57
     */
    Map getRouteByUrgent(String urgentId);

    /**
     * 获取紧急调度列表执行人与发起人
     * @param type
     * @return
     */
	List<PersonListVo> getPersons(String type);
}
