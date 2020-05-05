package com.chinobot.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import net.sf.json.JSONObject;

@ApiModel(description = "接口返回包装")
@Data
public class Result<T> {
    // 是否成功
	@ApiModelProperty(value = "是否成功")
    private boolean isSuccess;
    // 返回码
	@ApiModelProperty(value = "返回码")
    private Integer code;
	@ApiModelProperty(value = "返回信息")
    // 返回信息
    private String msg;
	@ApiModelProperty(value = "返回数据")
    // 返回数据
    private T data;

    @Override
    public String toString() {
        return JSONObject.fromObject (this).toString ();
    }
}
