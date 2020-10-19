package com.datacvg.sempmobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.bean.DimensionPositionBean;
import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-13
 * @Description : 数字神经指标列表适配器
 */
public class DimensionIndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TEXT_CHART = 1 ;
    private final int LONG_TEXT_CHART = 2 ;
    private final int LINE_CHART = 3 ;
    private final int BAR_CHART = 4 ;
    private final int PIE_CHART = 5 ;
    private final int BULLET_CHART = 6 ;
    private final int DASHBOARD_CHART = 7 ;

    private Context mContext ;
    private LayoutInflater inflater ;
    private List<DimensionPositionBean> dimensionPositionBeans = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null ;
        switch (viewType){
            case TEXT_CHART :
                view = inflater.inflate(R.layout.item_chart_text,parent,false);
                return new TextHolder(view);

            case LONG_TEXT_CHART :
                view = inflater.inflate(R.layout.item_chart_long_text,parent,false);
                return new LongTextHolder(view);

            case LINE_CHART :
                view = inflater.inflate(R.layout.item_chart_line,parent,false);
                return new LineHolder(view);

            case BAR_CHART :
                view = inflater.inflate(R.layout.item_chart_bar,parent,false);
                return new BarHolder(view);

            case PIE_CHART :
                view = inflater.inflate(R.layout.item_chart_pie,parent,false);
                return new PieHolder(view);

            case BULLET_CHART :
                view = inflater.inflate(R.layout.item_chart_bullet,parent,false);
                return new BulletHolder(view);

            case DASHBOARD_CHART :
                view = inflater.inflate(R.layout.item_chart_dashboard,parent,false);
                return new DashBoardHolder(view);

            default:
                view = inflater.inflate(R.layout.item_chart_none,parent,false);
                return new NoneHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dimensionPositionBeans.size();
    }

    public class TextHolder extends RecyclerView.ViewHolder{
        public TextHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class LongTextHolder extends RecyclerView.ViewHolder{
        public LongTextHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }

    public class LineHolder extends RecyclerView.ViewHolder{
        public LineHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class BarHolder extends RecyclerView.ViewHolder{
        public BarHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class PieHolder extends RecyclerView.ViewHolder{
        public PieHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class BulletHolder extends RecyclerView.ViewHolder{
        public BulletHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class DashBoardHolder extends RecyclerView.ViewHolder{
        public DashBoardHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class NoneHolder extends RecyclerView.ViewHolder{
        public NoneHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
