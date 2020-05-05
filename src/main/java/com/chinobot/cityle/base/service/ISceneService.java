//package com.chinobot.cityle.base.service;
//
//import java.util.List;
//import java.util.Map;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.base.entity.Scene;
//import com.chinobot.cityle.base.entity.vo.VoAddressBase;
//import com.chinobot.common.domain.Result;
//import com.chinobot.framework.web.service.IBaseService;
//
///**
// * <p>
// * 场景 服务类
// * </p>
// *
// * @author shizt
// * @since 2019-03-16
// */
//public interface ISceneService extends IBaseService<Scene> {
//
//	/**
//	 * 场景 分页
//	 * @param page
//	 * @param param
//	 * @return
//	 * @author shizt  
//	 * @date 2019年3月18日
//	 * @company chinobot
//	 */
//	IPage<Scene> getScenePage(Page page, Map<String, String> param);
//	
//	/**
//	 * 场景/地址 分页
//	 * @param page
//	 * @param param
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月1日
//	 * @company chinobot
//	 */
//	IPage<VoAddressBase<Map>> getSceneAddrPage(Page page, Map<String, String> param);
//	
//	/**
//	 * 根据id获取场景/地址
//	 * @param uuid
//	 * @return
//	 * @author shizt  
//	 * @date 2019年3月26日
//	 * @company chinobot
//	 */
//	VoAddressBase<Map> getSceneAddrByid(String uuid);
//	
//	/**
//	 * 获取待修改网格（未关联网格/关联当前网格id）的场景列表
//	 * @return
//	 * @author shizt  
//	 * @date 2019年3月29日
//	 * @company chinobot
//	 */
//	List<Map> getEditGridScene(String gridId);
//	
//	/**
//	 * 场景 新增修改
//	 * @param voAddressBase
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月2日
//	 * @company chinobot
//	 */
//	Result editScene(VoAddressBase<Scene> voAddressBase);
//
//	/**
//	 *  首页综合执法数据
//	 * @param param
//	 */
//	Map getZongHe(Map<String, Object> param);
//
//
//	/**
//	 * 获取场景列表
//	 * @param params
//	 * @return
//	 */
//	List<Map> getReportSceneList(Map<String, Object> params);
//	
//	
//	/**
//	 * 获取场景详细信息
//	 * @param params
//	 * @return
//	 */
//	Map getSceneInfo(Map<String, Object> params);
//	
//	/**
//	 * 违章统计
//	 * @param params
//	 * @return
//	 */
//	Map getReportViolationCount(Map<String, Object> params);
//	
//	/**
//	 * 近5月违章数
//	 * @param params
//	 * @return
//	 */
//	Map getReportFiveMonthViolation(Map<String, Object> params);
//
//	/**
//	 *   违章类型
//	 * @param params
//	 * @return
//	 */
//	Map getReportViolationType(Map<String, Object> params);
//
//	/**
//	 *   违章类型-雷达图
//	 * @param params
//	 * @return
//	 */
//	Map getReportViolationTypeRadar(Map<String, Object> params);
//
//	/**
//	 *   违建汇总
//	 * @param params
//	 * @return
//	 */
//	Map getReportIglbuiltCollect(Map<String, Object> params);
//	
//	/**
//	 * 执法统计季度报表
//	 * @return
//	 */
//	Map getReportLawefcQuarter(Map<String, Object> params);
//	
//}
