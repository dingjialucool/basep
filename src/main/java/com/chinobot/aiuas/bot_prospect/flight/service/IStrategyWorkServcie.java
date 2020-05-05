package com.chinobot.aiuas.bot_prospect.flight.service;

import java.time.LocalDate;

public interface IStrategyWorkServcie {

	boolean generatorWork(String strategyId, LocalDate workDate, int count);

	void workSchedulingByStrategy();

	void queryWeather();

}
