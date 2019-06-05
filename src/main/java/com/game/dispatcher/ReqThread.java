package com.game.dispatcher;

import java.util.concurrent.Callable;

/**
 * @ClassName ReqThread
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/39:59
 * @Version 1.0
 */
public class ReqThread implements Callable {
    private String msg;
    public ReqThread(Object msg){
        this.msg = (String) msg;
    }
    @Override
    public String call() throws Exception {
        //接收请求，处理请求
        String content = (String)msg;
        //解密
        content = DataCodeor.deCodeor(content);
        return content;
    }
}
