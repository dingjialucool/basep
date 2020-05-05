package com.chinobot.framework.web.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by huangw on 2019/4/15.
 */
public interface IKafkaDataService {
    /**
     * 更新数据库
     * @param key
     * @param jsonMsg
     * @return
     */
    String updateDataBase(String key, JSONObject jsonMsg);
}
