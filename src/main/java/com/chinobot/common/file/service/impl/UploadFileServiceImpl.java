package com.chinobot.common.file.service.impl;

import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.mapper.UploadFileMapper;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.framework.web.service.impl.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 文件 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-03-19
 */
@Service
public class UploadFileServiceImpl extends BaseService<UploadFileMapper, UploadFile> implements IUploadFileService {

	@Autowired
	private FileClient fileClient;
	
    @Override
    public List<UploadFile> save(MultipartFile file) throws IOException {
        List<UploadFile> uploadFiles = fileClient.uploadFile (file);
        for (UploadFile uploadFile : uploadFiles) {
        	super.save(uploadFile);
		}
        
        return uploadFiles;
    }

	@Override
	public List<UploadFile> save(File[] files) throws Exception {
		List<UploadFile> uploadFiles = fileClient.uploadFile (files);
        for (UploadFile uploadFile : uploadFiles) {
        	super.save(uploadFile);
		}
        
        return uploadFiles;
	}

	@Override
	public UploadFile save(File file) throws Exception {
		UploadFile uploadFile = fileClient.uploadFile(file);
		return uploadFile;
	}
}
