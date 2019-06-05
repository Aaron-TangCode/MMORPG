package com.test.testgameserverclient;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Message {
 
	private Header header;
 
	private String data;
 
	public Header getHeader() {
		return header;
	}
 
	public void setHeader(Header header) {
		this.header = header;
	}
 
	public String getData() {
		return data;
	}
 
	public void setData(String data) {
		this.data = data;
	}
 
	public Message(Header header) {
		this.header = header;
	}
 
	public Message(Header header, String data) {
		this.header = header;
		this.data = data;
	}
 
	public byte[] toByte() {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(MessageDecoder.PACKAGE_TAG);
		out.write(header.getEncode());
		out.write(header.getEncrypt());
		out.write(header.getExtend1());
		out.write(header.getExtend2());
		byte[] bb = new byte[32];
		byte[] bb2 = header.getSessionid().getBytes();
		for (int i = 0; i < bb2.length; i++) {
			bb[i] = bb2[i];
		}
 
		try {
			out.write(bb);
 
			byte[] bbb = data.getBytes("UTF-8");
			out.write(intToBytes2(bbb.length));
			out.write(intToBytes2(header.getCammand()));
			out.write(bbb);
			out.write('\n');
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toByteArray();
	}
 
	public static byte[] intToByte(int newint) {
		byte[] intbyte = new byte[4];
		intbyte[3] = (byte) ((newint >> 24) & 0xFF);
		intbyte[2] = (byte) ((newint >> 16) & 0xFF);
		intbyte[1] = (byte) ((newint >> 8) & 0xFF);
		intbyte[0] = (byte) (newint & 0xFF);
		return intbyte;
	}
 
	public static int bytesToInt(byte[] src, int offset) {
		int value;
		value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16) | ((src[offset + 3] & 0xFF) << 24));
		return value;
	}
 
	public static byte[] intToBytes2(int value) {
		byte[] src = new byte[4];
		src[0] = (byte) ((value >> 24) & 0xFF);
		src[1] = (byte) ((value >> 16) & 0xFF);
		src[2] = (byte) ((value >> 8) & 0xFF);
		src[3] = (byte) (value & 0xFF);
		return src;
	}
 
	public static void main(String[] args) {
		ByteBuf heapBuffer = Unpooled.buffer(8);
		System.out.println(heapBuffer);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(intToBytes2(1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] data = out.toByteArray();
		heapBuffer.writeBytes(data);
		System.out.println(heapBuffer);
		int a = heapBuffer.readInt();
		System.out.println(a);
	}
 
}