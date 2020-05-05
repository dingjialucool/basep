package com.chinobot.framework.web.exception;

import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.WxBotUtils;
import com.xxl.rpc.util.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author laihb
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @Value("${spring.profiles.active}")
    String active;

    /**
     * 所有异常报错
     *
     * @param request
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    public Result allExceptionHandler(HttpServletRequest request, Exception e) {
    	log.error("", e);

        // 把错误信息推送到企业微信开发组群
        try {
            // 判断是否云环境才推送错误消息
            if("hwyun".equals(active)){
                WxBotUtils.pushTextMsg("无人机监管平台报错了，服务器IP：" + IpUtil.getIp() + ":\n" + ExceptionUtils.getStackTrace(e));
            }
        } catch (IOException ex) {
            // ex.printStackTrace();
            log.error("推送到企业微信开发组群出错", ex);
        }

        return ResultFactory.error(ExceptionUtils.getStackTrace(e));
    }
}
