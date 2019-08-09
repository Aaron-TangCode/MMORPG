package com.game.server.codec;

import com.game.protobuf.message.MessageType;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class CustomProtobufEncoder extends MessageToByteEncoder<MessageLite> {

    @Override
    protected void encode(
            ChannelHandlerContext ctx, MessageLite msg, ByteBuf out) throws Exception {

        byte[] body = msg.toByteArray();
        byte[] header = encodeHeader(msg, (short)body.length);

        out.writeBytes(header);
        out.writeBytes(body);

        return;
    }

    private byte[] encodeHeader(MessageLite msg, short bodyLength) {
        byte b = 0x0f;

        // 根据协议对象查找对应的协议号
        if (msg != null){
            b = MessageType.getProtoCodeFromType(msg);
        }

        byte[] header = new byte[4];
        header[0] = (byte) (bodyLength & 0xff);
        header[1] = (byte) ((bodyLength >> 8) & 0xff);
        header[2] = 0; // 保留字段
        header[3] = b;

        return header;

    }
}
