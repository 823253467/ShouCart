package com.bwei.shoucart.model;

import com.bwei.shoucart.bean.ShopBean;
import com.bwei.shoucart.okhttp.AbstractUiCallBack;
import com.bwei.shoucart.okhttp.OkhttpUtils;

/**
 * Created by MK on 2017/11/21.
 */
public class MyModel {
    public void getData(final IModel iModel){
        OkhttpUtils.getInstance().asy(null,"http://120.27.23.105/product/getCarts?uid=100", new AbstractUiCallBack<ShopBean>() {
            @Override
            public void success(ShopBean bean) {
                iModel.success(bean);
            }

            @Override
            public void failure(Exception e) {
                iModel.failure(e);
            }
        });
    }
}
