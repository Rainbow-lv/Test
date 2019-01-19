package com.bawei.shopcardemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shopcardemo.R;

public class AddSubLayout  extends LinearLayout implements View.OnClickListener{

    private Button btn_add;
    private Button btn_sub;
    private TextView text_num;
    private AddSubListener addSubListener;

    public AddSubLayout(Context context) {
        super(context);
        init();
    }

    public AddSubLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddSubLayout(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.add_sub_layout, this);
        //找控件
        btn_add = view.findViewById(R.id.btn_add);
        btn_sub = view.findViewById(R.id.btn_sub);
        text_num = view.findViewById(R.id.text_number);
        //设置点击监听
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int width = r-l;//getWidth();
        int height = b-t;//getHeight();

    }
    @Override
    public void onClick(View v) {
        int num = Integer.parseInt(text_num.getText().toString());
        switch (v.getId()){
            case R.id.btn_add:
                num++;
                text_num.setText(num+"");
                break;
            case R.id.btn_sub:
                if (num==0){
                    Toast.makeText(getContext(),"数量不能小于0",Toast.LENGTH_LONG).show();
                    return;
                }
                num--;
                text_num.setText(num+"");
                break;
        }
        if (addSubListener!=null){
            addSubListener.addSub(num);
        }
    }
    public void setCount(int count) {
        text_num.setText(count+"");
    }
    public void setAddSubListener(AddSubListener addSubListener) {
        this.addSubListener = addSubListener;
    }

    public interface AddSubListener{
        void addSub(int count);
    }
}
