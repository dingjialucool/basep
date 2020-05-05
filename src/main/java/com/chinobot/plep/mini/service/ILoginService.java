package com.chinobot.plep.mini.service;

import com.chinobot.common.domain.Result;

public interface ILoginService {
	
	Result<String> genSessionCode(String code, String userId);
}
