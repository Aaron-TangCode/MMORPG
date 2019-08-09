package com.game.server.local;


import com.game.protobuf.message.MessageType;
import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;

public class LocalMessageMap {

    public static Map<Class<Message>, Integer> messageMap = new HashMap<>();

    public static void readAllMessageType(){
        for (MessageType messageType : MessageType.values()) {
            messageMap.put(messageType.getMessageLite(), messageType.getProtoCode());
        }
    }
}