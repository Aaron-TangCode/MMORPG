package com.test.annotation.firstexample;

/**
 * @ClassName House
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/6 14:17
 * @Version 1.0
 */
public interface House {
    @Deprecated
    void open();
    void openFrontDoor();
    void openBackDoor();
}
