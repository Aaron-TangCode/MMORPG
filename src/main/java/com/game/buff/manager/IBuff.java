package com.game.buff.manager;

import com.game.buff.bean.ConcreteBuff;
import com.game.role.bean.ConcreteRole;

public interface IBuff {
    public void executeBuff(ConcreteBuff buff, ConcreteRole role);
}
