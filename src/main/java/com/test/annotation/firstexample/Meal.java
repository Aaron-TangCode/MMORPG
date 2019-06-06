package com.test.annotation.firstexample;

import java.lang.annotation.Repeatable;

/**
 * @ClassName Meal
 * @Description TODO
 * @Author DELL
 * @Date 2019/6/6 14:21
 * @Version 1.0
 */
@Repeatable(MealContainer.class)
public @interface Meal {
    String value();

    String mainDish();
}
class Test{
    @Meal(value = "breakfast",mainDish="cerral")
    @Meal(value = "lunch",mainDish="ok")
    @Meal(value = "dinner",mainDish="salad")
    public void evaluateDiet(){}
}
