package com.game.dispatcher;

import org.springframework.stereotype.Component;

/**
 * 验证字符串非空
 */
@Component
public class StringUtil {
	public static boolean isNotNullOrEmpty(String str) {
		return str!=null&&!"".equals(str.trim());
	}
}