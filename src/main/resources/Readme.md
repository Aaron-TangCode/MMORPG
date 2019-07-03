#客户端的输入指令
```
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
{type:"/map/moveTo",username:"123",dest:"森林"}
{type:"/map/moveTo",username:"123",dest:"村子"}

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
```