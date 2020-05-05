package com.chinobot.common.utils.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

	public static Logger logger(LogTypeName typeName) {
        return LoggerFactory.getLogger(typeName.getLogTypeName());
    }
}
