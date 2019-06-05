package com.test.load.demo01;

/**
 * @ClassName BYDCar
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/415:36
 * @Version 1.0
 */
public class BYDCar implements ICarFactory {
    @Override
    public void makeCar() {
        System.out.println("i am a BYD");
    }
}
