package com.chinobot.aiuas.bot_collect.algorithm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.algorithm.entity.AlgorithmInfo;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AlgorithmSerachDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmOfAllVo;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmVo;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2020-03-06
 */
public interface AlgorithmInfoMapper extends IBaseMapper<AlgorithmInfo> {

	/**
	 * 获取领域-场景-任务树
	 * @return
	 */
	List<DomainSceneVo> getDomainTaskTree();

	/**
	 * 算法管理列表与查询
	 * @param page
	 * @param dto
	 * @return
	 */
	IPage<AlgorithmVo> getAlgorithmList(Page page,@Param("p") AlgorithmSerachDTO dto);

	/**
	 * 编辑回显
	 * @param uuid
	 * @return
	 */
	AlgorithmOfAllVo getAlgorithmById(@Param("uuid")String uuid);

}
