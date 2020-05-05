package com.chinobot.aiuas.bot_resource.uav.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.aiuas.bot_resource.uav.entity.FittingMessage;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.FittingVo;
import com.chinobot.aiuas.bot_resource.uav.mapper.FittingMessageMapper;
import com.chinobot.aiuas.bot_resource.uav.service.IFittingMessageService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 配件信息表 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
@Service
public class FittingMessageServiceImpl extends BaseService<FittingMessageMapper, FittingMessage> implements IFittingMessageService {

    @Autowired
    private FittingMessageMapper fittingMessageMapper;

    @Override
    public List<FittingVo> getFittingList(Map<String, Object> param) {
        return fittingMessageMapper.getFittingList(param);
    }

    @Override
    public void editFittingList(List<FittingVo> fittingVos, String uavUuid) {
        if(CommonUtils.objNotEmpty(fittingVos) && fittingVos.size() > 0) {
            for (FittingVo f : fittingVos) {
                FittingMessage fittingMessage = new FittingMessage();
                fittingMessage.setUuid(f.getUuid())
                        .setUavUuid(uavUuid)
                        .setFitName(f.getFitName())
                        .setFitNumber(f.getFitNumber());
                saveOrUpdate(fittingMessage);
            }
        }
    }

    @Override
    public void delFittingByUavUuid(String uavUuid) {
        UpdateWrapper<FittingMessage> fittingMessageUpdateWrapper = new UpdateWrapper<>();
        fittingMessageUpdateWrapper.eq("uav_uuid", uavUuid);

        FittingMessage fittingMessage = new FittingMessage();
        fittingMessage.setIsDeleted(GlobalConstant.IS_DELETED_YES);

        update(fittingMessage, fittingMessageUpdateWrapper);
    }
}
