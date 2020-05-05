package com.chinobot.aiuas.bot_collect.info.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.chinobot.aiuas.bot_collect.info.entity.Info;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectAndGeoDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectDTO;
import com.chinobot.aiuas.bot_collect.info.entity.vo.CollectPolygon;
import com.chinobot.aiuas.bot_collect.info.entity.vo.GovAreaVo;
import com.chinobot.aiuas.bot_collect.info.util.Tree;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 采查对象 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface IInfoService extends IBaseService<Info> {

	/**
	 * 采查对象树形结构
	 * @return
	 */
	Tree<CollectObjectDTO> getTreeObject();

	/**
	 * 新增采查对象
	 * @param dto
	 */
	void addCollectObject(CollectObjectAndGeoDTO dto);

	/**
	 * 删除采查对象
	 * @param infoId
	 */
	void removeCollectObject(String infoId);

	/**
	 * 导入并解析kml文件
	 * @param file
	 */
	List<CollectObjectAndGeoDTO> kmlUpload(MultipartFile file);

	/**
	 * 采查对象经纬度集合(多边形)
	 * @param id
	 * @return
	 */
	List<CollectPolygon> checkedCollectObject(String id);
	
	/**
	 * 回显采查对象
	 * @param infoId
	 */
	CollectObjectAndGeoDTO getCollectObject(String infoId);

	/**
	 * 导出kml文件
	 * @param id
	 */
	String exportKml(String id,HttpServletResponse resp);

	/**
	 * 获取行政code
	 * @return
	 */
	List<GovAreaVo> getCode();

	/**
	 * 判断是否可以删除（与策略木有关联或不在策略有效期内）
	 * @param infoId
	 */
	List<Info> getCollect(String infoId);
	
	/**
	 * 采查对象搜索
	 * @param infoName
	 * @return
	 */
	List<CollectObjectDTO> searchCollect(String infoName);

	/**
	 * 获取标签
	 * @return
	 */
	List<Map<String, String>> getTags();

	/**
	 * 根据采查对象，获取采查图片以及采查视频
	 * @param infoId
	 * @return
	 */
	Map<String,Object> searchInfoCheckData(String infoId);

}
