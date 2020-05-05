package com.chinobot.plep.home.area.service.impl;

import com.chinobot.plep.home.area.constant.AreaConstant;
import com.chinobot.plep.home.area.entity.GovArea;
import com.chinobot.plep.home.area.mapper.GovAreaMapper;
import com.chinobot.plep.home.area.service.IGovAreaService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-09-10
 */
@Service
public class GovAreaServiceImpl extends BaseService<GovAreaMapper, GovArea> implements IGovAreaService {
	@Autowired
	private GovAreaMapper govAreaMapper;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveGovArea(GovArea govArea) {
		String sourceLnglatSystem = govArea.getSourceLnglatSystem();
		String sourceBoundary = govArea.getSourceBoundary();
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	     /*\n 回车(\u000a)
	     \t 水平制表符(\u0009)
	    \s 空格(\u0008)
	    \r 换行(\u000d)*/
        Matcher m = p.matcher(sourceBoundary);
        sourceBoundary = m.replaceAll("");
		//坐标转换
		govArea.setTargetLnglatSystem(AreaConstant.GCJ02);
		if(AreaConstant.GCJ02.equals(sourceLnglatSystem)) {
			
			govArea.setTargetBoundary(sourceBoundary);
		}else {
			govArea.setTargetBoundary(transformLnglat(sourceBoundary,sourceLnglatSystem));
		}
		govArea.setTargetBoundaryFomat(fomatLnglat(govArea.getTargetBoundary()));
		//保存
		this.saveOrUpdate(govArea);
		Integer parentId = govArea.getParentId();
		//生成code
		if(parentId != null) {
			GovArea parentArea = this.getById(parentId);
			govArea.setLevel(parentArea.getLevel()+1);
			govArea.setCode(parentArea.getCode() + "-" + govArea.getId());
		}else {
			govArea.setCode(String.valueOf(govArea.getId()));
			govArea.setLevel(1);
		}
		//更新code
		this.saveOrUpdate(govArea);
	}
	
	@Override
	public List<Map> getListByParent(Map<String, Object> param) {
		
		return govAreaMapper.getListByParent(param);
	}
	/**
	 * 坐标转换
	 * @param lnglats
	 * @param lnglatSystem
	 * @return
	 */
	private String transformLnglat(String lnglats, String lnglatSystem) {
		String[] rings = lnglats.split("#");
		String resultRings = "";
		for(String ring : rings) {
			String[] points = ring.split(",");
			String resultRing = "";
			for(int i=0; i<points.length; i+=2) {
				double[] transPoint;
				if(AreaConstant.WGS84.equals(lnglatSystem)) {
					transPoint = GPSUtil.gps84_To_Gcj02(Double.parseDouble(points[i+1]), Double.parseDouble(points[i]));
				}else if(AreaConstant.BD09.equals(lnglatSystem)) {
					transPoint = GPSUtil.bd09_To_Gcj02(Double.parseDouble(points[i+1]), Double.parseDouble(points[i]));
				}else {
					return null;//源坐标系异常
				}
				resultRing += transPoint[1] + "," + transPoint[0] + ",";
			}
			resultRing = resultRing.substring(0, resultRing.length()-1);
			resultRings += resultRing + "#";
		}
		resultRings = resultRings.substring(0, resultRings.length()-1);
		return resultRings;
	}
	/**
	 * 格式化坐标
	 * @param lnglats
	 * @return
	 */
	private String fomatLnglat(String lnglats) {
		String[] rings = lnglats.split("#");
		String resultRings = "";
		for(String ring : rings) {
			String[] points = ring.split(",");
			String resultRing = "";
			for(int i=0; i<points.length; i+=2) {
				resultRing += points[i] + "," + points[i+1] + ";";
			}
			resultRing = resultRing.substring(0, resultRing.length()-1);
			resultRings += resultRing + "#";
		}
		resultRings = resultRings.substring(0, resultRings.length()-1);
		return resultRings;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void del(GovArea govArea) {
		govArea = this.getById(govArea.getId());
		govArea.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		this.saveOrUpdate(govArea);//本身置为无效
		UpdateWrapper<GovArea> wrapper = new UpdateWrapper<GovArea>();
		wrapper.likeRight("code", govArea.getCode()+"-");
		GovArea entity = new GovArea();
		entity.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		this.update(entity , wrapper);//子节点置为无效
	}

}
