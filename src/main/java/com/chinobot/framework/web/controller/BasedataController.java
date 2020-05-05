package com.chinobot.framework.web.controller;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.entity.Basedata;
import com.chinobot.framework.web.entity.Regions;
import com.chinobot.framework.web.service.IBasedataService;
import com.chinobot.framework.web.service.IRegionsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * <p>
 * 字典 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2019-05-17
 */
@Api(tags= {"字典表接口"})
@RestController
@RequestMapping("/api/basedata")
public class BasedataController extends BaseController {

	@Autowired
	private IBasedataService basedataService;
	@Autowired
	private IRegionsService regionsService;
	
	/**
	 * 获取码值
	 * @return
	 * @author shizt  
	 * @date 2019年5月30日
	 * @company chinobot
	 */
//	@RequestMapping("")
	@ApiOperation(value = "获取码值", notes = "无参数")
	@GetMapping("")
	public Result getBasedata() {
		QueryWrapper<Basedata> wrapper = new QueryWrapper<Basedata>();
		wrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID)
			   .orderByAsc("typecode","sort");
		List<Basedata> list = basedataService.list(wrapper);
		Map result = new HashMap();
		Map type = new LinkedHashMap();
		String typecode = "";
		for (Basedata b : list) {
			if(CommonUtils.isEmpty(typecode)) {
				typecode = b.getTypecode();
			}
			if(!typecode.equals(b.getTypecode())) {
				result.put(typecode, type);
				typecode = b.getTypecode();
				type = new LinkedHashMap();
			}
			type.put(b.getFieldcode(), b.getFieldname());
		}
		result.put(typecode, type);
		System.err.println("result: " + JSON.toJSONString(result));
		
		return ResultFactory.success(result);
	}
	
	
	@ApiOperation(value = "下级区域", notes = "参数 - Regions表中的parent")
	@GetMapping("/lowerRegions")
//	@RequestMapping("/lowerRegions")
	public Result getLowerRegions(String parent) {
		QueryWrapper<Regions> queryWrapper = new QueryWrapper<Regions>();
		queryWrapper.eq("parent", parent);
		
		return ResultFactory.success(regionsService.list(queryWrapper));
	}
	
//	@RequestMapping("")
//	public void test() throws IOException, Exception {
//		BufferedReader br = new BufferedReader(new FileReader("d://dict.json"));// 读取原始json文件  
//		String s = null;
//		StringBuilder builder = new StringBuilder();
//		while ((s = br.readLine()) != null) {
//			builder.append(s);
//		}
////		JSONObject jsonObject = JSON.parseObject(builder.toString());
//
//		LinkedHashMap<String, String> jsonMap = JSON.parseObject(builder.toString(), new TypeReference<LinkedHashMap<String, String>>() {
//        });
//        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
//        	Basedata basedata = new Basedata();
//        	basedata.setTypecode(entry.getKey());
//        	LinkedHashMap<Object, String> j = JSON.parseObject(entry.getValue(), new TypeReference<LinkedHashMap<Object, String>>() {
//            });
//        	for (Map.Entry<Object, String> e : j.entrySet()) {
//        		basedata.setFieldcode(e.getKey() + "");
//        		basedata.setFieldname(e.getValue());
//        		basedataService.save(basedata);
//        		basedata.setUuid(null);
////            System.out.println(JSON.toJSONString(basedata));
//			}
//        }
//	}
	
}
