//package com.chinobot.cityle.base.mapper;
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.ibatis.annotations.Param;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.base.entity.Scene;
//import com.chinobot.cityle.base.entity.vo.VoAddressBase;
//import com.chinobot.framework.web.mapper.IBaseMapper;
//
///**
// * <p>
// * 场景 Mapper 接口
// * </p>
// *
// * @author shizt
// * @since 2019-03-16
// */
//public interface SceneMapper extends IBaseMapper<Scene> {
//
//    public IPage<Scene> getSenceImpList(Page page, @Param("param") Map param);
//    
//    /**
//     * 场景 分页
//     * @param page
//     * @param param
//     * @return
//     * @author shizt  
//     * @date 2019年4月1日
//     * @company chinobot
//     */
//    IPage<VoAddressBase<Map>> getSceneAddr(Page page, @Param("p") Map<String, String> param);
//    
//    /**
//     * 根据id获取场景/地址
//     * @param uuid
//     * @return
//     * @author shizt  
//     * @date 2019年3月26日
//     * @company chinobot
//     */
//    VoAddressBase<Map> getSceneAddr(@Param("p") Map<String, String> param);
//    
//	
//	/**
//	 * 获取待修改网格（未关联网格/关联当前网格id）的场景列表
//	 * @return
//	 * @author shizt  
//	 * @date 2019年3月29日
//	 * @company chinobot
//	 */
//	List<Map> getEditGridScene(@Param("gridId") String gridId);
//	
//	
//	/**
//	 * 首页统计数据
//	 * @return
//	 * @author shizt  
//	 * @date 2019年5月15日
//	 * @company chinobot
//	 */
//	LinkedHashMap getHomeStatisticsData();
//	
//	List<Map> getReportSceneList(Map<String, Object> params);
//
//
//	/**
//	 * 首页综合执法数据
//	 * @param param
//	 * @return
//	 */
//	Map getZongHe(Map<String, Object> params);
//
//	List<Map> getReportViolationCount(Map<String, Object> params);
//	
//	List<Map> getReportFiveMonthViolation(Map<String, Object> params);
//
//	List<Map> getReportViolationType(Map<String, Object> params);
//
//	List<Map> getReportIglbuiltCollect(Map<String, Object> params);
//	
//	/**
//	 * 场景的季度数据
//	 * @return
//	 */
//	List<Map> getReportSceneQuarter(Map<String, Object> params);
//
//	/**
//	 * 预警的季度数据
//	 * @return
//	 */
//	List<Map> getReportWarningQuarter(Map<String, Object> params);
//	
//	/**
//	 * 网格的季度数据
//	 * @return
//	 */
//	List<Map> getReportGridQuarter(Map<String, Object> params);
//	
//
//	List<Map> getSceneInfo(Map<String, Object> params);
//}
