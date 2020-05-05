package com.chinobot.aiuas.bot_collect.result.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.chinobot.aiuas.bot_collect.info.entity.CollectData;
import com.chinobot.aiuas.bot_collect.info.service.ICollectDataService;
import com.chinobot.aiuas.bot_collect.result.entity.Result;
import com.chinobot.aiuas.bot_collect.result.mapper.ResultMapper;
import com.chinobot.aiuas.bot_collect.result.service.IResultService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作业采集结果表 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-03-17
 */
@Service
public class ResultServiceImpl extends BaseService<ResultMapper, Result> implements IResultService {

    @Autowired
    private ResultMapper resultMapper;
    @Autowired
    private ICollectDataService iCollectDataService;

    @Override
    public List<Map> getProgressCollectOptions() {
        return resultMapper.getProgressCollectOptions();
    }

    @Override
    public Map getProgressCollectData(Page page, String[] collectIds) {
        Map result = new HashMap();
        // 工程列表
        IPage<Map> progressCollectList = resultMapper.getProgressCollectList(page, collectIds);
        result.put("list", progressCollectList);
        // 统计
        Map statistics = resultMapper.getStatistics(collectIds);
        statistics.put("infoTotal", progressCollectList.getTotal());
        result.put("statistics", statistics);
        result.put("pie", packagePie(resultMapper.getProgressPieData(collectIds)));

        return result;
    }

    @Override
    public IPage<Map> getFlightWork(Page page, String[] collectIds) {
        return resultMapper.getFlightWork(page, collectIds);
    }

    @Override
    public Map getProgressCollectInfo(String collectId) {
        Map result = new HashMap();
        result.put("info", resultMapper.getProgressCollectInfo(collectId));
        result.put("data", iCollectDataService.lambdaQuery()
                            .select(CollectData::getFileUuid)
                            .eq(CollectData::getCollectUuid, collectId)
                            .eq(CollectData::getIsDeleted, GlobalConstant.IS_DELETED_NO).list());
        return result;
    }

    private List packagePie(List<Map> data){
        List result = new ArrayList();
        List stageList = new ArrayList();
        Map stageMap;
        Map collectData;
        String stage = null;
        for (Map<String, String> d: data) {
            if(null == stage){
                stage = d.get("stage");
            }
            if(!stage.equals(d.get("stage"))){
                stageMap = new HashMap();
                stageMap.put("stage", stage);
                stageMap.put("data", stageList);
                result.add(stageMap);

                stage = d.get("stage");
                stageList = new ArrayList();
            }
            collectData = new HashMap();
            collectData.put("collectId", d.get("collectId"));
            collectData.put("name", d.get("collectName"));
            stageList.add(collectData);
        }
        stageMap = new HashMap();
        stageMap.put("stage", stage);
        stageMap.put("data", stageList);
        result.add(stageMap);

        return result;
    }
}
