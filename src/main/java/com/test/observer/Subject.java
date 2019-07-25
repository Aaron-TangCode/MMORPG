package com.test.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Subject
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/25 10:13
 * @Version 1.0
 */
public class Subject {
    private List<Observer> observerList = new ArrayList<>();
    private int state;

    public void setState(int state) {
        this.state = state;
        notifyA();
    }

    public void register(Observer observer){
        observerList.add(observer);
    }

    public void notifyA(){
        for (int i = 0; i < observerList.size(); i++) {
            observerList.get(i).update();
        }
    }
}
