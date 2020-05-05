package com.chinobot.cityle.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.SceneTaskTemplet;
import com.chinobot.cityle.base.mapper.SceneTaskTempletMapper;
import com.chinobot.cityle.base.service.ISceneTaskTempletService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 巡查内容模板 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-04-09
 */
@Service
public class SceneTaskTempletServiceImpl extends BaseService<SceneTaskTempletMapper, SceneTaskTemplet> implements ISceneTaskTempletService {

	@Autowired
	private SceneTaskTempletMapper sceneTaskTempletMapper;
	
	@Override
	public IPage<Map> getTaskTempletPage(Page page, Map<String, Object> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		 if(param.get("sceneType")!=null && param.get("sceneType")!="" ) {
	        	String str = (String) param.get("sceneType");
	        	if(str.indexOf(',')>0) {
	        		String[] split = str.split(",");
	        		param.put("sceneType", split);
	        	}else {
					String[] split = new String[1];
					split[0] = str;
					param.put("sceneType", split);
				}
	        }
		 if(param.get("level")!=null && param.get("level")!="" ) {
	        	String str = (String) param.get("level");
	        	if(str.indexOf(',')>0) {
	        		String[] split = str.split(",");
	        		param.put("level", split);
	        	}else {
					String[] split = new String[1];
					split[0] = str;
					param.put("level", split);
				}
	        }
		return sceneTaskTempletMapper.getTaskTempletByPage(page, param);
	}

	@Override
	public Map getTaskTempletById(String uuid) {
		Map<String, String> param = new HashMap();
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("uuid", uuid);
		
		List<Map> taskTemplets = sceneTaskTempletMapper.getTaskTemplet(param);
		System.out.println(JSON.toJSONString("--"+sceneTaskTempletMapper.getTaskTemplet(param)));
		return (taskTemplets.size() > 0)? taskTemplets.get(0): null;
	}

	@Override
	public List<Map> getTaskTempletByParam(Map<String, String> param) {
		
		return sceneTaskTempletMapper.getTaskTemplet(param);
	}

}
