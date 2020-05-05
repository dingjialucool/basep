package com.chinobot.plep.home.dataset.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;

import com.chinobot.plep.home.dataset.entity.vo.MetadataAndResultVo;
import com.chinobot.plep.home.dataset.entity.vo.MetadataVo;
import com.chinobot.plep.home.dataset.service.IJdbcUtilService;

@Service
public class JdbcUtilServiceImpl implements IJdbcUtilService{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public Object test() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("testV", "%深圳%");
		return getMetadataAndResult("SELECT * from p_setting where val like :testV;", param);
	}
	@Override
	public List<String> getSqlParam(String sql){
		String[] splits = sql.split(":");
		List<String> list = new ArrayList<String>();
		if(splits.length>1) {
			for(int i=1; i<splits.length; i++) {
				String s = "\\d+.\\d+|\\w+";
		        Pattern  pattern=Pattern.compile(s);  
		        Matcher  ma=pattern.matcher(splits[i]);  
		        while(ma.find()){  
		        	String group = ma.group();
		        	if(!list.contains(group)) {
		        		list.add(group);
		        	}
		            break;
		        }
			}
		}
		return list;
	}
	
	@Override
	public MetadataAndResultVo getMetadataAndResult(String sql) {
		if(sql.contains(";")) {
			sql = sql.replace(";", "");
		}
		SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql + " LIMIT 0,10");
		SqlRowSetMetaData metaData = rowSet.getMetaData();
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
		MetadataAndResultVo vo = new MetadataAndResultVo();
		vo.setMetadatas(list);
		vo.setResult(listMap);
		return vo;
	}
	
	@Override
	public MetadataAndResultVo getMetadataAndResult(String sql, Map<String, Object> param) {
		if(sql.contains(";")) {
			sql = sql.replace(";", "");
		}
		SqlRowSet rowSet = namedParameterJdbcTemplate.queryForRowSet(sql + " LIMIT 0,10", param);
		SqlRowSetMetaData metaData = rowSet.getMetaData();
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
		MetadataAndResultVo vo = new MetadataAndResultVo();
		vo.setMetadatas(list);
		vo.setResult(listMap);
		return vo;
	}
	@Override
	public List<Map<String, Object>> getResult(String sql, Map<String, Object> param) {
		if(sql.contains(";")) {
			sql = sql.replace(";", "");
		}
		List<Map<String, Object>> listMap = namedParameterJdbcTemplate.queryForList(sql + " LIMIT 0,10", param);
		return listMap;
	}
}
