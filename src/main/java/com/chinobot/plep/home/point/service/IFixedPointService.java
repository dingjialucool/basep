package com.chinobot.plep.home.point.service;

import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.dto.FileBusDto;
import com.chinobot.plep.home.point.entity.vo.FlyPointFileVo;
import com.chinobot.plep.home.point.entity.vo.VoPldr;
import com.chinobot.plep.home.point.entity.vo.VoPoint;
import com.chinobot.plep.home.point.entity.vo.VoRoute;
import com.chinobot.plep.home.route.entity.Route;

import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.dto.FileBusDto;
import com.chinobot.plep.home.point.entity.vo.VoPldr;
import com.chinobot.plep.home.point.entity.vo.VoPoint;
import com.chinobot.plep.home.point.entity.vo.VoRoute;
import com.chinobot.plep.home.route.entity.Route;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.dto.FileBusDto;
import com.chinobot.plep.home.point.entity.vo.FixedPointVo;
import com.chinobot.plep.home.point.entity.vo.LnglatVo;
import com.chinobot.plep.home.point.entity.vo.VoPldr;
import com.chinobot.plep.home.point.entity.vo.VoPoint;
import com.chinobot.plep.home.point.entity.vo.VoRoute;
import com.chinobot.plep.home.route.entity.Route;

/**
 * <p>
 * 定点场景关系表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
public interface IFixedPointService extends IBaseService<FixedPoint> {

	List<FixedPointVo> getAllPoint(Map<String, String> param);

	IPage<Map> getAllPoint(Page page, Map<String, String> param);
	
	IPage<Map> getPointPage(Page page, Map<String, Object> param);
	
	void edit(VoPoint vo);

	void del(FixedPoint sp);

	String saveRoute(VoRoute vo);

	void delRoute(Route route);

	List<Map> getAllRouteLine(Map<String, String> param);

	List<Map> getRouteLine(String uuid);

	Map doPldr(VoPldr vo) throws Exception;

	List<Map> getFlyLinePointImg(Map<String, Object> param);

	List<Map> getRangePoint(Map<String, Object> param);

	List<Double> printImageTags(File file) throws Exception;

	Map getFixPointCount(Map<String, Object> param);

	Map getCountOfLine(Map<String, Object> param);

	Map getFixPointCounts(Map<String, Object> param);

	List<Map> getPointInfo(Map<String,Object> pointMap);

	Map getFlyImgList(Page page, Map<String, Object> param);

	List<Map> getFlyImgToZip(Map<String, Object> param);

	List<Map> getFlyImgListNoPage(Map<String, Object> param);

	List<Map> getAllPointNoPage(Map<String, Object> param);

	boolean addPic(FileBusDto dto);

	void saveToLabel(FileBusDto dto);
	
	LnglatVo getGpsByImgId(String id) throws Exception;
}
