package com.chinobot.plep.mini.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.chinobot.plep.mini.entity.WxUser;


public class MemoryStore {
	/** 初始时间（分） */
	private static final int TIMER_INTERVAL = 0;
	/** 清理的时间间隔（分）*/
	private static final int REMOVE_EXPIRE_TIME = 1;

	/** 存储Map，group -> (key -> code) */
	private static final Map<String, ValidateCode> storeMap =
		new ConcurrentHashMap<String, ValidateCode>();
	
	private static boolean IS_FIRST =true;

	public static ValidateCode getValidateCode(String key){
		return storeMap.get(key);
	}
	
	public static void setStore(String key,ValidateCode dCode){
		storeMap.put(key, dCode);
		if (IS_FIRST) {
			startTimerClearService();
			IS_FIRST = false;
		}
	}


    /**
     * 方法描述: 开启定时清理无效验证用户的服务
     * @author wenls
     */
    private static void startTimerClearService() {
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Calendar now = Calendar.getInstance();
                for (String key:storeMap.keySet()) {
                    // 移除无效的验证
                    if (isInvalid(now,storeMap.get(key))){
                        System.out.print("移除key："+key);
                        storeMap.remove(key);
                    }
                }
            }
        }, TIMER_INTERVAL, REMOVE_EXPIRE_TIME, TimeUnit.MINUTES);
    }
	
	/**
	 * 方法描述: 判断是否无效
	 * @return 是返回true，否则返回false
	 * @author wenls
	 */
	private static boolean isInvalid(Calendar now, ValidateCode code) {
		Date expireTime = code.getExpireTime();
		if (expireTime == null) return false;
		return expireTime.before(now.getTime());
	}
	
//	public static void startTimerClear() {
//		startTimerClearService();
//	}

	private MemoryStore() {
		// 定时删除无效验证用户
		startTimerClearService();
	}
	
	public static void clearCaptcha(String key){
		storeMap.remove(key);
	}

	public static boolean isContain(WxUser user){
       return storeMap.containsValue(user);
    }


}
