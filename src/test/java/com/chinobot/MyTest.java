package com.chinobot;

import org.junit.Test;

import com.chinobot.common.utils.CommonUtils;

public class MyTest {

    @Test
    public void test1(){
    	System.out.println(CommonUtils.doLongString("123456789", 9));
    }
}
