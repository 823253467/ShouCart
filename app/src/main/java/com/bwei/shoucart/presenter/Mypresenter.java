package com.bwei.shoucart.presenter;

import com.bwei.shoucart.bean.ShopBean;
import com.bwei.shoucart.model.IModel;
import com.bwei.shoucart.model.MyModel;
import com.bwei.shoucart.view.MyView;

/**
 * Created by MK on 2017/11/21.
 */
public class Mypresenter {
    MyView view;
    private final MyModel model;

    public Mypresenter(MyView view) {
        this.view = view;
        model = new MyModel();
    }

    public void showData(){
        model.getData(new IModel() {
            @Override
            public void success(ShopBean bean) {
                if (view!=null){

                    view.success(bean);
                }
            }

            @Override
            public void failure(Exception e) {
                if (view!=null){
                    view.failure(e);
                }
            }
        });
    }
    //防止内存遗漏
    public void datach(){
        this.view=null;
    }
}
