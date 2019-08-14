package com.game.gang.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.gang.bean.GangEntity;
import com.game.gang.bean.GangMemberEntity;
import com.game.gang.bean.Job;
import com.game.gang.service.GangService;
import com.game.role.bean.ConcreteRole;
import com.game.role.service.RoleService;
import com.game.utils.MapUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.text.MessageFormat;

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

    @Autowired
    private RoleService roleService;
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

        //channel
        Channel channel = role.getChannel();
        //创建工会
        buildGang(flag,gangName,role,channel);


    }

    /**
     * 创建工会
     * @param flag
     * @param gangName
     * @param role
     */
    private void buildGang(GangMemberEntity flag,String gangName,ConcreteRole role,Channel channel) {
        //没工会可创建工会
        if(flag==null){
            //插入工会
            gangService.insertGang(gangName);
            //查询工会
            GangEntity gangEntity =  gangService.queryGang(gangName);
            //插入工会会长
            GangMemberEntity entity = new GangMemberEntity();

            entity.setRole(role);
            entity.setGang(gangEntity);
            entity.setJob(Job.CHARIMEN.getName());
            gangService.insertGangMember(entity);
            channel.writeAndFlush("成功创建工会");
        }else{//有工会就返回提示信息
            channel.writeAndFlush(role.getName()+"已经有工会了!\t工会："+flag.getGang().getName());
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
        GangMemberEntity entity = belongGang(roleName);
        //查询角色
        ConcreteRole role = getRole(roleName);
        //加入工会
        join(entity,role,gangName);
    }

    /**
     * 加入工会
     * @param entity
     * @param role
     * @param gangName
     */
    private void join(GangMemberEntity entity,ConcreteRole role,String gangName) {

        //channel
        Channel channel = role.getChannel();
        //没的话加入工会
        if(entity==null){
            GangMemberEntity newEntity = new GangMemberEntity();
            newEntity.setRole(role);
            //查询工会
            GangEntity gangEntity =  gangService.queryGang(gangName);
            newEntity.setGang(gangEntity);
            //设置工会职位
            newEntity.setJob(Job.GENERAL.getName());
            gangService.insertGangMember(newEntity);
            //返回信息
            channel.writeAndFlush("成功加入工会"+gangName);
        }else{//有就返回提示信息
            channel.writeAndFlush("你已经加入工会");
        }
    }

    /**
     * 解散工会
     */
    @RequestAnnotation("/dismiss")
    public void dismiss(String roleName,String gangName){
        //判断角色是否有工会

        //判断角色是否会长


    }

    /**
     *
     * @param roleName
     * @param number
     */
    @RequestAnnotation("/donateMoney")
    public void donate(String roleName,String number){
        //获取工会
        ConcreteRole role = getRole(roleName);
        GangEntity gangEntity = gangService.queryGangByRoleName(role.getId());
        int cost = Integer.parseInt(number);
        Integer money = role.getMoney();
        role.setMoney(money-cost);
        //更新角色
        roleService.updateRole(role);
        gangEntity.setFunds(gangEntity.getFunds()+cost);
        //更新工会基金
        gangService.updateGangEntity(gangEntity);
        String outputContent = "【通知】：{0}向{1}捐献{2}金币";
        role.getChannel().writeAndFlush(MessageFormat.format(outputContent,role.getName(),gangEntity.getName(),number));
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
