package com.bawei.shopcardemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.shopcardemo.MainActivity;
import com.bawei.shopcardemo.R;
import com.bawei.shopcardemo.app.LVApplication;
import com.bawei.shopcardemo.bean.Goods;
import com.bawei.shopcardemo.bean.Shop;
import com.bawei.shopcardemo.bean.ShopCarBean;
import com.bawei.shopcardemo.view.AddSubLayout;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends BaseExpandableListAdapter {

    List<Shop> mlist = new ArrayList<>();
    private TotalPriceListener totalPriceListener;

    public ShopAdapter(){
    }
    public void setTotalPriceListener(TotalPriceListener totalPriceListener) {
        this.totalPriceListener = totalPriceListener;
    }

    @Override
    public int getGroupCount() {
        return mlist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mlist.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mlist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mlist.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //一级列表
    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder gh = new GroupHolder();
        if (convertView == null){
            convertView = View.inflate(parent.getContext(), R.layout.group_item,null);
            gh.checkBox = convertView.findViewById(R.id.checkBox);
            convertView.setTag(gh);
        }else {
            gh = (GroupHolder) convertView.getTag();
        }
        //绑定数据
        final Shop shop = mlist.get(groupPosition);
        gh.checkBox.setText(shop.getSellerName());
        gh.checkBox.setChecked(shop.isCheck());//设置商铺选中状态
        gh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //数据更新
                shop.setCheck(isChecked);
                //得到商品信息
                List<Goods> goodsList = mlist.get(groupPosition).getList();
                for (int i=0;i<goodsList.size();i++){//商品信息循环赋值
                    goodsList.get(i).setSelected(isChecked?1:0);//商铺选中则商品必须选中
                }
                notifyDataSetChanged();
                //计算价格
                jisuanprice();
            }
        });
        return convertView;
    }



    //二级列表
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder ch = new ChildHolder();
        if (convertView == null){
            convertView = View.inflate(parent.getContext(),R.layout.child_list,null);
            ch.check = convertView.findViewById(R.id.car_check);
            ch.image = convertView.findViewById(R.id.image);
            ch.text = convertView.findViewById(R.id.text);
            ch.price = convertView.findViewById(R.id.text_price);
            ch.addsub = convertView.findViewById(R.id.add_sub_layout);
            convertView.setTag(ch);
        }else {
            ch = (ChildHolder) convertView.getTag();
        }
        //绑定数据
        final Goods goods = mlist.get(groupPosition).getList().get(childPosition);
        ch.text.setText(goods.getTitle());
        ch.price.setText("单价："+goods.getPrice());
        //点击选中计算价格
        ch.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                goods.setSelected(isChecked?1:0);
                jisuanprice();//计算价格
            }
        });
        if (goods.getSelected()==0){
            ch.check.setChecked(false);
        }else{
            ch.check.setChecked(true);
        }
        String imageurl = "https" + goods.getImages().split("https")[1];
        Log.i("dt", "imageUrl: " + imageurl);
        imageurl = imageurl.substring(0, imageurl.lastIndexOf(".jpg") + ".jpg".length());
        Glide.with(LVApplication.getInstance()).load(imageurl).into(ch.image);//加载图片

        ch.addsub.setCount(goods.getNum());//设置商品数量
        ch.addsub.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                goods.setNum(count);
                jisuanprice();//计算价格
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void checkAll(boolean isChecked) {
        for (int i = 0; i < mlist.size(); i++) {//循环的商家
            Shop shop = mlist.get(i);
            shop.setCheck(isChecked);
            for (int j = 0; j < shop.getList().size(); j++) {
                Goods goods = shop.getList().get(j);
                goods.setSelected(isChecked?1:0);
            }
        }
        notifyDataSetChanged();
        jisuanprice();//计算价格
    }

    class GroupHolder{
        public CheckBox checkBox;
    }
    class ChildHolder{
        public CheckBox check;
        public TextView text;
        public TextView price;
        public ImageView image;
        public AddSubLayout addsub;
    }
    public interface TotalPriceListener {
        void totalPrice(double totalPrice);
    }
    public void addAll(List<Shop> data) {
        if (data != null)
            mlist.addAll(data);
    }

    private void jisuanprice() {
        double totalprice=0;
        for (int i=0;i<mlist.size();i++){
            Shop shop = mlist.get(i);
            for (int j = 0; j < shop.getList().size(); j++) {
                Goods goods = shop.getList().get(j);
                if (goods.getSelected() == 1){
                    //如果选中
                    totalprice = totalprice + goods.getNum()*goods.getPrice();
                }
            }
            if (totalPriceListener != null){
                totalPriceListener.totalPrice(totalprice);
            }
        }
    }

}
