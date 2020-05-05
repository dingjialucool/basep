package com.chinobot.plep.home.dataset.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.dataset.entity.DataDto;
import com.chinobot.plep.home.dataset.entity.DataSet;
import com.chinobot.plep.home.dataset.entity.Rule;
import com.chinobot.plep.home.dataset.entity.vo.MetadataAndResultAndTotalVo;
import com.chinobot.plep.home.dataset.entity.vo.MetadataVo;
import com.chinobot.plep.home.dataset.service.IDataSetService;
import com.chinobot.plep.home.dataset.service.IJdbcUtilService;
import com.chinobot.plep.home.dataset.service.IRuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 数据集 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
@Api(tags= {"数据集接口"})
@RestController
@RequestMapping("/api/dataset/data-set")
public class DataSetController extends BaseController {

	@Autowired
	private IDataSetService dataSetService;
	@Autowired
	private IJdbcUtilService jdbcUtilService;
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired
	private IRuleService ruleService;
	
	@ApiOperation(value = "获取数据集列表", notes = "参数 - 分页page,Map param(name,operateBy,start(开始时间),end(结束时间),sceneId(场景Id),domainId(领域Id))")
	@GetMapping("/getDataSetList")
	public Result getDataSetList(Page page,@RequestParam  Map<String, Object> param) {
		
		return ResultFactory.success(dataSetService.getDataSetList(page,param));
		
	}
	
	@ApiOperation(value = "删除数据集", notes = "参数 - 数据集主键uuid")
	@GetMapping("/deleteDataSet")
	public Result deleteDataSet(@ApiParam(name = "uuid", value = "数据集主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		//如果规则中有用到数据集，则不能删除
		QueryWrapper<Rule> queryWrapper = new QueryWrapper<Rule>();
		queryWrapper.eq("set_id", uuid).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<Rule> list = ruleService.list(queryWrapper);
		if(list.size()>0) {
			return ResultFactory.error(600, "该数据集正在使用中，不能删除", null);
		}
		//否则可以删除
		UpdateWrapper<DataSet> updateWrapper = new UpdateWrapper<DataSet>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		return ResultFactory.success(dataSetService.update(updateWrapper));
	}
	
	@ApiOperation(value = "解析sql", notes = "参数 - sql,")
	@GetMapping("/refresh")
	public Result refresh(@ApiParam(name = "sql", value = "sql内容", required = true) @RequestParam(value = "sql", required = true) String sql) {
		List<String> sqlParam = jdbcUtilService.getSqlParam(sql);
		//如果没有参数
		if(sqlParam.size()<1) {
			return ResultFactory.success(jdbcUtilService.getMetadataAndResult(sql));
		}else {//如果有参数
			return ResultFactory.success(sqlParam);
		}
		
	}
	
	@ApiOperation(value = "刷新元数据", notes = "参数 - sql,Map param(start,size,参数)")
	@GetMapping("/refreshData")
	public Result refreshData(@ApiParam(name = "sql", value = "sql内容", required = true) @RequestParam(value = "sql", required = true) String sql,@RequestParam Map<String, Object> param) {
		 String start = (String) param.get("start");
		 String size = (String) param.get("size");
		return ResultFactory.success(getResult(sql, param,start,size));
		
	}
	
	
	private MetadataAndResultAndTotalVo getResult(String sql, Map<String, Object> param,String start,String size) {
		if(sql.contains(";")) {
			sql = sql.replace(";", "");
		}
		String packSql = "select count(*) as totalNum from ("+sql+") s";
		SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql + " LIMIT " + start +"," + size , param);
		SqlRowSet rowSet2 = namedParameterJdbcTemplate.queryForRowSet(packSql , param);
		
		SqlRowSetMetaData metaData = rowSet.getMetaData();
		SqlRowSetMetaData metaDatas = rowSet2.getMetaData();
		int columnCount = metaData.getColumnCount();
		List<MetadataVo> list = new ArrayList<MetadataVo>();
		for(int i=1; i<=columnCount; i++) {
			MetadataVo vo = new MetadataVo();
			vo.setField(metaData.getColumnLabel(i));
			vo.setType(metaData.getColumnType(i));
			vo.setFieldType(metaData.getColumnTypeName(i));
			list.add(vo);
		}
		List<Map> listMap = new ArrayList<Map>();
		while(rowSet.next()) {
			Map map = new HashMap();
			for(MetadataVo vo : list) {
				map.put(vo.getField(), rowSet.getObject(vo.getField()));
			}
			listMap.add(map);
		}
		
		
		MetadataAndResultAndTotalVo vo = new MetadataAndResultAndTotalVo();
		vo.setMetadatas(list);
		vo.setResult(listMap);
		while(rowSet2.next()) {
			long count = rowSet2.getLong("totalNum");
			vo.setTotalNum(count);
		}
		return vo;
	}
	
	
	@ApiOperation(value = "保存元数据，数据集", notes = "参数 - 数据集和元数据的包装类dataDto")
	@PostMapping("/save")
	public Result saveData(@RequestBody DataDto dataDto) {
		
		return ResultFactory.success(dataSetService.saveData(dataDto));
		
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "回显元数据", notes = "参数 - 数据集主键uuid")
	@GetMapping("/backData")
	public Result backData(@ApiParam(name = "uuid", value = "数据集主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		
		List<Map> list = dataSetService.getMetadata(uuid);//元数据
		
		DataSet dataSet = dataSetService.getById(uuid);//数据集
		
//		System.out.println(JSON.parse(dataSet.getParam())+"--");
//		Map param = (Map) JSON.parse(dataSet.getParam());
//		MetadataAndResultVo metadataAndResult = jdbcUtilService.getMetadataAndResult(dataSet.getSqlContent(), param);
		
		Map map = new HashMap();
		map.put("dataSet", dataSet);
//		map.put("result", metadataAndResult.getResult());
		map.put("metadata", list);
		return ResultFactory.success(map);
		
	}
	
	@ApiOperation(value = "约定参数warningId的值", notes = "参数 - 无")
	@GetMapping("/getParamValue")
	public Result getParamValue() {
		
		List<String> paramValueList = dataSetService.getParamValue();
		List<Map> list = new ArrayList<Map>();
		Map map = new HashMap();
		map.put("key", "warning_id");
		map.put("List", paramValueList);
		list.add(map);
		return ResultFactory.success(list);
		
	}
	
	
	@ApiOperation(value = "获取领域、场景、任务的树形数据", notes = "参数 - 无")
	@GetMapping("/treeDomainSceneTask")
	public Result<List<DomainSceneVo>> treeDomainSceneTask() {
		
		return ResultFactory.success(dataSetService.treeDomainSceneTask());
	}
}
