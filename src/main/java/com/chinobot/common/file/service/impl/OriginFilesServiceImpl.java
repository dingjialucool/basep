package com.chinobot.common.file.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.file.entity.OriginFiles;
import com.chinobot.common.file.mapper.OriginFilesMapper;
import com.chinobot.common.file.service.IOriginFilesService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 原始文件 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-06-05
 */
@Service
public class OriginFilesServiceImpl extends BaseService<OriginFilesMapper, OriginFiles> implements IOriginFilesService {
    
	@Autowired
	private FileClient fileClient;
	@Autowired
	private OriginFilesMapper originFilesMapper;
	
    @Override
    public List<OriginFiles> save(MultipartFile file,String catalogId) throws IOException {
        List<OriginFiles> originFiles = fileClient.originFile (file,catalogId);
        for (OriginFiles originFile : originFiles) {
        	super.save(originFile);
		}
        
        return originFiles;
    }

	@Override
	public IPage<Map> getOriginPage(Page page, Map<String, Object> param) {
		return originFilesMapper.getOriginPage(page,param);
	}

	@Override
	public Map<String, String> getLabelPicture(Map<String, Object> param) {
		
		return originFilesMapper.getLabelPicture(param);
	}

	@Override
	public List<OriginFiles> getOriginFilesByTask(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return originFilesMapper.getOriginFilesByTask(param);
	}

}
