package com.game.user.service;

import com.game.backpack.manager.LoadGoodsData;
import com.game.property.manager.InjectProperty;
import com.game.protobuf.message.ContentType;
import com.game.protobuf.message.ResultCode;
import com.game.protobuf.protoc.MsgUserInfoProto;
import com.game.protobuf.service.ProtoService;
import com.game.role.bean.ConcreteRole;
import com.game.user.bean.User;
import com.game.user.handler.UserHandler;
import com.game.user.manager.LocalUserMap;
import com.game.user.repository.UserRepository;
import com.game.utils.CacheUtils;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserService
 * @Description 用户服务
 * @Author DELL
 * @Date 2019/7/3 16:52
 * @Version 1.0
 */
@Service
public class UserService {
    @Autowired
    private LoadGoodsData loadGoodsData;
    /**
     * 用户数据范文
     */
    @Autowired
    private UserRepository userRepository;
    /**
     * 登录服务
     */
    @Autowired
    private Login login;
    /**
     * 注册服务
     */
    @Autowired
    private RegisterService registerService;
    /**
     * 用户控制器
     */
    @Autowired
    private UserHandler userHandler;
    /**
     * proto服务
     */
    @Autowired
    private ProtoService protoService;
    /**
     * 注入属性服务
     */
    @Autowired
    private InjectProperty injectProperty;

    /**
     * 更新用户
     * @param user
     */
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    /**
     * 登录
     * @param channel channel
     * @param requestUserInfo requestUserInfo
     * @return 协议信息
     */
    public MsgUserInfoProto.ResponseUserInfo login(Channel channel, MsgUserInfoProto.RequestUserInfo requestUserInfo) {
        //获取账号和密码
        String username = requestUserInfo.getUsername();
        String password = requestUserInfo.getPassword();

        //省略部分校验

        //校验数据库
        boolean is_success = this.login.login(username, password);

        //其他一系列操作
        ConcreteRole role = null;
        //获取user
        User user = userRepository.selectUserByUsername(username);
        //判断登录与否
        Channel oldChannel = LocalUserMap.getUserChannelMap().get(user.getId());
        //重复登录
        if(oldChannel!=null){
            oldChannel.writeAndFlush(MsgUserInfoProto.ResponseUserInfo.newBuilder()
                            .setContent(ContentType.REPEATED_LOGIN)
                            .setResult(ResultCode.FAIL)
                            .setUserId(user.getId())
                            .build()
            );

            oldChannel.close();
            LocalUserMap.getUserChannelMap().put(user.getId(),channel);
        }

        if(is_success){

            role = userHandler.getRoleAfterLoginSuccess(username);

            //绑定userId和channel
            LocalUserMap.getChannelUserMap().put(channel,user.getId());
            if(role==null){
                //用户还没创建角色
                return MsgUserInfoProto.ResponseUserInfo.newBuilder()
                        .setType(MsgUserInfoProto.RequestType.LOGIN)
                        .setContent(ContentType.UNCREATE_ROLE)
                        .setResult(ResultCode.FAIL)
                        .setUserId(user.getId())
                        .build();
            }

            //初始化玩家数据
            initUserState(user.getId(), role, channel);
            //注入物品信息
            loadGoodsData.loadingGoodsData(role.getId());
            //注入属性
            injectProperty.initProperty(role.getName());
            // 成功消息返回
            return MsgUserInfoProto.ResponseUserInfo.newBuilder()
                    //请求类型
                    .setType(MsgUserInfoProto.RequestType.LOGIN)
                    //内容
                    .setContent(ContentType.LOGIN_SUCCESS)
                    //内容
                    .setResult(ResultCode.SUCCESS)
                    //userid
                    .setUserId(user.getId())
                    //设置角色
                    .setRole(protoService.transToRole(role))
                    .build();
        }else{
            return MsgUserInfoProto.ResponseUserInfo.newBuilder()
                    .setType(MsgUserInfoProto.RequestType.LOGIN)
                    .setContent(ContentType.LOGIN_FAIL)
                    .setResult(ResultCode.FAIL)
                    .build();
        }

    }

    /**
     * 注册
     * @param channel channel
     * @param requestUserInfo requestUserInfo
     * @return 协议信息
     */
    public MsgUserInfoProto.ResponseUserInfo register(Channel channel, MsgUserInfoProto.RequestUserInfo requestUserInfo) {
        //获取用户名和密码
        String username = requestUserInfo.getUsername();
        String password = requestUserInfo.getPassword();
        //判断用户是否存在,且存入数据库
        boolean is_success = registerService.register(username, password, password);

        //存在就返回提示消息
        if(is_success){
            return MsgUserInfoProto.ResponseUserInfo.newBuilder()
                    //请求类型
                    .setType(MsgUserInfoProto.RequestType.REGISTER)
                    //内容
                    .setContent(ContentType.REGISTER_SUCCESS)
                    //结果
                    .setResult(ResultCode.SUCCESS)
                    .build();
        }else{
            return MsgUserInfoProto.ResponseUserInfo.newBuilder()
                    .setType(MsgUserInfoProto.RequestType.REGISTER)
                    .setContent(ContentType.REGISTER_FAIL)
                    .setResult(ResultCode.FAIL)
                    .build();
        }
    }

    /**
     * 退出
     * @param channel channel
     * @param requestUserInfo requestUserInfo
     * @return 协议信息
     */
    public MsgUserInfoProto.ResponseUserInfo exit(Channel channel, MsgUserInfoProto.RequestUserInfo requestUserInfo) {
        //获取角色
        int userId = requestUserInfo.getUserId();
        ConcreteRole role = LocalUserMap.getUserRoleMap().get(userId);

        //清楚缓存
        unloadUserState(userId,role,channel);
        //
        return MsgUserInfoProto.ResponseUserInfo.newBuilder()
                .setType(MsgUserInfoProto.RequestType.EXIT)
                .setContent(ContentType.EXIT_SUCCESS)
                .build();
    }

    /**
     * 初始化玩家数据
     * @param userId  userId
     * @param role role
     * @param channel  channel
     */
    private void initUserState(Integer userId, ConcreteRole role, Channel channel) {

        // 本地保存
        LocalUserMap.getUserRoleMap().put(userId,role);
        // 分配用户的业务线程
//        UserExecutorManager.bindUserExecutor(userId);
        //注入channel值
        role.setChannel(channel);
        // 绑定 userId-channel
        LocalUserMap.getUserChannelMap().put(userId,channel);
        // 绑定 channel-userId
        LocalUserMap.getChannelUserMap().put(channel,userId);
        //roleName-role
        CacheUtils.addRole(role.getName(),role);
    }

    /**
     * 清楚缓存数据
     * @param userId userId
     * @param role role
     * @param channel channel
     */
    private void unloadUserState(Integer userId, ConcreteRole role, Channel channel){
        //清楚缓存
        LocalUserMap.getUserRoleMap().remove(userId);

        LocalUserMap.getUserChannelMap().remove(userId);

        LocalUserMap.getChannelUserMap().remove(channel);

        CacheUtils.getRoleByName().remove(role.getName());

        role = null;
    }
}
