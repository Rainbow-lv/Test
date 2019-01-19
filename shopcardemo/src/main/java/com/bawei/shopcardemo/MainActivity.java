package com.bawei.shopcardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shopcardemo.adapter.ShopAdapter;
import com.bawei.shopcardemo.bean.Goods;
import com.bawei.shopcardemo.bean.Result;
import com.bawei.shopcardemo.bean.Shop;
import com.bawei.shopcardemo.bean.User;
import com.bawei.shopcardemo.core.DataCall;
import com.bawei.shopcardemo.presenter.ShopPresenter;
import com.lll.greendaodemo.DaoMaster;
import com.lll.greendaodemo.DaoSession;
import com.lll.greendaodemo.UserDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ShopAdapter.TotalPriceListener {

    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.checkbox01)
    CheckBox checkbox01;
    @BindView(R.id.sumss)
    TextView sumss;
    @BindView(R.id.btn_jiesuan)
    Button btnJiesuan;
    private ShopAdapter shopAdapte;
    private ShopPresenter shopPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        checkbox01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                shopAdapte.checkAll(isChecked);
            }
        });

        //创建适配器
        shopAdapte = new ShopAdapter();
        //设置适配器
        expandableListView.setAdapter(shopAdapte);
        shopPresenter = new ShopPresenter(new ShopCall());
        shopPresenter.request();
        shopAdapte.setTotalPriceListener(this);//设置总价回调器
        //去掉小箭头
        expandableListView.setGroupIndicator(null);
        //让其group不能被点击
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return true;
            }
        });
    }

    private class ShopCall implements DataCall<Result> {
        @Override
        public void success(Result result) {
            List<Shop> data = (List<Shop>) result.getData();
            shopAdapte.addAll(data);
            //遍历所有group,将所有项设置成默认展开
            int groupCount = data.size();
            for (int i = 0; i < groupCount; i++) {
                expandableListView.expandGroup(i);
            }
            shopAdapte.notifyDataSetChanged();
        }

        @Override
        public void fail(Result result) {
            Toast.makeText(getBaseContext(), "失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void totalPrice(double totalPrice) {
        sumss.setText(String.valueOf(totalPrice));
    }
}
