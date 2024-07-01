package com.stone.utils.thread;

import com.stone.model.user.pojos.ApUser;

public class AppThreadLocalUtil {
    private final static ThreadLocal<ApUser> APP_USER_THREAD_LOCAL = new ThreadLocal<>();

    // 存入线程
    public static void setUser(ApUser apUser) {
        APP_USER_THREAD_LOCAL.set(apUser);
    }

    // 从线程中获取
    public static ApUser getUser() {
        return APP_USER_THREAD_LOCAL.get();
    }

    // 清理
    public static void clear() {
        APP_USER_THREAD_LOCAL.remove();
    }

}
