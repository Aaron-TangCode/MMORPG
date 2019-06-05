package com.game.dispatcher;

import org.springframework.stereotype.Component;

/**
 * @ClassName ResultDTO
 * @Description 结果的封装类 单例 饿汉
 * @Author DELL
 * @Date 2019/5/2815:25
 * @Version 1.0
 */
@Component
public class ResultDTO {

    /**
     * 访问代码  200  成功  404 找不到资源  500 服务器异常
     */
    private String code;
    /**
     * 消息内容
     */
    private String msg;
    private static ResultDTO dto = new ResultDTO();

    private ResultDTO() {
    }
    public static ResultDTO newInstrance(String code,String msg) {
        dto.setMsg(msg);
        dto.setCode(code);
        return dto;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public static ResultDTO getDto() {
        return dto;
    }
    public static void setDto(ResultDTO dto) {
        ResultDTO.dto = dto;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
