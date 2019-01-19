package com.bawei.shopcardemo.core;

import com.bawei.shopcardemo.bean.Result;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter {
    public DataCall dataCall;
    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }

    public abstract Observable observable(Object... args);

    public void request(Object...args) {
        observable(args).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                if (result.getCode().equals("0")){
                    dataCall.success(result);
                }else{
                    dataCall.fail(result);
                }
            }
        });
    }
}
