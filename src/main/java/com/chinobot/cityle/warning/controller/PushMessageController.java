//package com.chinobot.cityle.warning.controller;
//
//
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.warning.Constant.GlobalConstant;
//import com.chinobot.cityle.warning.entity.PushMessage;
//import com.chinobot.cityle.warning.service.IPushMessageService;
//import com.chinobot.common.domain.Result;
//import com.chinobot.common.utils.ResultFactory;
//import com.chinobot.framework.web.controller.BaseController;
//
///**
// * <p>
// * 推送信息表 前端控制器
// * </p>
// *
// * @author dingjl
// * @since 2019-03-16
// */
//@RestController
//@RequestMapping("/api/warning")
//public class PushMessageController extends BaseController {
//
//	@Autowired
//	private IPushMessageService iPushMessageService;
//	
//	/**
//	 * 预警信息推送失败
//	 * @param page
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping("/no/push")
//    public Result getPushMessage(Page page ,@RequestParam Map<String, Object> param){
//		System.out.println(param);		
//		param.put("pushStatus", GlobalConstant.No_Push_Status);
//		iPushMessageService.getNoPushMessage(page,param);			
//		return ResultFactory.success(page);
//    }
//	
//	/**
//	 *  再次推送信息
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping("/no/push/again")
//    public Result pushMessageAgain(@RequestBody Map<String, Object> param){
//		System.out.println(param);		
//		boolean bo = iPushMessageService.pushMessageAgain(param);
//		System.out.println(bo);
//		if(!bo) {
//			return ResultFactory.fail(null);
//		}
//		PushMessage pushMessage = new PushMessage();
//		pushMessage.setPushStatus("1");
//		pushMessage.setConfireStatus("1");
//		String uuid = (String) param.get("push_message_id");
//		pushMessage.setUuid(uuid);
//		iPushMessageService.updateById(pushMessage);
//		return ResultFactory.success();
//    }
//	
//	
//	/**
//	 * 预警信息已被推送,且接收人已确认
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping("/yes/push")
//    public Result getYesPushMessage( Page page ,@RequestParam Map<String, Object> param){
//		
//		param.put("pushStatus", GlobalConstant.Yes_Push_Status);
//		param.put("confirmStatus", GlobalConstant.Confirm_Status);
//		iPushMessageService.getYesPushMessage(page,param);			
//		return ResultFactory.success(page);
//    }
//	
//	/**
//	 * 预警信息已被推送,但接收人未确认
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping("/yes/push/noconfirm")
//    public Result getYesMessageByTimeAndPoint(Page page ,@RequestParam Map<String, Object> param){
//		
//		param.put("pushStatus", GlobalConstant.Yes_Push_Status);
//		param.put("confirmStatus", GlobalConstant.NO_Confirm_Status);
//		 iPushMessageService.getYesPushMessage(page,param);			
//		return ResultFactory.success(page);
//		
//    }
//	
//	/**
//	 *      获取实时预警信息
//	 * @param page
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping("/real/time")
//    public Result getRealTimeMessage(Page page ,@RequestParam Map<String, Object> param){				
//		iPushMessageService.getRealTimeMessage(page,param);			
//		return ResultFactory.success(page);		
//    }
//	
//	/**
//	 *      获取历史预警信息
//	 * @param page
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping("/history/time")
//    public Result getHistoryTimeMessage(Page page ,@RequestParam Map<String, Object> param){				
//		iPushMessageService.getHistoryTimeMessage(page,param);	
//		return ResultFactory.success(page);
//		
//    }
//	
//	/**
//	 *   获取文件ID与类型
//	 * @param param
//	 * @return
//	 */
//	@RequestMapping("/get/fileId")
//    public Result getFileId(@RequestBody Map<String, Object> param){
//		System.out.println(param);
//		List<LinkedHashMap<String, String>> list = iPushMessageService.getFileId(param);			
//		return ResultFactory.success(list);		
//    }
//}
