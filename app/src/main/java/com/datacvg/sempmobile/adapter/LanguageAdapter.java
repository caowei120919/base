package com.datacvg.sempmobile.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.bean.LanguageBean;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-28
 * @Description : 语言设置
 */
public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
   private Context mContext ;
   private List<LanguageBean> languageBeans = new ArrayList<>();
   private LayoutInflater inflater ;
   private String language ;
   private ChangeLanguageClick changeLanguageClick ;

   public LanguageAdapter(Context mContext, List<LanguageBean> languageBeans
           , ChangeLanguageClick click) {
        this.mContext = mContext;
        this.languageBeans = languageBeans ;
        inflater = LayoutInflater.from(mContext);
        language = PreferencesHelper.get(Constants.APP_LANGUAGE,Constants.LANGUAGE_AUTO);
        this.changeLanguageClick = click ;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_language,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTitle.setText(languageBeans.get(position).getTitle());
        holder.imgSelected.setVisibility(languageBeans.get(position).getValue().equals(language)
                ? View.VISIBLE :View.GONE);
        holder.itemView.setOnClickListener(view ->  {
                changeLanguageClick.changeLanguageClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return languageBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_selected)
        ImageView imgSelected ;
        @BindView(R.id.tv_title)
        TextView tvTitle ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface ChangeLanguageClick{
       void changeLanguageClick(int position);
    }
}
