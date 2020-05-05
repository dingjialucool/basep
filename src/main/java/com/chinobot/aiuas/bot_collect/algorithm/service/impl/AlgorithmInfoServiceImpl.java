package com.chinobot.aiuas.bot_collect.algorithm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.algorithm.entity.AlgorithmInfo;
import com.chinobot.aiuas.bot_collect.algorithm.entity.AlgorithmInfoExt;
import com.chinobot.aiuas.bot_collect.algorithm.entity.AlgorithmTask;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AlgorithmDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AlgorithmSerachDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AuditingDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmOfAllVo;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmVo;
import com.chinobot.aiuas.bot_collect.algorithm.mapper.AlgorithmInfoMapper;
import com.chinobot.aiuas.bot_collect.algorithm.service.IAlgorithmInfoExtService;
import com.chinobot.aiuas.bot_collect.algorithm.service.IAlgorithmInfoService;
import com.chinobot.aiuas.bot_collect.algorithm.service.IAlgorithmTaskService;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djl
 * @since 2020-03-06
 */
@Service
public class AlgorithmInfoServiceImpl extends BaseService<AlgorithmInfoMapper, AlgorithmInfo> implements IAlgorithmInfoService {

	@Autowired
	private IAlgorithmInfoService algorithmInfoService;
	@Autowired
	private IAlgorithmInfoExtService algorithmInfoExtService;
	@Autowired
	private IAlgorithmTaskService algorithmTaskService;
	@Autowired
	private AlgorithmInfoMapper algorithmInfoMapper;
	
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addAlgorithm(AlgorithmDTO dto) {
		//1.新增/编辑算法
		AlgorithmInfo algorithmInfo = new AlgorithmInfo();
		if (StringUtils.isNotEmpty(dto.getUuid())) {
			algorithmInfo.setUuid(dto.getUuid());
		}
		algorithmInfo.setBusinessStatus(dto.getBusinessStatus());
		algorithmInfo.setAlgorithmName(dto.getAlgorithmName());
		algorithmInfo.setAlgorithmDesc(dto.getAlgorithmDesc());
		algorithmInfo.setAlgorithmVersion(dto.getAlgorithmVersion());
		algorithmInfoService.saveOrUpdate(algorithmInfo);
		//2.新增扩展信息
		AlgorithmInfoExt algorithmInfoExt = null;
		if (StringUtils.isNotEmpty(dto.getUuid())) {
			 algorithmInfoExt = algorithmInfoExtService.getOne(new LambdaQueryWrapper<AlgorithmInfoExt>().eq(AlgorithmInfoExt::getAlgorithmUuid, dto.getUuid()));
		}
		if (algorithmInfoExt == null) {
			algorithmInfoExt = new AlgorithmInfoExt();
		}
		algorithmInfoExt.setAlgorithmUuid(algorithmInfo.getUuid())
		.setAlgorithmicThinking(dto.getAlgorithmicThinking())
		.setNumberStandard(dto.getAlgorithmVersion())
		.setOperationStandard(dto.getOperationStandard());
		algorithmInfoExtService.saveOrUpdate(algorithmInfoExt);
		
		//3.新增算法-采查任务关系
			//先删除已经存在的算法任务
		QueryWrapper<AlgorithmTask> queryWrapper = new QueryWrapper<AlgorithmTask>();
		queryWrapper.eq("algorithm_info_uuid", algorithmInfo.getUuid());
		algorithmTaskService.remove(queryWrapper);
		List<String> collectTaskIdList = dto.getCollectTaskIdList();
		if(collectTaskIdList.size()>0) {
			List<AlgorithmTask> list = new ArrayList<AlgorithmTask>();
			for (String taskId : collectTaskIdList) {
				AlgorithmTask algorithmTask = new AlgorithmTask();
				algorithmTask.setAlgorithmInfoUuid(algorithmInfo.getUuid());
				algorithmTask.setCollectTaskUuid(taskId);
				list.add(algorithmTask);
			}
			algorithmTaskService.saveBatch(list);
		}
	}

	
	@Override
	public List<DomainSceneVo> getDomainTaskTree() {
		
		return algorithmInfoMapper.getDomainTaskTree();
	}

	@Override
	public IPage<AlgorithmVo> getAlgorithmList(AlgorithmSerachDTO dto) {
		
		String businessStatus = dto.getBusinessStatus();
		if(StringUtils.isNotEmpty(businessStatus) ) {
        	
	    	if(businessStatus.indexOf(',')>0) {
	    		String[] split = businessStatus.split(",");
	    		dto.getBusinessStatusArr().add(Arrays.asList(split));
	    	}else {
				String[] split = new String[1];
				split[0] = businessStatus;
				dto.getBusinessStatusArr().add(split);
			}
        }
		
		Page page  = new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		return algorithmInfoMapper.getAlgorithmList(page,dto);
	}

	@Override
	public void auditing(AuditingDTO dto) {
		AlgorithmInfo algorithmInfo = new AlgorithmInfo();
		algorithmInfo.setUuid(dto.getAlgorithmId())
		.setBusinessStatus(dto.getBusinessStatus())
		.setApprovalBy(dto.getPersonId())
		.setApprovalIdea(dto.getAuditingIdea());
		
		algorithmInfoService.saveOrUpdate(algorithmInfo);
		
	}

	@Override
	public AlgorithmOfAllVo getAlgorithmById(String uuid) {
		
		return algorithmInfoMapper.getAlgorithmById(uuid);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeAlgorithm(String uuid) {
		//删除算法
		algorithmInfoService.update(new LambdaUpdateWrapper<AlgorithmInfo>().eq(AlgorithmInfo::getUuid, uuid).set(AlgorithmInfo::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//删除扩展算法
		algorithmInfoExtService.update(new LambdaUpdateWrapper<AlgorithmInfoExt>().eq(AlgorithmInfoExt::getAlgorithmUuid, uuid).set(AlgorithmInfoExt::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//删除算法任务关联
		QueryWrapper<AlgorithmTask> queryWrapper = new QueryWrapper<AlgorithmTask>();
		queryWrapper.eq("algorithm_info_uuid", uuid);
		algorithmTaskService.remove(queryWrapper);
	}

}
