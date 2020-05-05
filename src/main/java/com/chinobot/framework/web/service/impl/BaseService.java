package com.chinobot.framework.web.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.framework.web.service.IBaseService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService<M extends IBaseMapper<T>, T> extends ServiceImpl<BaseMapper<T>, T> implements IBaseService<T> {

}
