package com.bwei.shoucart.model;

import com.bwei.shoucart.bean.ShopBean;

/**
 * Created by MK on 2017/11/21.
 */
public interface IModel {
    public void success(ShopBean bean);
    public void failure(Exception e);
}
