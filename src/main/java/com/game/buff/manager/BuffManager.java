package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.buff.handler.BuffType;
import com.game.role.bean.ConcreteRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BuffManager
 * @Description buff管理器
 * @Author DELL
 * @Date 2019/6/20 17:42
 * @Version 1.0
 */
@Component
public class BuffManager {
    @Autowired
    private RedBuff redBuff;
    @Autowired
    private BlueBuff blueBuff;

    @Autowired
    private DefendBuff defendBuff;

    @Autowired
    private AttackBuff attackBuff;
    private IBuff iBuff;

    public BuffManager(IBuff iBuff) {
        this.iBuff = iBuff;
    }

    public void execBuff(ConcreteBuff buff, ConcreteRole role){
        iBuff.executeBuff(buff,role);
    }

    public static Map<BuffType,IBuff> iBuffMap = new HashMap<>();

    public static Map<BuffType,IBuff> getiBuffMap(){
        return iBuffMap;
    }
    @PostConstruct
    public void loadBuffData(){
        iBuffMap.put(BuffType.RED,redBuff);
        iBuffMap.put(BuffType.BLUE,blueBuff);
        iBuffMap.put(BuffType.ATTACK,attackBuff);
        iBuffMap.put(BuffType.DEFEND,defendBuff);
    }
}
