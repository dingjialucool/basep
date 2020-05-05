package com.chinobot.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @ClassName: TaskCode   
 * @Description: 生成巡查任务的编号
 * @author: dingjialu  
 * @date:2019年4月10日 上午11:24:12
 */
public class TaskCode {

	/**
	 *  任务编号
	 * @return
	 */
	public  static String getTaskCode() {
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String format = sFormat.format(new Date());
        String taskCode = "T"+format; 
		return taskCode;
	}
}
