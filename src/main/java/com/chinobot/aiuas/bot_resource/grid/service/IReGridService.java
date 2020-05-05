package com.chinobot.aiuas.bot_resource.grid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_resource.grid.entity.ReGrid;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridGeographyVo;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridVo;
import com.chinobot.framework.web.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网格表 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-02-26
 */
public interface IReGridService extends IBaseService<ReGrid> {

    /**
     * 网格 分页
     * @Author: shizt
     * @Date: 2020/2/26 11:33
     */
    IPage<GridVo> getPage(Page page, Map<String, Object> param);

    /**
     * 编辑网格
     * @Author: shizt
     * @Date: 2020/2/26 14:04
     */
    void edit(GridGeographyVo gridGeographyVo);

    /**
     * 根据网格id获取详细
     * @Author: shizt
     * @Date: 2020/2/26 14:05
     */
    GridGeographyVo getInfo(String gridId);

    /**
     * 获取周边网格
     * @param bounds: 地图边界范围坐标
     * @Author: shizt
     * @Date: 2020/2/27 9:30
     */
    List<GridGeographyVo> getAroundGrid(Map<String, Object> param);
}
