package com.chinobot.cityle.base.service.impl;

import com.chinobot.cityle.base.entity.UserRole;
import com.chinobot.cityle.base.mapper.UserRoleMapper;
import com.chinobot.cityle.base.service.IUserRoleService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-03-20
 */
@Service
public class UserRoleServiceImpl extends BaseService<UserRoleMapper, UserRole> implements IUserRoleService {

}
