package com.chinobot.common.file.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 文件 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-03-19
 */
public interface IUploadFileService extends IBaseService<UploadFile> {

    List<UploadFile> save(MultipartFile file) throws IOException;
    
    List<UploadFile> save(File[] files) throws Exception;
    
    UploadFile save(File file) throws Exception;
}
