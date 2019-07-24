package com.test.event;


        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Objects;

/**
 * @ClassName EventBus
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/23 21:37
 * @Version 1.0
 */

class Event {

}

class MonsterDeadEvent extends Event {
    // 凶手
    // 被害怪物

    /**  死亡时间 **/
    long deadTime;
}

interface Handler {

    void exce();

}

public class EventBus {

    private static EventBus self;

    private EventBus() {
        self = new EventBus();
    }


    public static EventBus getInstance() {
        return self;
    }

    private Map<Event, List<Handler>> eventListMap = new HashMap<>();


    /**
     *  发布事件
     * @param event
     */
    public void publish(Event event) {
        List<Handler> handlerList = eventListMap.get(event);
        if (Objects.nonNull(handlerList)) {
            handlerList.forEach(Handler::exce
            );
        }
    }


    public static void main(String[] args) {

        // 任务系统
        // 系统初始化时 加载枚举与Handler的关系

        // --------------- 触发
        // ....使用技能，怪物 ti死亡
        MonsterDeadEvent monsterDeadEvent = new MonsterDeadEvent();
        // 填充事件信息
        EventBus.self.publish(monsterDeadEvent);
    }
}


class  KillMonsterHandler implements Handler {
    @Override
    public void exce() {
        // 获取玩家的当前任务列表   getTaskList
        // 过滤出跟杀怪相关的任务
        // 检查任务的目标是否是当前的被害的怪物
        //if monsterType == now  count++
        // if count > targetCount 完成任务
    }
}



enum TaskType {


    // 杀指定怪物指定数量
    KILL_MONSTER(new KillMonsterHandler());


    // 升级到xx级


    //  拾取某物品


    TaskType(Handler handler) {
        this.handler = handler;
    }
    private Handler handler;
}