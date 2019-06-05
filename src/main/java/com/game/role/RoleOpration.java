package com.game.role;

/**
 * @ClassName RoleOpration
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/3117:53
 * @Version 1.0
 */
public class RoleOpration {
    public static String getRoleState(ConcreteRole concreteRole){
        int hp = concreteRole.getHp();
        return hp>0? RoleState.ALIVE_STATE:RoleState.DEAD_STATE;
    }
}
