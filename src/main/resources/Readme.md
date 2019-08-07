#客户端的输入指令
```
//游戏介绍
本游戏是一款MMORPG游戏，多人在线角色扮演游戏。游戏玩法：有玩家PK，刷副本，掉装备，排行榜，玩家互动，拍卖行，工会等新奇有趣玩法。  
游戏名字：江湖传奇
//操作指引
- 1、启动服务端：com.game.server.SpringMain
- 2、启动客户端：com.game.client.Client
- 3、在客户端端口输入相关指令

##### 相关指令如下：
//登录操作
{type:"/user/login",username:"123",password:"123"}
{type:"/user/login",username:"456",password:"123"}
{type:"/user/login",username:"789",password:"123"}
//注册用户
{type:"/user/registerService",username:"123",password:"123",ackpassword:"123"}
//注册角色
{type:"/register/role",username:"tanghailin",roleName:"role8",occupationId:1}
//登出
{type:"/user/logout",username:"123"}
//根据roleName获取它所在的地图
{type:"/role/getMap",roleName:"role4"}
//获取当前地图角色信息
{type:"/user/getRoleInfo",mapname:"村子"}
{type:"/user/getRoleInfo",mapname:"森林"}

//切换地图
{type:"/map/moveTo",roleName:"role4",dest:"森林"}
{type:"/map/moveTo",roleName:"role4",dest:"村子"}

//获取当前地图
{type:"/map/moveTo",username:"123",dest:"森林"}
{type:"/map/moveTo",username:"123",dest:"村子"}

//获取某个角色的状态信息
{type:"/role/getRoleState",roleName:"role4"}

//给某个角色加血
{type:"/role/roleByAddBlood",roleName:"role4"}
//给某个角色减血
{type:"/role/roleByHurted",roleName:"role4"}
//和某个NPC谈话
{type:"/npc/taklwithnpc",rolename:"role4",npcname:"NPC1"}
//某个角色学习某个技能
{type:"/skill/studyskill",roleName:"role4",skillName:"噬魂之手"}
{type:"/skill/studyskill",roleName:"role4",skillName:"血之狂暴"}

//某个角色升级某个技能
{type:"/skill/upgradeskill",roleName:"role4",skillName:"血之狂暴"}

//某个角色用某个技能攻击某个怪兽
{type:"/skill/useskill",roleName:"role4",skillName:"血之狂暴",monsterName:"怪兽4号"}
//某个角色获取某个物品
{type:"/backpack/getGoods",roleName:"role4",goodsName:"小血瓶"}
{type:"/backpack/getGoods",roleName:"role4",goodsName:"小蓝瓶"}
{type:"/backpack/getGoods",roleName:"role4",goodsName:"太刀"}
{type:"/backpack/getGoods",roleName:"role4",goodsName:"手枪"}
{type:"/backpack/getGoods",roleName:"role4",goodsName:"技能包"}
//某个角色使用某个物品
{type:"/role/roleUseGoods",roleName:"role4",goodsName:"手枪"}
{type:"/role/roleUseGoods",roleName:"role4",goodsName:"太刀"}
{type:"/role/roleUseGoods",roleName:"role4",goodsName:"小血瓶"}
{type:"/role/roleUseGoods",roleName:"role4",goodsName:"小蓝瓶"}

//把某个装备添加到装备栏
{type:"/equipment/addEquipment",roleName:"role4",goodsName:"太刀"}
{type:"/equipment/addEquipment",roleName:"role4",goodsName:"手枪"}

//某个角色在商店系统购买某个物品或装备
{type:"/shop/buy",roleName:"role4",goodsName:"小血瓶"}

//和世界聊天
{type:"/chat/all",roleName:"role4",content:"来自上帝的祝福"}

//和某个角色私聊
{type:"/chat/someone",roleName:"role4",roleTarget:"role5",content:"来自上帝的祝福"}

//邮件发送道具
{type:"/email/goods",goodsName:"小血瓶"}

//PK模块：A攻击B
{type:"/skill/rolePK",roleName:"role4",skillName:"血之狂暴",targetRoleName:"role5"}

//刷副本
{type:"/duplicate/attackboss1",roleName:"role4",bossName:"怪兽4号",mapName:"森林"}

//交易系统
{type:"/trade/tradegoods",roleName1:"role4",roleName2:"role5",goodsName:"小血瓶"}
//请求交易
{type:"/trade/requestTrade",roleName1:"role4",roleName2:"role5"}
//确认交易
{type:"/trade/confirmTrade",roleName1:"role5",roleName2:"role4",uuid:""}
//进行交易物品
{type:"/trade/tradingGoods",uuid:"",goodsName:"小蓝瓶"}
//进行交易金币
{type:"/trade/tradingMoney",uuid:"",number:"10"}


//工会系统
//创建工会
{type:"/gang/create",roleName:"role4",gangName:"王者工会"}
//加入工会
{type:"/gang/join",roleName:"role5",gangName:"王者工会"}
//捐款给工会
{type:"/gang/donateMoney",roleName:"role4",number:"10"}

//交易行系统
//发布物品到交易平台(一口价)
{type:"/auction/publish",seller:"role4",goodsName:"小蓝瓶",number:"1",price:"10",isNow:"true"}

//发布物品到交易平台(竞拍)
{type:"/auction/publish",seller:"role4",goodsName:"小蓝瓶",number:"1",price:"10",isNow:"false"}

//查询交易平台的物品
{type:"/auction/queryAuction",roleName:"role4"}

//撤回交易平台的物品
{type:"/auction/recycle",roleName:"role4",auctionId:"1"}

//竞拍叫价
{type:"/auction/bidding",roleName:"role5",auctionId:"",money:"20"}

//任务系统
//查询可接受的任务
{type:"/task/queryReceivableTask",roleName:"role4"}
//查询已接受的任务
{type:"/task/queryReceivedTask",roleName:"role4"}

//接受任务
{type:"/task/receiveTask",roleName:"role4",taskId:"1"}
//放弃任务
{type:"/task/discardTask",roleName:"role4",taskId:"1"}

//排行榜查询
{type:"/rank/queryRank",roleName:"role4"}
```