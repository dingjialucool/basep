//package com.chinobot.cityle.warning.service;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.ibatis.annotations.Param;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.warning.entity.PushMessage;
//import com.chinobot.framework.web.service.IBaseService;
//
///**
// * <p>
// * 推送信息表 服务类
// * </p>
// *
// * @author dingjl
// * @since 2019-03-16
// */
//public interface IPushMessageService extends IBaseService<PushMessage> {
//	
//	/**
//	 *	 推送信息已被确认
//	 * @param param
//	 * @return
//	 */
//	IPage<Map> getYesPushMessage(Page page, Map param);
//
//	/**
//	 * 	推送信息未推送
//	 * @param page
//	 * @param param
//	 * @param pushStatus
//	 * @param confireStatus
//	 * @return
//	 */
//	IPage<Map> getNoPushMessage(Page page, Map<String, Object> param);
//
//	/**
//	 *	 获取实时预警信息
//	 * @param page
//	 * @param param
//	 */
//	IPage<Map>  getRealTimeMessage(Page page, Map<String, Object> param);
//
//	/**
//	 * 	获取历史预警信息
//	 * @param page
//	 * @param param
//	 * @return
//	 */
//	IPage<Map> getHistoryTimeMessage(Page page, Map<String, Object> param);
//
//	/**
//	 * 	获取文件ID与类型
//	 * @param param
//	 * @return
//	 */
//	List<LinkedHashMap<String, String>> getFileId(Map<String, Object> param);
//
//	/**
//	 * 	再次推送预警信息
//	 * @param param
//	 * @return
//	 */
//	boolean pushMessageAgain(Map<String, Object> param);
//}
