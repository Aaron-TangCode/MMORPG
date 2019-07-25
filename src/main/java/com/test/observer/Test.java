package com.test.observer;

/**
 * @ClassName Test
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/25 10:28
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Subject subject = new Subject();

        FirstObserver firstObserver = new FirstObserver(subject);
        SecondObserver secondObserver = new SecondObserver(subject);
        ThirdObserver thirdObserver = new ThirdObserver(subject);

        firstObserver.subject.setState(1);
    }
}
