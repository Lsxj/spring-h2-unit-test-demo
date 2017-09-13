package com.citi.training.spring.map;

/**
 * Created by sxj on 2017/3/1.
 */
public interface Converter {
    public <S, D> D convert(S sourceInstance, Class<D> destClass);
}
