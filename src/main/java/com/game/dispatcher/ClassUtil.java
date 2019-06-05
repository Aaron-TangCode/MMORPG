package com.game.dispatcher;

import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是根据一个公共父级包得到所有子级包下所有控制器类的全限定名的工具类
 *功能：
 *1.获取一个父级包下所有类的全限定名封装的Class对象
 */
@SuppressWarnings("rawtypes")//去掉泛型警告
@Component
public class ClassUtil {
	public static List<Class> parseAllController(String basePackage){
		List<Class> clzes = new ArrayList<>();
		String path = basePackage.replace(".", "/");
		//获取此包在磁盘的位置
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		File file = new File(url.getPath());
		getClass(file,clzes,basePackage);
		return clzes;
	}
 
	private static void getClass(File file, List<Class> clzes,String packAgeName) {
		//文件存在
		if (file.exists()) {
			//是文件
			if (file.isFile()) {
				try {
					String className = null;
					if (packAgeName.contains(".class")) {
						className = packAgeName.replace(".class", "");
					}else {
						className  = (packAgeName+"."+file.getName()).replace(".class", "");
					}
					clzes.add(Class.forName(className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			//是目录
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File f : files) {
					String packAge=packAgeName+"."+f.getName();
					getClass(f, clzes, packAge);
				}
			}
		}
	}
}