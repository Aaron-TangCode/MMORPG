package com.test.load;

/**
 * 配置加载器
 * @param <T>
 */
public interface IConfigLoader<T> {
    
    /**
     * load the config typed by T
     *
     * @return
     * @throws Exception
     */
    public T load() throws Exception;
}