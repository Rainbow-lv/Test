package com.bawei.shopcardemo.core;

import com.bawei.shopcardemo.bean.Result;

public interface DataCall<T> {
    void success(T result);
    void fail(Result result);
}
