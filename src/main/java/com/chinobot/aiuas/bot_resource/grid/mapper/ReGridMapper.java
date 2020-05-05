package com.chinobot.aiuas.bot_resource.grid.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_prospect.warning2info.entity.dto.DeptAreaDTO;
import com.chinobot.aiuas.bot_prospect.warning2info.entity.dto.DeptGridDTO;
import com.chinobot.aiuas.bot_resource.grid.entity.ReGrid;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridGeographyVo;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网格表 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-02-26
 */
public interface ReGridMapper extends IBaseMapper<ReGrid> {

    /**
     * 网格 分页
     *
     * @Author: shizt
     * @Date: 2020/2/26 11:33
     */
    IPage<GridVo> getPage(Page page, @Param("p") Map<String, Object> param);

    /**
     * 根据网格id获取详细
     *
     * @Author: shizt
     * @Date: 2020/2/26 14:33
     */
    GridGeographyVo getInfo(@Param("gridId") String gridId);

    /**
     * 获取周边网格
     * @Author: shizt
     * @Date: 2020/2/27 10:02
     */
    List<GridGeographyVo> getAroundGrid(@Param("p") Map<String, Object> param);

    void delGridByDeptId(@Param("deptId") String deptId);
    
    List<DeptGridDTO> findGridForEvent(@Param("p") Map<String, Object> param);
    
    List<DeptAreaDTO> findAreaForEvent(@Param("p") Map<String, Object> param);
}
