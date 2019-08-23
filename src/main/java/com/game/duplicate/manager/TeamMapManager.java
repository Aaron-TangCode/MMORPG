package com.game.duplicate.manager;

import com.game.role.bean.ConcreteRole;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName TeamMapManager
 * @Description 队伍管理器
 * @Author DELL
 * @Date 2019/8/23 14:50
 * @Version 1.0
 */
public class TeamMapManager {
    private TeamMapManager(){}

    private static volatile Map<String, List<ConcreteRole>> teamMap =  new HashMap<>();
    /**
     * key:roleName
     * value:teamName
     */
    private static volatile Map<String, String> roleTeamMap =  new HashMap<>();

    public static Map<String, List<ConcreteRole>> getTeamMap(){
        return teamMap;
    }

    public static Map<String, String> getRoleTeamMap(){
        return roleTeamMap;
    }
}
