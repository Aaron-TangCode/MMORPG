package com.game.gang.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.gang.bean.GangMemberEntity;
import com.game.gang.service.GangService;
import com.game.role.bean.ConcreteRole;
import com.game.utils.MapUtils;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @ClassName GangController
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/16 16:21
 * @Version 1.0
 */
@Controller
@RequestAnnotation("/gang")
public class GangController {

    @Autowired
    private GangService gangService;

    /**
     * 创建工会
     * @param roleName
     * @param gangName
     * todo
     */
    @RequestAnnotation("/create")
    public void createGang(String roleName,String gangName){
        //获取角色
        ConcreteRole role = getRole(roleName);
        //判断该角色是否已经有工会
        GangMemberEntity flag = belongGang(roleName);
        ChannelHandlerContext ctx = role.getCtx();
        //没工会可创建工会
        if(flag!=null){
            //插入工会
//            gangService.insertGang(gangName);
            //插入工会会长
            GangMemberEntity entity = new GangMemberEntity();
//            entity.setGang();
//            entity.setJob(Job.CHARIMEN);
//            gangService.insertGangMember()
        }else{//有工会就返回提示信息
            ctx.channel().writeAndFlush(roleName+"已经有工会了!\t工会："+flag.getGang().getName());
        }

    }

    /**
     * 加入工会
     * @param roleName
     * @param gangName
     */
    @RequestAnnotation("/join")
    public void joinGang(String roleName,String gangName){
        //判断是否已经有工会

        //没的话加入工会

        //有就返回提示信息
    }

    /**
     * 解散工会
     */
    @RequestAnnotation("/dismiss")
    public void dismiss(String roleName,String gangName){
        //判断角色是否有工会

        //判断角色是否
    }


    @RequestAnnotation("/donateMoney")
    public void donate(String roleName,String number){

    }

    /**
     * 判断角色是否有工会
     * @param roleName
     * @return
     */
    public GangMemberEntity belongGang(String roleName){
        ConcreteRole role = getRole(roleName);
        GangMemberEntity entity = gangService.findGangMember(role.getId());
        return entity;
    }

    /**
     * 获取角色
     * @return
     */
    public ConcreteRole getRole(String roleName){
        return MapUtils.getMapRolename_Role().get(roleName);
    }
}
