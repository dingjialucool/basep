package com.chinobot.aiuas.bot_resource.grid.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.base.entity.Geography;
import com.chinobot.aiuas.bot_collect.base.mapper.GeographyMapper;
import com.chinobot.aiuas.bot_collect.base.service.IGeographyService;
import com.chinobot.aiuas.bot_collect.info.entity.dto.GeographyDTO;
import com.chinobot.aiuas.bot_resource.grid.entity.ReGrid;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridGeographyVo;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridVo;
import com.chinobot.aiuas.bot_resource.grid.mapper.ReGridMapper;
import com.chinobot.aiuas.bot_resource.grid.service.IReGridService;
import com.chinobot.cityle.base.entity.Grid;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网格表 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-02-26
 */
@Service
public class ReGridServiceImpl extends BaseService<ReGridMapper, ReGrid> implements IReGridService {

    @Autowired
    private ReGridMapper gridMapper;
    @Autowired
    private GeographyMapper geographyMapper;
    @Autowired
    private IGeographyService geographyService;

    /**
     * 地理信息业务类型
     */
    private static final String BUSI_TYPE = "bot_resource_grid";

    @Override
    public IPage<GridVo> getPage(Page page, Map<String, Object> param) {
        return gridMapper.getPage(page, param);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void edit(GridGeographyVo gridGeographyVo) {
        // 网格
        GridVo gridVo = gridGeographyVo.getGrid();
        ReGrid grid = new ReGrid()
                .setUuid(gridVo.getUuid())
                .setName(gridVo.getName())
                .setContent(gridVo.getContent())
                .setDeptId(gridVo.getDeptId())
                .setIsDeleted(gridVo.getIsDeleted());
        saveOrUpdate(grid);

        // 地理信息
        GeographyDTO geographyDto = gridGeographyVo.getGeography();
        if(CommonUtils.objNotEmpty(geographyDto)){
            Geography geography = null;
            // 判断是删除还是新增修改
            if(StringUtils.isNotEmpty(geographyDto.getGeographyId())
                    && CommonUtils.objNotEmpty(grid.getIsDeleted())
                    && grid.getIsDeleted().equals(GlobalConstant.IS_DELETED_YES)){
                geography = new Geography();
                geography.setUuid(geographyDto.getGeographyId())
                        .setIsDeleted(GlobalConstant.IS_DELETED_YES);
            }else{
                geography = packGeography(geographyDto, grid.getUuid());
            }
            geographyService.saveOrUpdate(geography);
        }
    }

    @Override
    public GridGeographyVo getInfo(String gridId) {
        return gridMapper.getInfo(gridId);
    }

    @Override
    public List<GridGeographyVo> getAroundGrid(Map<String, Object> param) {
        return gridMapper.getAroundGrid(param);
    }

    /**
     * 地理信息封装
     * @param geographyDto
     * @return
     */
    private Geography packGeography(GeographyDTO geographyDto, String busiId) {
        Geography geography = new Geography();
        if(StringUtils.isNotEmpty(geographyDto.getGeographyId())) {
            geography.setUuid(geographyDto.getGeographyId());
        }else {
            //获取该业务类型最大的序号
            Long maxSort = geographyMapper.getMaxSort(BUSI_TYPE);
            Integer sort = (int) (maxSort+1);
            geography.setSort(sort);
        }
        geography.setBusiId(busiId);
        geography.setBusiType(BUSI_TYPE);
        geography.setGeoType("polygon");
        geography.setLnglats(geographyDto.getLnglats());
        geography.setLat(geographyDto.getLat());
        geography.setLng(geographyDto.getLng());
        return geography;
    }
}
