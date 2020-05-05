package com.chinobot.drools;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.drools.executor.RuleExecutor;

@RestController
@RequestMapping("/api/droolsTest")
public class TestController {

	@GetMapping("/excuteTest")
	public Result excuteTest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("version", "V0.1");
		map.put("label_y_n", 1249);
		map.put("droolsFilter", false);
		//存量数据集一
		//RuleExecutor.execute(map, "78d94915785f86efc21aa9eafc1bbd20"+"#"+"123123123");
		//楼顶数据集一
		RuleExecutor.execute(map, "f72da4ba9436f4e6a5fc8c8f1f460f55"+"#"+"sfsnb7823df");
		return ResultFactory.success(map);
	}
}
