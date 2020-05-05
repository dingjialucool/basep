package com.chinobot.plep.app.dispatch.service;

import java.util.List;
import java.util.Map;

import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.app.dispatch.entity.AppDispatchFile;

/**
 * <p>
 * app调度预警文件 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-08-13
 */
public interface IAppDispatchFileService extends IBaseService<AppDispatchFile> {

    Result saveOrUpdateAppDispatchFiles(List<AppDispatchFile> appDispatchFiles);

	AppDispatchFile getDispatchFiles(Map<String, Object> param);
	
}
