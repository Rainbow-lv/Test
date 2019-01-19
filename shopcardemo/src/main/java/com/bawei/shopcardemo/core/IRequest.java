package com.bawei.shopcardemo.core;

import com.bawei.shopcardemo.bean.Goods;
import com.bawei.shopcardemo.bean.Result;
import com.bawei.shopcardemo.bean.Shop;
import com.bawei.shopcardemo.bean.ShopCarBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IRequest {

     /**
      * 查询购物车
      * @return
      */
     @GET("product/getCarts?uid=71")
     Observable<Result<List<Shop>>> shopcar();

}
