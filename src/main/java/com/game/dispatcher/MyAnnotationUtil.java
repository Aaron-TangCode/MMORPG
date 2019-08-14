package com.game.dispatcher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.game.role.bean.ConcreteRole;
import com.game.role.repository.RoleRepository;
import com.game.server.SpringMain;
import com.game.user.repository.LoginRepository;
import com.game.utils.MapUtils;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 注释工具类  用来解析注解类
 * 此类拥有如下功能
 * 1.解析properties文件  得到Controller控制器类全限定名(跟Spring类似配置，指定扫描的包)
 * 2.得到该包下所有的添加了自定义注解的控制器类(可能有子包) 装进集合
 * 3.得到添加了自定义注解的方法 装进集合
 * 4.利用反射创建控制器对象,并反射执行方法(这个功能不应该这个类有,但还是装在这个类)
 */
@Component
public class MyAnnotationUtil {
    /**
     * 装类+方法资源名-->类+方法的缓存
     */
    private static Map<String,ClassAndMethodDTO> map = new HashMap<>();
    /**
     * 控制器是否单例
     */
    private static boolean isSingle = true;
    /**
     * 控制器类对象
     */
    private static Map<Class,Object> controllerClasses =new HashMap<>();

    /**
     * 获取方法的参数名对应的值
     * @param method  指定
     * @return
     */
    private static List<Object> getMethodParameters(Class clz, Method method, JSONObject json){
        List<Object> values = new ArrayList<>();
        ClassPool pool = ClassPool.getDefault();
        try {
            CtClass ctClass = pool.get(clz.getName());
            CtMethod ctMethod = ctClass.getDeclaredMethod(method.getName());
            // 使用javassist的反射方法的参数名
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                    .getAttribute(LocalVariableAttribute.tag);
            if (attr != null) {
                CtClass[] types = ctMethod.getParameterTypes();
                // 非静态的成员函数的第一个参数是this
                int pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
                for (int i = 0; i < types.length; i++) {
                    //参数名
                    String name = attr.variableName(i + pos);
                    //参数类型
                    String type = types[i].getName();
                    //解析值
                    if (type.contains("String")) {
                        values.add(json.getString(name));
                    }
                    if (type.contains("Boolean")) {
                        values.add(json.getBoolean(name));
                    }
                    if (type.contains("boolean")) {
                        values.add(json.getBooleanValue(name));
                    }
                    if (type.contains("Integer")) {
                        values.add(json.getInteger(name));
                    }
                    if (type.contains("int")) {
                        values.add(json.getIntValue(name));
                    }
                    if (type.contains("Long")) {
                        values.add(json.getLong(name));
                    }
                    if (type.contains("long")) {
                        values.add(json.getLongValue(name));
                    }
                    if (type.contains("Short")) {
                        values.add(json.getShort(name));
                    }
                    if (type.contains("short")) {
                        values.add(json.getShortValue(name));
                    }
                    if (type.contains("Byte")) {
                        values.add(json.getByte(name));
                    }
                    if (type.contains("byte")) {
                        values.add(json.getByteValue(name));
                    }
                    if (type.contains("Char")) {
                        values.add(json.getString(name).charAt(0));
                    }
                    if (type.contains("Character")) {
                        values.add(new Character(json.getString(name).charAt(0)));
                    }
                    if (type.contains("Float")) {
                        values.add(json.getFloat(name));
                    }
                    if (type.contains("float")) {
                        values.add(json.getFloatValue(name));
                    }
                    if (type.contains("Double")) {
                        values.add(json.getDouble(name));
                    }
                    if (type.contains("double")) {
                        values.add(json.getDoubleValue(name));
                    }
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return values;
}
    /**
     * 供外部调用的方法,具备如下功能
     * A.传进来一个加密的字符串,解析出需要的如下内容：
     *  1.请求资源  找到那个控制器类的那个方法
     *   2.参数 对应类的对应方法需要的参数
     *  B.通过反射创建对应控制器对象
     *  C.调用控制器类的方法
     * @param content
     * @return
     */
    public static String requestService(String content, ChannelHandlerContext ctx) {
        //转换JSON对象
        JSONObject json = (JSONObject)JSON.parse(content);

        String path = "/user/login";
        //获取 资源名
        String type= (String)json.get("type");
        if(type.contains(path)){
            String username = json.getString("username");
            String password = json.getString("password");
            LoginRepository loginRepository = new LoginRepository();
            final boolean isSuccess = loginRepository.login(username,password);
            if(isSuccess){
                RoleRepository roleRepository = new RoleRepository();
                int roleId = loginRepository.getUserRoleIdByUsername(username);
                ConcreteRole role = roleRepository.getRole(roleId);
                role.setChannel(ctx.channel());
                //加角色名-角色对象
                MapUtils.getMapRolename_Role().put(role.getName(),role);
            }
        }
        //将类的资源名和方法的进行拼接
        ClassAndMethodDTO dto = map.get(type);
        if (dto==null) {
            return JSON.toJSONString(ResultDTO.newInstrance("404", "找不到资源:"+type));
        }
        Object result = null;
        try {
            Class clz = dto.getClz();
            //创建类的对象
            Object obj = SpringMain.applicationContext.getBean(clz);
            Method method = dto.getMethod();
            //https://blog.csdn.net/wwwwenl/article/details/53427039
            //获取参数名称参考上述链接博客的javassist方式
            List<Object> parameters = getMethodParameters(clz,method,json);
            //执行方法
            result = method.invoke(obj,parameters.toArray());
            JSON.toJSONString(result);
        } catch (Exception e) {
            return JSON.toJSONString(ResultDTO.newInstrance("500",e.getMessage()));
        }
        if(result!=null){
            return result.toString();
        }
        return null;
    }

    static {
        //解析资源文件得到资源对象
        Properties prop = parsePackAgeByProperties("src/main/resources/my.properties");
        //得到是否单例
        String single = prop.getProperty("my.single");
        if (StringUtil.isNotNullOrEmpty(single)) {
            isSingle = Boolean.parseBoolean(single);
        }
        //得到基础控制器包
        String basePackage=prop.getProperty("my.controller.package");
        //解析失败
        if (!StringUtil.isNotNullOrEmpty(basePackage)) {
            throw new RuntimeException("解析properties资源文件失败,请检查是否是properties文件路径有错或资源文件中属性名是否是my.controller.package");
        }
        //拿到基础包之后,去得到所有Controller类
        List<Class> clzes = ClassUtil.parseAllController(basePackage);
        //创建一个临时变量用来装类的资源,只在当前静态代码块验证类资源是否唯一
        List<String> classTypes = new ArrayList<>();

        //迭代所有全限定名
        for (Class clz : clzes) {
            //判断是否有自定义注解
            if(clz.isAnnotationPresent(RequestAnnotation.class)) {
                //获取自定义注解
                RequestAnnotation annotation = (RequestAnnotation)clz.getAnnotation(RequestAnnotation.class);
                //获取value值
                String value = annotation.value();
                //先获取map中是否已经存在了此资源名
                if (classTypes.contains(value)) {
                    throw new RuntimeException("控制器类资源"+value+"已经存在,不能重复哦！");
                }
                //将类资源装进临时变量,提供下次循环判断
                classTypes.add(value);
                //获取类的所有方法
                Method[] methods = clz.getMethods();
                //迭代方法
                for (Method method : methods) {
                    //判断方法是否有注解
                    if(method.isAnnotationPresent(RequestAnnotation.class)) {
                        //获取自定义注解
                        RequestAnnotation methodAnnotation = (RequestAnnotation)method.getAnnotation(RequestAnnotation.class);
                        //获取value值
                        String methodValue = methodAnnotation.value();
                        //判断资源名是否重复
                        String type = value+methodValue;
                        ClassAndMethodDTO sysDto = map.get(type);
                        if (sysDto!=null) {
                            throw new RuntimeException("方法资源"+type+"已经存在,不能重复哦！");
                        }
                        //添加类名资源+方法资源--类+方法DTO
                        ClassAndMethodDTO camdto = new ClassAndMethodDTO();
                        camdto.setClz(clz);
                        camdto.setMethod(method);
                        map.put(type, camdto);
                    }
                }
            }
        }
    }
    /**
     * 根据资源文件的url获取资源值
     * @param url
     * @return
     */
    public static Properties parsePackAgeByProperties(String url) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(url));
            return prop;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

