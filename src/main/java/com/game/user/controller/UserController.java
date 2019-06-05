package com.game.user.controller;

import com.game.dispatcher.RequestAnnotation;
import com.game.role.bean.ConcreteRole;
import com.game.user.service.Login;
import com.game.user.service.RegisterService;
import com.game.role.service.RoleService;
import com.game.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 用户controller
 */
@RequestAnnotation("/user")
@Component
public class UserController {
	@Autowired
	private Login login;

	@Autowired
	private RegisterService registerService;

	@Autowired
	private RoleService roleService;

	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestAnnotation("/login")
	public String login(String username, String password) {
		boolean isSuccess = login.login(username,password);
		ConcreteRole role = this.getRoleAfterLoginSuccess(username);
		if(isSuccess){
			MapUtils.getMapRole().put(username,role);
			return role.getName()+"上线了";
		}else{
			System.out.println("登录失败");
		}
		return null;
	}

	/**
	 * 用户登出
	 * @param username
	 * @return
	 */
	@RequestAnnotation("/logout")
	public String logout(String username) {
		//通过username找到map中的role
		ConcreteRole role = MapUtils.getMapRole().get(username);
		//移除角色信息，下线
		MapUtils.getMapRole().remove(username);
		return role.getName()+"下线了";
	}

	/**
	 * 打印当前场景的所有角色信息
	 * @param mapname
	 * @return
	 */
	@RequestAnnotation("/getRoleInfo")
	public String getRoleInfo(String mapname) {
		//获取当前场景的所有角色信息
		Map<String,ConcreteRole> map = MapUtils.getMapRole();
		return returnRoleInfo(map,mapname);
	}

	/**
	 * 返回用户角色信息
	 * @param map
	 * @param mapname
	 * @return
	 */
	private String returnRoleInfo(Map<String, ConcreteRole> map,String mapname) {
		Set<String> sets = map.keySet();
		StringBuffer sb = new StringBuffer();
		Iterator<String> iterator = sets.iterator();
		while(iterator.hasNext()){
			String name = iterator.next();
			String map_name = map.get(name).getConcreteMap().getName();
			if (map_name.equals(mapname)){
				sb.append(map.get(name).getName()+","+map.get(name).getConcreteMap().getName()+","+map.get(name).getHp());
			}
			if(iterator.hasNext()){
				sb.append(";");
			}
		}
		return sb.toString();
	}

	/**
	 * 注册
	 * @param username
	 * @param password
	 * @param ackpassword
	 * @return
	 */
	@RequestAnnotation("/registerService")
	public String registerService(String username,String password,String ackpassword){
		boolean isSuccess = registerService.register(username,password,ackpassword);
		if(isSuccess){
			return "成功注册用户"+username+",请继续注册游戏角色";
		}else{
			return "注册失败";
		}
	}

	/**
	 * 成功登录后，获取用户对应人物角色
	 * @param username
	 * @return
	 */
	public ConcreteRole getRoleAfterLoginSuccess(String username){
		int id = login.getUserRoleIdByUsername(username);
		//查找role
		ConcreteRole role = roleService.getRole(id);
		//返回role
		return role;
	}
}