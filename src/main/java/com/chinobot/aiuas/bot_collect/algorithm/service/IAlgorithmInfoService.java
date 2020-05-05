package com.chinobot.aiuas.bot_collect.algorithm.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinobot.aiuas.bot_collect.algorithm.entity.AlgorithmInfo;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AlgorithmDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AlgorithmSerachDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AuditingDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmOfAllVo;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djl
 * @since 2020-03-06
 */
public interface IAlgorithmInfoService extends IBaseService<AlgorithmInfo> {

	/**
	 * 算法管理新增
	 * @param dto
	 */
	void addAlgorithm(AlgorithmDTO dto);

	/**
	 * 获取领域-场景-任务树
	 * @return
	 */
	List<DomainSceneVo> getDomainTaskTree();

	/**
	 * 算法管理列表与查询
	 * @param dto
	 * @return
	 */
	IPage<AlgorithmVo> getAlgorithmList(AlgorithmSerachDTO dto);

	/**
	 * 算法审批
	 * @param dto
	 */
	void auditing(AuditingDTO dto);

	/**
	 * 编辑回显
	 * @param uuid
	 * @return
	 */
	AlgorithmOfAllVo getAlgorithmById(String uuid);

	/**
	 * 算法删除
	 * @param uuid
	 */
	void removeAlgorithm(String uuid);

}
