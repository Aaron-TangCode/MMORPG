package com.test.load;


public abstract class AbstractConfigLoader <T, U> implements IConfigLoader<T>{

    protected IConfigProvider<U> provider;

    protected AbstractConfigLoader(IConfigProvider<U> provider) {
        this.provider = provider;
    }

    @Override
    public T load() throws Exception {
        return load(getProvider().provide());
    }
    public abstract T load(U loaderSource) throws Exception;
    
    protected IConfigProvider<U> getProvider() {
        return this.provider;
    }
}