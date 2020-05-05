//package com.chinobot.cityle.ddjk.controller;
//
//import java.io.IOException;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.chinobot.cityle.ddjk.service.IDemoDataService;
//import com.chinobot.common.domain.Result;
//import com.chinobot.common.utils.CommonUtils;
//import com.chinobot.common.utils.ResultFactory;
//import com.chinobot.framework.web.controller.BaseController;
//
//@RestController
//@RequestMapping("/api/demo")
//@SuppressWarnings("rawtypes")
//public class DemoController extends BaseController {
//
//    @Autowired
//    private IDemoDataService demoDataService;
//    
//	@RequestMapping(value = "/createver", method = RequestMethod.GET)
//	public Result createFileVer(String fileId) throws IOException {
//		if (CommonUtils.isEmpty(fileId)) {
//			return ResultFactory.error ();
//		}
//		demoDataService.createFileVer(fileId);
//		return ResultFactory.success ();
//	}
//
//	@RequestMapping(value = "/getfilever", method = RequestMethod.GET)
//	public Result getFileVer() throws IOException {
//		return ResultFactory.success (demoDataService.getFileVer(null));
//	}
//	
//	@RequestMapping(value = "/start", method = RequestMethod.GET)
//	public void startDemo(@RequestParam Map<String, Object> param) throws IOException {
//		demoDataService.startDemo(param);
//	}
//	
//	@RequestMapping(value = "/pause", method = RequestMethod.GET)
//	public void pause(@RequestParam Map<String, Object> param) throws IOException {
//		demoDataService.pause(param);
//	}
//
//	@RequestMapping(value = "/reset", method = RequestMethod.GET)
//	public void reset(@RequestParam Map<String, Object> param) throws IOException {
//		demoDataService.reset(param);
//	}
//}
