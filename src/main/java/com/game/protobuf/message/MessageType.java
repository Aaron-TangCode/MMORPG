package com.game.protobuf.message;

import com.game.protobuf.protoc.MsgMapInfoProto;
import com.game.protobuf.protoc.MsgRoleInfoProto;
import com.game.protobuf.protoc.MsgUserInfoProto;
import com.google.protobuf.MessageLite;

import java.lang.reflect.Constructor;

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
    RESPONSE_MAPINFO_PROTO(0x06,MsgMapInfoProto.ResponseMapInfo.class);

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
