package com.bwei.shoucart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.shoucart.bean.ShopBean;
import com.bwei.shoucart.presenter.Mypresenter;
import com.bwei.shoucart.view.MyView;

public class MainActivity extends AppCompatActivity implements MyView, CompoundButton.OnCheckedChangeListener {

    private Mypresenter mypresenter;
    private ShopAdapter adapter;
    private RecyclerView recyclerView;
    private TextView text_qujieshuan;
    private TextView text_shangpingeshu;
    private TextView text_zongjia;
    private CheckBox checkbox_quanxuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewid);
        text_qujieshuan = (TextView) findViewById(R.id.text_qujieshuan);
        text_shangpingeshu = (TextView) findViewById(R.id.text_shangpingeshu);
        text_zongjia = (TextView) findViewById(R.id.text_zongjia);
        checkbox_quanxuan = (CheckBox) findViewById(R.id.checkbox_quanxuan);
        checkbox_quanxuan.setOnCheckedChangeListener(this);
        mypresenter = new Mypresenter(this);
        mypresenter.showData();
        adapter = new ShopAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new ShopAdapter.UpdateUiListener() {
            @Override
            public void setTotal(String total, String num,boolean allCheck) {

                checkbox_quanxuan.setChecked(allCheck);
                text_shangpingeshu.setText(num);
                text_zongjia.setText(total);
            }
        });
    }

    @Override
    public void success(ShopBean bean) {
            adapter.add(bean);
    }

    @Override
    public void failure(Exception e) {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mypresenter.datach();
    }
    //checkbox全选事件
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        adapter.selectAll(checkbox_quanxuan.isChecked());
    }
}

