package com.hailintang.gameserver2.Main;

import com.hailintang.gameserver2.State.AliveState;
import com.hailintang.gameserver2.State.DeadState;
import com.hailintang.gameserver2.State.ForbidState;
import com.hailintang.gameserver2.State.State;
import com.hailintang.gameserver2.map.Map;
import com.hailintang.gameserver2.map.MapInfo;
import com.hailintang.gameserver2.map.Origin;
import com.hailintang.gameserver2.map.Village;
import com.hailintang.gameserver2.role.Role_01;
import com.hailintang.gameserver2.role.Role_02;

/**
 * @ClassName Main
 * @Description TODO
 * @Author DELL
 * @Date 2019/5/2411:40
 * @Version 1.0
 */
public class Main {
    public static void main(String[] args) {
        Map origin = new Origin(1,"起始之地");
        Map village = new Village(2,"村子");
        Map forest = new Village(4,"森林");

        Role_01 role_01 = new Role_01(origin,"玩家01");
        AliveState aliveState = new AliveState("生存");
        aliveState.doAction(role_01);
        MapInfo.printCurMapAllOfRoleInfo(origin);
        MapInfo.printCurMapAllOfRoleInfo(village);

        System.out.println("=========================");

        role_01.moveTo(origin,village);
        MapInfo.printCurMapAllOfRoleInfo(origin);
        MapInfo.printCurMapAllOfRoleInfo(village);
        System.out.println("=========================");


        Role_02 role_02 = new Role_02(origin,"玩家02");

        aliveState.doAction(role_02);
        MapInfo.printCurMapAllOfRoleInfo(origin);
        MapInfo.printCurMapAllOfRoleInfo(village);

        System.out.println("=========================");

        role_02.moveTo(origin,village);
        MapInfo.printCurMapAllOfRoleInfo(origin);
        MapInfo.printCurMapAllOfRoleInfo(village);
        System.out.println("=========================");

    }
}
