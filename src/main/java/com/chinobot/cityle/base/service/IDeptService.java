package com.chinobot.cityle.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 部门 服务类
 * </p>
 *
 * @author laihb
 * @since 2019-03-16
 */
public interface IDeptService extends IBaseService<Dept> {
	
	/**
	 * 部门 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年3月18日
	 * @company chinobot
	 */
	IPage<VoAddressBase<Map>> getDeptAddrPage(Page page, Map<String, String> param);
	
	/**
	 * 根据id获取部门场景
	 * @param id
	 * @return
	 * @author shizt  
	 * @date 2019年3月25日
	 * @company chinobot
	 */
	VoAddressBase<Map> getDeptAddrById(String uuid);
	
	/**
	 * 部门 新增修改
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	Result editDept(VoAddressBase<Dept> voAddressBase);

	/**
	 * 删除部门
	 * @Author: shizt
	 * @Date: 2020/2/27 11:27
	 */
	void delDept(String deptId);


	/**
	 * 同一机构和责任人类型下部门是否唯一
	 * @Author: shizt
	 * @Date: 2020/3/4 15:15
	 */
	List<String> checkDeptUnique(String unitPersonType, String organization);
}
