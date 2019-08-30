#客户端的输入指令
```
//游戏介绍
本游戏是一款MMORPG游戏，多人在线角色扮演游戏。游戏玩法：有玩家PK，刷副本，掉装备，排行榜，玩家互动，拍卖行，工会等新奇有趣玩法。  
游戏名字：江湖传奇
//操作指引
- 1、启动服务端：com.game.server.SpringMain
- 2、启动客户端：com.hailintang.client.StartClient
- 3、在客户端端口输入相关指令

##### 相关指令如下：
客户端连接后：（有以下信息提示）
欢迎进入游戏！登录:Login		注册:Register		帮助文档:Help
Connect to Server Successfully!

输入Help指令，会有相关操作文档出现：
游戏的指令有：
用户相关：
	 Login：登录
	 Register：注册
角色相关：
	 ChooseRole：创建角色
	 RoleInfo：获取角色信息
	 UseGoods：角色使用物品
场景相关：
	 GetMap：获取角色当前所在地图
	 Move：角色移动，切换地图
NPC相关：
	 TalkToNpc：和NPC谈话
背包相关：
	 GetGoods：获取物品或装备
	 DiscardGoods：丢弃物品或装备
	 ShowGoods：显示玩家装备或物品
技能相关：
	 UpgradeSkill：升级技能
	 StudySkill：学习新技能
	 RolePK：角色PK
装备相关：
	 AddEquip：装备武器
	 RemoveEquip：移除武器
	 ShowEquip：展示武器
副本相关：
	 EnterDuplicate：（单人）进入副本
	 TeamEnterDuplicate：（组队）进入副本
	 QueryTeam：查询队伍
	 CreateTeam：创建队伍
	 JoinTeam：加入队伍
	 ExitTeam：退出队伍
	 DismissTeam：解散队伍
	 UseSkillAttackBoss：使用技能攻击boss
商店相关：
	 Buy：购买装备或物品
聊天相关：
	 ChatAll：世界聊天
	 ChatSomeone：私聊
邮件相关：
	 SendGoods：发送物品或装备
交易相关：
	 TradeGoods：交易
	 RequestTrade：请求交易
	 ConfirmTrade：确认交易
	 TradingGoods：交易物品
	 TradingMoney：交易金额
工会相关：
	 CreateGang：创建工会
	 JoinGang：加入工会
	 DismissGang：解散工会
	 DonateMoney：捐钱
拍卖行相关：
	 Bidding：竞拍物品
	 Recycle：物品下架
	 Publish：物品上架
	 QueryAuction：查询拍卖行物品
排行榜相关：
	 QueryRank：排行榜
退出游戏相关：
	 Exit：退出当前游戏
任务相关：
	 QueryReceivableTask：查询可接受任务
	 QueryReceivedTask：查询已接受任务
	 ReceiveTask：接受任务
	 DiscardTask：放弃任务
```