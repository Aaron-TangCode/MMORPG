package com.game.buff.handler;

/**
 * @ClassName BuffType
 * @Description buff类型
 * @Author DELL
 * @Date 2019/8/14 18:12
 * @Version 1.0
 */
public enum  BuffType {
    /**
     *
     */
    RED,
    BLUE,
    ATTACK,
    DEFEND;


    public static BuffType getBuffType(String s) {
        for (BuffType e: BuffType.values() ) {
            if (e.toString().equals(s)) {
                return e;
            }
        }
        return RED;
   }
}
