package com.game.skill.handler;

import com.game.protobuf.protoc.MsgSkillInfoProto;
import com.game.skill.service.SkillService;
import com.game.utils.ProtobufUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.game.server.request.RequestSkillInfoType.*;

/**
 * @ClassName SkillHandler
 * @Description 技能处理器
 * @Author DELL
 * @Date 2019/8/12 15:40
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class SkillHandler extends SimpleChannelInboundHandler<MsgSkillInfoProto.RequestSkillInfo> {
    /**
     * 协议信息
     */
    private MsgSkillInfoProto.ResponseSkillInfo responseSkillInfo;
    /**
     * 技能服务
     */
    @Autowired
    private SkillService skillService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MsgSkillInfoProto.RequestSkillInfo requestSkillInfo) throws Exception {
        //channel
        Channel channel = ctx.channel();
        //协议号
        int typeNum = requestSkillInfo.getType().getNumber();
        //分发任务
        switch (typeNum) {
            case UPGRADESKILL :
                responseSkillInfo = skillService.upgradeSkill(channel,requestSkillInfo);
                break;
            case STUDYSKILL :
                responseSkillInfo = skillService.studySkill(channel,requestSkillInfo);
                break;
            case ROLEPK :
                responseSkillInfo = skillService.rolePK(channel,requestSkillInfo);
                break;
            case USESKILL :
                responseSkillInfo = skillService.useSkill(channel,requestSkillInfo);
                break;
                default:
                    break;
        }
        //发送消息
        ProtobufUtils.sendProtobufMessage(ctx,responseSkillInfo);
    }
}
