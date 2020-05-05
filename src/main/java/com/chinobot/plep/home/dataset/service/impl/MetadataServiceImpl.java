package com.chinobot.plep.home.dataset.service.impl;

import com.chinobot.plep.home.dataset.entity.Metadata;
import com.chinobot.plep.home.dataset.mapper.MetadataMapper;
import com.chinobot.plep.home.dataset.service.IMetadataService;
import com.chinobot.framework.web.service.impl.BaseService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 元数据表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
@Service
public class MetadataServiceImpl extends BaseService<MetadataMapper, Metadata> implements IMetadataService {

}
