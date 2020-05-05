package com.chinobot.cityle.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.vo.PersonOrganizationVo;
import com.chinobot.cityle.base.entity.vo.PersonRole;
import com.chinobot.framework.web.service.IBaseService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 人员 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-03-18
 */
public interface IPersonService extends IBaseService<Person> {

	/**
	 * 人员 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年3月18日
	 * @company chinobot
	 */
	IPage<Person> getPersonPage(Page page, Map<String, String> param);

	/**
	 * 根据条件获取人员
	 * @param uuid
	 * @return
	 * @author shizt  
	 * @date 2019年4月8日
	 * @company chinobot
	 */
	Map getPersonByParam(Map param);
	
	/**
	 * 人员角色 新增修改
	 * @param personRole
	 * @author shizt  
	 * @date 2019年3月21日
	 * @company chinobot
	 */
	void saveOrUpdatePersonRole(PersonRole personRole);
	
	/**
	 * 判断原密码是否正确
	 * @param personRole
	 * @author zll  
	 * @date 2019年5月5日
	 * @company chinobot
	 */
	boolean passWordJudge(PersonRole personRole);

	/**
	 * 根据机构码值获取人员（按部门分组）
	 * @Param: [organization]
	 * @Return: java.util.List<com.chinobot.cityle.base.entity.vo.PersonOrganizationVo>
	 * @Author: shizt
	 * @Date: 2020/1/14 19:58
	 */
	List<PersonOrganizationVo> getPersonByOrganization(String organization);
}
