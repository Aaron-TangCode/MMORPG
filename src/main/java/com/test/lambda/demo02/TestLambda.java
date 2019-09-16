package com.test.lambda.demo02;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Aaron
 * @date 2019-09-12 10:57
 * @function
 */
public class TestLambda {

    List<Employee> list = Arrays.asList(
            new Employee(102, "张三", 18, 5555.55),
            new Employee(102, "李四", 22, 6666.44),
            new Employee(102, "王五", 19, 1234.12),
            new Employee(102, "赵六", 20, 5555.55),
            new Employee(102, "孙七", 21, 9999.99)

    );

    @Test
    public void test01() {
        Collections.sort(list, (e1, e2) -> {
            if(e1.getAge()==e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else{
                return -Integer.compare(e1.getAge(),e2.getAge());
            }
        });

        for (Employee employee : list) {
            System.out.println(employee);
        }
    }
}