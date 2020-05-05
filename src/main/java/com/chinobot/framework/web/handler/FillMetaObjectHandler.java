package com.chinobot.framework.web.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ThreadLocalUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FillMetaObjectHandler implements MetaObjectHandler {
	
	
    @Override
    public void insertFill(MetaObject metaObject) {
    	Person personInfo =  ThreadLocalUtil.getResources();
    	if(CommonUtils.isObjEmpty(personInfo)) {
    		personInfo = ThreadLocalUtil.getDefaultResources();
    	}
    	String personUuid = personInfo.getUuid();
        log.info("start insert fill ....");
        isFillByVal("createBy",personUuid, metaObject, FieldFill.INSERT_UPDATE);
        isFillByVal("createTime", LocalDateTime.now(), metaObject, FieldFill.INSERT_UPDATE);
        isFillByVal("operateBy",personUuid, metaObject, FieldFill.INSERT_UPDATE);
        isFillByVal("operateTime",LocalDateTime.now(), metaObject, FieldFill.INSERT_UPDATE);
        isFillByVal("areaCascadeCode",personInfo.getDept().getAreaCode(), metaObject, FieldFill.INSERT_UPDATE);
        isFillByVal("deptCascadeCode",personInfo.getDept().getDeptCode(), metaObject, FieldFill.INSERT_UPDATE);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    	Person personInfo =  ThreadLocalUtil.getResources();
    	if(CommonUtils.isObjEmpty(personInfo)) {
    		personInfo = ThreadLocalUtil.getDefaultResources();
    	}
    	String personUuid = personInfo.getUuid();
        log.info("start update fill ....");
        this.setUpdateFieldValByName("operateBy", personUuid, metaObject);
        this.setUpdateFieldValByName("operateTime", LocalDateTime.now(), metaObject);
    
//        this.setUpdateFieldValByName("et.operateBy",personUuid, metaObject, FieldFill.UPDATE);
//        this.setUpdateFieldValByName("et.operateTime",LocalDateTime.now(), metaObject, FieldFill.UPDATE);
    }

    /**
     * 判断是否有值，有值则不填充
     * @param fieldName
     * @param fieldVal
     * @param metaObject
     */
    private boolean isFillByVal(String fieldName, Object fieldVal, MetaObject metaObject, FieldFill fieldFill){
        if(metaObject.hasGetter (fieldName)){
            Object obj = metaObject.getValue(fieldName);
            if(obj==null){
                if(FieldFill.INSERT_UPDATE.equals (fieldFill)){
                    this.setInsertFieldValByName(fieldName,fieldVal, metaObject);
                }else if (FieldFill.UPDATE.equals (fieldFill)){
                    this.setUpdateFieldValByName(fieldName.substring(3),fieldVal, metaObject);
                }
                return true;
            }
        }
        return false;
    }
}
