package com.datacvg.dimp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.bean.TableBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description :
 */
public class TableGridAdapter extends BaseAdapter {

    private Context mContext ;
    private List<TableBean> tableBeans = new ArrayList<>();
    private LayoutInflater inflater ;
    private OnItemClickListener listener ;

    public TableGridAdapter(Context mContext, List<TableBean> tableBeans , OnItemClickListener listener) {
        this.mContext = mContext;
        this.tableBeans = tableBeans;
        this.listener = listener ;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return tableBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return tableBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.item_table,null);
            viewHolder = new ViewHolder();
            viewHolder.imgPicture = convertView.findViewById(R.id.img_picture);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            viewHolder.relScreen = convertView.findViewById(R.id.rel_screen) ;
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(LanguageUtils.isZh(mContext)
                ? tableBeans.get(position).getRes_clname() : tableBeans.get(position).getRes_flname());
        String imgUrl = String.format(Constants.BASE_URL + Constants.IMG_TAB_URL,tableBeans.get(position).getRes_imgpath());
        Glide.with(mContext).load(imgUrl)
                .placeholder(R.mipmap.screen_default)
                .error(R.mipmap.screen_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(viewHolder.imgPicture);
        viewHolder.relScreen.setOnClickListener(v -> {
            listener.onItemClick(tableBeans.get(position));
        });
        return convertView;
    }

    public class ViewHolder{
        ImageView imgPicture ;
        TextView tvTitle ;
        RelativeLayout relScreen ;
    }


    public interface OnItemClickListener{
        /**
         * 单项点击监听
         * @param tableBean
         */
        void onItemClick(TableBean tableBean);
    }
}
