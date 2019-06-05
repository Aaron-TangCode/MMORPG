package com.game.dispatcher;

import java.util.concurrent.Callable;

/**
 * @ClassName HandleThread
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/310:22
 * @Version 1.0
 */
public class HandleThread implements Callable {
    private String content;
    public HandleThread(String content){
        this.content = content;
    }
    @Override
    public Object call() throws Exception {
        String result = MyAnnotationUtil.requestService(content);
        return result;
    }
}
