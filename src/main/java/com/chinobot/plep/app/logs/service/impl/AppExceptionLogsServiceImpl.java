package com.chinobot.plep.app.logs.service.impl;

import com.chinobot.plep.app.logs.entity.AppExceptionLogs;
import com.chinobot.plep.app.logs.mapper.AppExceptionLogsMapper;
import com.chinobot.plep.app.logs.service.IAppExceptionLogsService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * app错误日志 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-10-27
 */
@Service
public class AppExceptionLogsServiceImpl extends BaseService<AppExceptionLogsMapper, AppExceptionLogs> implements IAppExceptionLogsService {

}
