package com.test.stackoverflow;

/**
 * @ClassName Question02
 * @Description java是值传递还是引用传递？答案是：值传递
 * @Author DELL
 * @Date 2019/6/10 12:35
 * @Version 1.0
 */
public class Question02 {
    public static void main(String[] args) {
        Dog aDog = new Dog("Max");
        Dog oldDog = aDog;

        // we pass the object to foo
        foo(aDog);
        // aDog variable is still pointing to the "Max" dog when foo(...) returns
        System.out.println("1:"+aDog.getName().equals("Max"));//true
        System.out.println("2:"+aDog.getName().equals("Fifi"));// false
        System.out.println(aDog == oldDog);//true
    }

    public static void foo(Dog d) {
        System.out.println(d.getName().equals("Max"));//true
        // change d inside of foo() to point to a new Dog instance "Fifi"
        d = new Dog("Fifi");
        System.out.println(d.getName().equals("Fifi"));//true
    }
    public static class Dog{
        String name;

        public Dog(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
