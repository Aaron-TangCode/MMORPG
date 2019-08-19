package com.game.protobuf.message;

import com.game.protobuf.protoc.*;
import com.google.protobuf.MessageLite;

import java.lang.reflect.Constructor;

/**
 * 协议信息
 */
public enum MessageType {
    /**
     * 用户信息请求
     */
    REQUEST_USERINFO_PROTO(0x01, MsgUserInfoProto.RequestUserInfo.class),
    RESPONSE_USERINFO_PROTO(0x02,MsgUserInfoProto.ResponseUserInfo.class),
    //role
    REQUEST_ROLEINFO_PROTO(0x03,MsgRoleInfoProto.RequestRoleInfo.class),
    RESPONSE_ROLEINFO_PROTO(0x04,MsgRoleInfoProto.ResponseRoleInfo.class),
    //map
    REQUEST_MAPINFO_PROTO(0x05, MsgMapInfoProto.RequestMapInfo.class),
    RESPONSE_MAPINFO_PROTO(0x06,MsgMapInfoProto.ResponseMapInfo.class),
    //npc
    REQUEST_NPCINFO_PROTO(0x07, MsgNpcInfoProto.RequestNpcInfo.class),
    RESPONSE_NPCINFO_PROTO(0x08,MsgNpcInfoProto.ResponseNpcInfo.class),
    //goods
    REQUEST_GOODSINFO_PROTO(0x09,MsgGoodsInfoProto.RequestGoodsInfo.class),
    RESPONSE_GOODSINFO_PROTO(0x0A,MsgGoodsInfoProto.ResponseGoodsInfo.class),
    //skill
    REQUEST_SKILLINFO_PROTO(0x0B, MsgSkillInfoProto.RequestSkillInfo.class),
    RESPONSE_SKILLINFO_PROTO(0x0C,MsgSkillInfoProto.ResponseSkillInfo.class),
    //equip
    REQUEST_EQUIPINFO_PROTO(0x0D, MsgEquipInfoProto.RequestEquipInfo.class),
    RESPONSE_EQUIPINFO_PROTO(0x0E,MsgEquipInfoProto.ResponseEquipInfo.class),
    //duplicate
    REQUEST_BOSSINFO_PROTO(0x0F, MsgBossInfoProto.RequestBossInfo.class),
    RESPONSE_BOSSINFO_PROTO(0x10,MsgBossInfoProto.ResponseBossInfo.class),
    //shop
    REQUEST_SHOPINFO_PROTO(0x11, MsgShopInfoProto.RequestShopInfo.class),
    RESPONSE_SHOPINFO_PROTO(0x12,MsgShopInfoProto.ResponseShopInfo.class),
    //chat
    REQUEST_CHATINFO_PROTO(0x13, MsgChatInfoProto.RequestChatInfo.class),
    RESPONSE_CHATINFO_PROTO(0x14,MsgChatInfoProto.ResponseChatInfo.class),
    //email
    REQUEST_EMAILINFO_PROTO(0x15, MsgEmailInfoProto.RequestEmailInfo.class),
    RESPONSE_EMAILINFO_PROTO(0x16,MsgEmailInfoProto.ResponseEmailInfo.class),
    //trade
    REQUEST_TRADEINFO_PROTO(0x17, MsgTradeInfoProto.RequestTradeInfo.class),
    RESPONSE_TRADEINFO_PROTO(0x18,MsgTradeInfoProto.ResponseTradeInfo.class),
    //gang
    REQUEST_GANGINFO_PROTO(0x19, MsgGangInfoProto.RequestGangInfo.class),
    RESPONSE_GANGINFO_PROTO(0x1A,MsgGangInfoProto.ResponseGangInfo.class),
    //auction
    REQUEST_AUCTIONINFO_PROTO(0x1B, MsgGangInfoProto.RequestGangInfo.class),
    RESPONSE_AUCTIONINFO_PROTO(0x1C,MsgGangInfoProto.ResponseGangInfo.class),
    //rank
    REQUEST_RANKINFO_PROTO(0x1D, MsgRankInfoProto.RequestRankInfo.class),
    RESPONSE_RANKINFO_PROTO(0x1E,MsgRankInfoProto.ResponseRankInfo.class);
    public Integer protoCode;
    public Class messageLite;

    MessageType(Integer protoCode, Class messageLite) {
        this.protoCode = protoCode;
        this.messageLite = messageLite;
    }

    public Integer getProtoCode() {
        return protoCode;
    }

    public Class getMessageLite() {
        return messageLite;
    }

    /**
     * 通过协议序号 -> 协议对象
     *
     * @param code
     * @return
     */
    public static Object getProtoInstanceByCode(Integer code) throws Exception{
        for (MessageType protoType : MessageType.values()) {
            if (protoType.getProtoCode().intValue() == code){

                // 反射生成协议对象
                Constructor constructor = protoType.getMessageLite().getDeclaredConstructor();
                constructor.setAccessible(true);

                return constructor.newInstance();
            }
        }
        return null;
    }

    /**
     * 类对象 -> 序号
     *
     * @param messageLite
     * @return
     */
    public static Byte getProtoCodeFromType(MessageLite messageLite){
        for (MessageType protoType : MessageType.values()) {
            if (messageLite.getClass() == protoType.getMessageLite()){
                return Byte.valueOf(String.valueOf(protoType.getProtoCode()));
            }
        }
        return null;
    }
}
