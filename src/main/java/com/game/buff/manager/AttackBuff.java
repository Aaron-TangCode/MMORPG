package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.role.bean.ConcreteRole;
import org.springframework.stereotype.Component;

/**
 * @ClassName AttackBuff
 * @Description 攻击buff
 * @Author DELL
 * @Date 2019/9/5 18:06
 * @Version 1.0
 */
@Component
public class AttackBuff implements IBuff {
    @Override
    public void executeBuff(ConcreteBuff buff, ConcreteRole role) {

    }
}
