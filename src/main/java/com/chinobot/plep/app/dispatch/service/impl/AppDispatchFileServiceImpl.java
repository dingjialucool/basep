package com.chinobot.plep.app.dispatch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.plep.app.dispatch.entity.AppDispatchFile;
import com.chinobot.plep.app.dispatch.mapper.AppDispatchFileMapper;
import com.chinobot.plep.app.dispatch.service.IAppDispatchFileService;
import com.chinobot.framework.web.service.impl.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * app调度预警文件 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-08-13
 */
@Service
public class AppDispatchFileServiceImpl extends BaseService<AppDispatchFileMapper, AppDispatchFile> implements IAppDispatchFileService {
	
	@Autowired
	private AppDispatchFileMapper appDispatchFileMapper;
	
    @Override
    public Result saveOrUpdateAppDispatchFiles(List<AppDispatchFile> appDispatchFiles) {
        QueryWrapper<AppDispatchFile> queryWrapper;
        for (AppDispatchFile a:  appDispatchFiles) {
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("uav_id",  a.getUavId());
            queryWrapper.eq("file_name",  a.getFileName());
            queryWrapper.eq("date_created",  a.getDateCreated());
            if(super.count(queryWrapper) == 0){
                super.saveOrUpdate(a);
            }
        }
        return ResultFactory.success();
    }

	@Override
	public AppDispatchFile getDispatchFiles(Map<String, Object> param) {
		// TODO Auto-generated method stub
        return appDispatchFileMapper.getDispatchFiles(param);
	}
}
