package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import com.chinobot.cityle.base.entity.vo.PersonOrganizationVo;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.meeting.entity.vo.PersonVo;

/**
 * <p>
 * 人员 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-03-18
 */
public interface PersonMapper extends IBaseMapper<Person> {
	
	/**
	 * 根据条件获取人员
	 * @param uuid
	 * @return
	 * @author shizt  
	 * @date 2019年4月8日
	 * @company chinobot
	 */
	Map getPersonByParam(@Param("p")Map param);

	IPage<Person> getPersonPage(Page page,@Param("p")Map<String, String> param);
	
	List<Person> getPersonBydeptAndChild(@Param("p")Map param);

	/**
	 * 获取人员
	 * @return
	 */
	List<PersonVo> getAllOpenIdPerson(@Param("deptId") String deptId);
	
	PersonVo personVoById(@Param("uuid") String uuid);

	/**
	 * 根据机构码值获取人员（按部门分组）
	 * @Param: [organization]
	 * @Return: java.util.List<com.chinobot.cityle.base.entity.vo.PersonOrganizationVo>
	 * @Author: shizt
	 * @Date: 2020/1/14 19:58
	 */
	List<PersonOrganizationVo> getPersonByOrganization(@Param("organization") String organization);
	
}
