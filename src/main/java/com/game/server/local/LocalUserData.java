package com.game.server.local;

/**
 * @ClassName LocalUserData
 * @Description TODO
 * @Author DELL
 * @Date 2019/8/8 16:05
 * @Version 1.0
 */
public class LocalUserData {
    public static long USERID = 0L;


    public static void setUserId(long userId){
        USERID = userId;
    }

    public static long getUserId(){
        return USERID;
    }
}
