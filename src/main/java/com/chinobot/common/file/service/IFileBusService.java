package com.chinobot.common.file.service;

import java.util.List;
import java.util.Map;

import com.chinobot.common.file.entity.FileBus;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 业务文件关联 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-04-08
 */
public interface IFileBusService extends IBaseService<FileBus> {
    
    /**
     * 根据业务id获取文件id列表
     * @param busId
     * @return
     * @author shizt  
     * @date 2019年4月8日
     * @company chinobot
     */
    List<Map> getFileIdByBusId(Map param);

    /**
     * 业务文件关联 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.common.file.entity.FileBus>
     * @Author: shizt
     * @Date: 2020/1/14 15:11
     */
    List<FileBus> getFileBusList(Map<String, Object> param);

    /**
     * 业务文件关联 保存
     * @Param: [fileBus, busId, module]
     * @Return: boolean
     * @Author: shizt
     * @Date: 2020/1/16 9:43
     */
    boolean saveFileBusList(List<FileBus> fileBus, String busId, String module);

    /**
     * 业务文件关联 删除
     * @Param: [busId, module]
     * @Return: boolean
     * @Author: shizt
     * @Date: 2020/1/17 10:39
     */
    boolean delFileBus(String busId, String module);
}
