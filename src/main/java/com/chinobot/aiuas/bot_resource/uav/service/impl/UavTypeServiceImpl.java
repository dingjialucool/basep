package com.chinobot.aiuas.bot_resource.uav.service.impl;

import com.chinobot.aiuas.bot_resource.uav.entity.UavType;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeListVo;
import com.chinobot.aiuas.bot_resource.uav.mapper.UavTypeMapper;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.aiuas.bot_resource.uav.service.IUavTypeService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机型 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
@Service
public class UavTypeServiceImpl extends BaseService<UavTypeMapper, UavType> implements IUavTypeService {
    @Autowired
    private UavTypeMapper uavTypeMapper;
    @Autowired
    private IReUavService iReUavService;
    @Autowired
    private IFileBusService iFileBusService;
    /**
     * 文件模块名
     */
    final static String FILE_MODULE = "uav_type_img";

    @Override
    public List<UavTypeListVo> getUavTypeList(Map<String, Object> param) {
        return uavTypeMapper.getUavTypeList(param);
    }

    @Override
    public UavTypeInfoVo getUavTypeInfo(Map<String, Object> param) {
        UavTypeInfoVo uavTypeInfoVo = uavTypeMapper.getUavType(param);

        Map fileParam = new HashMap<>();
        fileParam.put("busId", (String) param.get("uuid"));
        fileParam.put("module", "uav_type_img");
        uavTypeInfoVo.setFiles(iFileBusService.getFileBusList(fileParam));

        return uavTypeInfoVo;
    }

    @Override
    public String editUavType(UavTypeInfoVo uavTypeInfoVo) {
        UavType uavType = new UavType();
        uavType.setUuid(uavTypeInfoVo.getUuid())
                .setModuleName(uavTypeInfoVo.getModuleName())
                .setType(uavTypeInfoVo.getType())
                .setMaxLength(uavTypeInfoVo.getMaxLength())
                .setMaxWidth(uavTypeInfoVo.getMaxWidth())
                .setMaxHeight(uavTypeInfoVo.getMaxHeight())
                .setMaxWeight(uavTypeInfoVo.getMaxWeight())
                .setUavDescription(uavTypeInfoVo.getUavDescription())
                .setMaxLoad(uavTypeInfoVo.getMaxLoad())
                .setMaxWindSpeed(uavTypeInfoVo.getMaxWindSpeed())
                .setMaxFlyTimeMin(uavTypeInfoVo.getMaxFlyTimeMin())
                .setJobEnvironmentLowerTemperature(uavTypeInfoVo.getJobEnvironmentLowerTemperature())
                .setJobEnvironmentHigherTemperature(uavTypeInfoVo.getJobEnvironmentHigherTemperature())
                .setFirmName(uavTypeInfoVo.getFirmName())
                .setLinkPerson(uavTypeInfoVo.getLinkPerson())
                .setLinkPhone(uavTypeInfoVo.getLinkPhone());
        saveOrUpdate(uavType);

        iFileBusService.saveFileBusList(uavTypeInfoVo.getFiles(), uavType.getUuid(), FILE_MODULE);

        return uavType.getUuid();
    }

    @Override
    public void delUavType(String uuid) {
        // 机型
        UavType uavType = new UavType();
        uavType.setUuid(uuid)
                .setIsDeleted(GlobalConstant.IS_DELETED_YES);
        updateById(uavType);
        // 无人机
        iReUavService.delUavByUavTypeId(uuid);
        // 相册
        iFileBusService.delFileBus(uuid, FILE_MODULE);
    }

}
