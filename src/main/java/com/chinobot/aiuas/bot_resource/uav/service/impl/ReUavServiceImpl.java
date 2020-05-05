package com.chinobot.aiuas.bot_resource.uav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.entity.UavType;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavListVo;
import com.chinobot.aiuas.bot_resource.uav.mapper.ReUavMapper;
import com.chinobot.aiuas.bot_resource.uav.service.IFittingMessageService;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.aiuas.bot_resource.uav.service.IUavTypeService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 无人机 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
@Service
public class ReUavServiceImpl extends BaseService<ReUavMapper, Uav> implements IReUavService {

    @Autowired
    private ReUavMapper reUavMapper;
    @Autowired
    private IUavTypeService iUavTypeService;
    @Autowired
    private IFittingMessageService iFittingMessageService;
    @Autowired
    private IFileBusService iFileBusService;
    /**
     * 文件模块名
     */
    final static String FILE_MODULE = "uav_img";

    @Override
    public IPage<UavListVo> getUavList(Page page, Map<String, Object> param) {
        return reUavMapper.getUavList(page, param);
    }

    @Override
    public UavInfoVo getUavInfo(Map<String, Object> param) {
        if(((String) param.get("type")).equals("add")){
            UavType uavType = iUavTypeService.getById((String) param.get("uavTypeUuid"));
            UavInfoVo uavInfoVo = new UavInfoVo();
            uavInfoVo.setUavTypeUuid(uavType.getUuid());
            uavInfoVo.setModuleName(uavType.getModuleName());

            return uavInfoVo;
        }

        // 无人机
        UavInfoVo uavInfo = reUavMapper.getUavInfo(param);
        if(CommonUtils.objNotEmpty(uavInfo)){
            // 设备
            Map fitParam = new HashMap();
            fitParam.put("uavUuid", (String) param.get("uuid"));
            uavInfo.setFittingVoList(iFittingMessageService.getFittingList(fitParam));
            // 相册
            Map fileParam = new HashMap<>();
            fileParam.put("busId", (String) param.get("uuid"));
            fileParam.put("module", "uav_img");
            uavInfo.setFiles(iFileBusService.getFileBusList(fileParam));
        }

        return uavInfo;
    }

    @Override
    public String editUav(UavInfoVo uavInfoVo) {
        // 无人机
        Uav uav = new Uav();
        uav.setUuid(uavInfoVo.getUuid())
            .setEname(uavInfoVo.getEname())
            .setSerialNumber(uavInfoVo.getSerialNumber())
            .setUavTypeUuid(uavInfoVo.getUavTypeUuid())
            .setPersonUuid(uavInfoVo.getPersonId());
        saveOrUpdate(uav);
        // 设备
        iFittingMessageService.editFittingList(uavInfoVo.getFittingVoList(), uav.getUuid());
        // 相册
        iFileBusService.saveFileBusList(uavInfoVo.getFiles(), uav.getUuid(), FILE_MODULE);

        return uav.getUuid();
    }

    @Override
    public void delUav(String uuid) {
        // 删除无人机
        Uav uav = new Uav();
        uav.setUuid(uuid)
            .setIsDeleted(GlobalConstant.IS_DELETED_YES);
        updateById(uav);
        // 删除配件
        iFittingMessageService.delFittingByUavUuid(uuid);
        // 删除相册
        iFileBusService.delFileBus(uuid, FILE_MODULE);
    }

    @Override
    public void delUavByUavTypeId(String uavTypeId) {
        QueryWrapper<Uav> uavQueryWrapper = new QueryWrapper<>();
        uavQueryWrapper.eq("uav_type_uuid", uavTypeId)
            .eq("is_deleted", GlobalConstant.IS_DELETED_NO);
        List<Uav> uavs = list(uavQueryWrapper);

        for (Uav u : uavs) {
            // 删除无人机
            Uav uav = new Uav();
            uav.setUuid(u.getUuid())
                .setIsDeleted(GlobalConstant.IS_DELETED_YES);
            updateById(uav);
            // 删除配件
            iFittingMessageService.delFittingByUavUuid(uav.getUuid());
            // 删除相册
            iFileBusService.delFileBus(uav.getUuid(), FILE_MODULE);
        }
    }

    @Override
    public List<Map> getReUavList(Map<String, Object> param) {
        return reUavMapper.getReUavList(param);
    }

    @Override
    public IPage<HomePageUavVo> getReUavPage(Page page, Map<String, Object> param) {
        return reUavMapper.getReUavPage(page, param);
    }

    @Override
    public List<HomePageUavVo> getReUavPage(Map<String, Object> param) {
        return reUavMapper.getReUavPage(param);
    }
}
