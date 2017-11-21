package com.bwei.shoucart;


        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;


        import com.bumptech.glide.Glide;
        import com.bwei.shoucart.bean.ShopBean;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;


/**
 * Created by muhanxi on 17/11/21.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.IViewHolder> {

    private Context context;

    private List<ShopBean.DataBean.ListBean> list;
    // 存放 商家的id 和 商家名称
    private Map<String,String> map = new HashMap<>();

    public ShopAdapter(Context context) {
        this.context = context;
    }




    /**
     * 添加数据 并更新显示
     * @param bean
     */
    public void add(ShopBean bean) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }

        // 遍历商家
        for (ShopBean.DataBean shop : bean.getData()) {
            map.put(shop.getSellerid(),shop.getSellerName());
            // 遍历商品
            for (int i = 0; i < shop.getList().size(); i++) {
                this.list.add(shop.getList().get(i));
            }
        }



        setFirst(this.list);

        notifyDataSetChanged();
    }

    /**
     * 设置数据源， 控制显示商家
     * @param list
     */
    private void setFirst(List<ShopBean.DataBean.ListBean> list){

        if(list.size() > 0){
            list.get(0).setIsFirst(1);
            for(int i=1;i<list.size();i++){
                if(list.get(i).getSellerid() == list.get(i-1).getSellerid()){
                    list.get(i).setIsFirst(2);
                }else{
                    list.get(i).setIsFirst(1);
                }
            }

        }

    }

    @Override
    public ShopAdapter.IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = View.inflate(context, R.layout.adapter_layout, null);
        return new IViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShopAdapter.IViewHolder holder, final int position) {


        // 显示商品图片

        if(list.get(position).getIsFirst() == 1){
            //显示商家
            holder.shop_checkbox.setVisibility(View.VISIBLE);
            holder.tv_item_shopcart_shopname.setVisibility(View.VISIBLE);
            holder.shop_checkbox.setChecked(list.get(position).isShopSelected());

//            显示商家的名称
//            list.get(position).getSellerid() 取到商家的id
//            map.get（）取到 商家的名称
            holder.tv_item_shopcart_shopname.setText(map.get(String.valueOf(list.get(position).getSellerid())));
        } else {
            holder.shop_checkbox.setVisibility(View.GONE);
            holder.tv_item_shopcart_shopname.setVisibility(View.GONE);
        }


        //控制 商品的  checkbox
        holder.item_checkbox.setChecked(list.get(position).isItemSelected());





        String[] url = list.get(position).getImages().split("\\|");
        //ImageLoader.getInstance().displayImage(url[0],holder.item_pic);
        Glide.with(context)
                .load(url[0])
                .into(holder.item_pic);

        holder.item_name.setText(list.get(position).getTitle());
        holder.item_price.setText(list.get(position).getPrice()+"");


        holder.costomViewid.setEditText(list.get(position).getNum());


        // 商家的checkbox
        holder.shop_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                list.get(position).setShopSelected(holder.shop_checkbox.isChecked());

                for(int i=0;i<list.size();i++){
                    if(list.get(position).getSellerid() == list.get(i).getSellerid()){
                        list.get(i).setItemSelected(holder.shop_checkbox.isChecked());
                    }
                }

                notifyDataSetChanged();
                sum(list);

            }
        });

        // 商品的checkbox
        holder.item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                list.get(position).setItemSelected(holder.item_checkbox.isChecked());


                for(int i=0;i<list.size();i++){
                    for (int j=0;j<list.size();j++){
                        if(list.get(i).getSellerid() == list.get(j).getSellerid() && !list.get(j).isItemSelected()){
                            list.get(i).setShopSelected(false);
                            break;
                        }else {
                            list.get(i).setShopSelected(true);
                        }
                    }
                }

                notifyDataSetChanged();
                sum(list);

            }
        });


        holder.item_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(position);

                setFirst(list);

                notifyDataSetChanged();
                sum(list);

            }
        });




        //加减号
        holder.costomViewid.setListener(new CostomView.ClickListener() {
            @Override
            public void click(int count) {
                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);
            }
        });





    }




    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    /**
     * 计算总价
     * @param list
     */
    private void sum(List<ShopBean.DataBean.ListBean> list){

        int totalNum = 0 ;
        float totalMoney =  0.0f;

        boolean allCheck =true;
        for(int i=0;i<list.size();i++){
            if(list.get(i).isItemSelected()){
                totalNum += list.get(i).getNum() ;
                totalMoney += list.get(i).getNum() * list.get(i).getPrice();
            }else {
                allCheck = false;
            }
        }

        listener.setTotal(totalMoney+"",totalNum+"",allCheck);
    }


    public void selectAll(boolean check){

        for(int i=0;i<list.size();i++){
            list.get(i).setShopSelected(check);
            list.get(i).setItemSelected(check);

        }
        notifyDataSetChanged();

        sum(list);

    }


    static class IViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox shop_checkbox;
        private final TextView tv_item_shopcart_shopname;
        private final ImageView item_del;
        private final CheckBox item_checkbox;
        private final CostomView costomViewid;
        private final TextView tv_item_shopcart_cloth_size;
        private final TextView item_name;
        private final TextView item_price;
        private final ImageView item_pic;

        IViewHolder(View view) {
            super(view);
            shop_checkbox = (CheckBox) view.findViewById(R.id.shop_checkbox);
            tv_item_shopcart_shopname = (TextView) view.findViewById(R.id.tv_item_shopcart_shopname);
            item_checkbox = (CheckBox) view.findViewById(R.id.item_checkbox);
            item_del = (ImageView) view.findViewById(R.id.item_del);
            costomViewid = (CostomView) view.findViewById(R.id.costomViewid);
            tv_item_shopcart_cloth_size = (TextView) view.findViewById(R.id.tv_item_shopcart_cloth_size);
            item_name = (TextView) view.findViewById(R.id.item_name);
            item_price = (TextView) view.findViewById(R.id.item_price);
            item_pic = (ImageView) view.findViewById(R.id.item_pic);
        }
    }


    public UpdateUiListener listener;
    public void setListener(UpdateUiListener listener){
        this.listener = listener;
    }
    interface UpdateUiListener {
        public void setTotal(String total,String num,boolean allCheck);
    }


}

