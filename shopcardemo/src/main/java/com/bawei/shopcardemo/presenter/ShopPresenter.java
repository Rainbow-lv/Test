package com.bawei.shopcardemo.presenter;

import com.bawei.shopcardemo.core.BasePresenter;
import com.bawei.shopcardemo.core.DataCall;
import com.bawei.shopcardemo.core.IRequest;
import com.bawei.shopcardemo.http.NetWork;

import io.reactivex.Observable;

public class ShopPresenter extends BasePresenter {
    public ShopPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        IRequest iRequest = NetWork.instance().create(IRequest.class);
        return iRequest.shopcar();
    }
}
