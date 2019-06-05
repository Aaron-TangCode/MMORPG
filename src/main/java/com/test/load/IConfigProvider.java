package com.test.load;

/**
 * 配置加载器
 * @param <T>
 */
public interface IConfigProvider<T> {
    /**
     * provide a config source used for loading config
     * @return
     * @throws Exception
     */
    public T provide() throws Exception;
}
