package com.chinobot.plep.home.dataset.entity;


import java.util.List;

import lombok.Data;

@Data
public class DataDto {

	private DataSet dataSet;
	
	private List<Metadata> metadatas;
}
