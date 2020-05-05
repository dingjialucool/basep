package com.chinobot.common.utils.log;

public enum LogTypeName {

	KAFKA_SEND("kafkaSendLog"),
	KAFKA_RECEIVE("kafkaReceiveLog"),
	SHELL("shellLog")
	;
	
	private String logTypeName;
	
	LogTypeName(String logTypeName) {
        this.logTypeName = logTypeName;
    }

	public String getLogTypeName() {
		return logTypeName;
	}

	public void setLogTypeName(String logTypeName) {
		this.logTypeName = logTypeName;
	}
	
	
}
