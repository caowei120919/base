package com.datacvg.dimp.adapter;

import android.content.Context;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.bean.ActionPlanIndexForActionBean;
import com.datacvg.dimp.event.AddDepartmentEvent;
import com.datacvg.dimp.event.AddOrRemoveIndexEvent;
import com.datacvg.dimp.event.AddOrRemoveIndexForTaskEvent;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-11-02
 * @Description :
 */
public class TaskIndexAdapter extends RecyclerView.Adapter<TaskIndexAdapter.ViewHolder> {
    private final int PARENT_VIEW = 0 ;
    private final int CHILD_VIEW = 1 ;
    private List<ActionPlanIndexForActionBean> totalDepartmentInAtBeans = new ArrayList<>() ;
    private List<ActionPlanIndexForActionBean> showDepartmentInAtBeans = new ArrayList<>() ;
    private List<ActionPlanIndexForActionBean> firstLevelDepartmentInAtBeans = new ArrayList<>() ;
    private List<ActionPlanIndexForActionBean> removeDepartmentInAtBeans = new ArrayList<>();
    private SparseIntArray addedChildNodeIds = new SparseIntArray();
    private Context mContext ;
    private List<String> selectDepartments = new ArrayList<>() ;
    private Boolean isSearch = false ;

    public void setSearch(Boolean search) {
        isSearch = search;
    }

    public TaskIndexAdapter(Context mContext, List<ActionPlanIndexForActionBean> departmentInAtBeans) {
        this.mContext = mContext ;
        setDepartments(departmentInAtBeans);
    }


    public void setSelectDepartments(List<String> selectDepartments) {
        this.selectDepartments = selectDepartments;
        if (selectDepartments == null){
            this.selectDepartments = new ArrayList<>() ;
        }
        notifyDataSetChanged();
    }

    public void setDepartments(List<ActionPlanIndexForActionBean> departmentInAtBeans) {
        totalDepartmentInAtBeans = departmentInAtBeans ;
        reset();
        super.notifyDataSetChanged();
    }

    private void reset() {
        showDepartmentInAtBeans.clear();
        initNodes();
        addedChildNodeIds.clear();
        showDepartmentInAtBeans.addAll(firstLevelDepartmentInAtBeans);
    }

    private void initNodes() {
        firstLevelDepartmentInAtBeans.clear();
        //先循环一次，获取最小的level
        int level = -1;
        for (ActionPlanIndexForActionBean node : totalDepartmentInAtBeans) {
            if (level == -1 || level > node.level) {
                level = node.level;
            }
        }
        for (ActionPlanIndexForActionBean node : totalDepartmentInAtBeans) {
            //过滤出最外层
            if (node.level == level) {
                firstLevelDepartmentInAtBeans.add(node);
            }
            //清空之前添加的
            if (node.hasChild()) {
                node.childNodes.clear();
            }
            //给节点添加子节点并排序
            for (ActionPlanIndexForActionBean t : totalDepartmentInAtBeans) {
                if (node.id.equals(t.id)  && node != t) {
                    continue;
                }
                if (node.id .equals(t.pId) && node.level != t.level) {
                    node.addChild(t);
                }
            }
            if (node.hasChild()) {
                Collections.sort(node.childNodes);
            }
        }
        Collections.sort(firstLevelDepartmentInAtBeans);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View parentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_department_parent,parent,false);
        return new ViewHolder(parentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActionPlanIndexForActionBean node = showDepartmentInAtBeans.get(position);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.cbExpend.getLayoutParams();
        params.leftMargin = isSearch ? 0 : (int) ((showDepartmentInAtBeans.get(position).level + 0.5) * dip2px(10));
        holder.cbExpend.setVisibility(showDepartmentInAtBeans.get(position).hasChild() ? View.VISIBLE : View.INVISIBLE);
        holder.cbExpend.setOnCheckedChangeListener(null);
        holder.imgDepartment.setVisibility(View.GONE);
        holder.cbExpend.setChecked(node.hasChild() && showDepartmentInAtBeans.containsAll(node.childNodes));
        holder.cbExpend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (node.hasChild()) {
                        showDepartmentInAtBeans.addAll(position + 1 ,node.childNodes);
                    }
                }else{
                    if(node.hasChild()){
                        removeDepartmentInAtBeans.clear();
                        removeChild(node.childNodes);
                        showDepartmentInAtBeans.removeAll(removeDepartmentInAtBeans);
                    }
                }
                notifyDataSetChanged();
            }
        });
        holder.cbDepartment.setSelected(selectDepartments != null && !selectDepartments.isEmpty() && selectDepartments.contains(showDepartmentInAtBeans.get(position).id));
        holder.cbDepartment.setOnClickListener(v -> {
            if (selectDepartments == null || selectDepartments.isEmpty() || !selectDepartments.contains(showDepartmentInAtBeans.get(position).id)){
                selectDepartments.add(showDepartmentInAtBeans.get(position).id);
            }else{
                selectDepartments.remove(showDepartmentInAtBeans.get(position).id);
            }
            notifyDataSetChanged();
            EventBus.getDefault().post(new AddOrRemoveIndexForTaskEvent(showDepartmentInAtBeans.get(position).getBean()));
        });
        holder.tvDepartmentName.setText(showDepartmentInAtBeans.get(position).getBean().getName());
    }

    /**
     * 递归删除子元素
     * @param childNodes
     */
    private void removeChild(List<ActionPlanIndexForActionBean> childNodes) {
        for (ActionPlanIndexForActionBean bean :childNodes) {
            if(bean.hasChild()){
                removeChild(bean.childNodes);
            }
            removeDepartmentInAtBeans.add(bean);
        }
    }

    private int dip2px(int dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return showDepartmentInAtBeans.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(showDepartmentInAtBeans.get(position).isExpand){
            return PARENT_VIEW ;
        }
        return CHILD_VIEW;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.cb_expend)
        CheckBox cbExpend ;

        @BindView(R.id.cb_department)
        ImageView cbDepartment ;

        @BindView(R.id.tv_departmentName)
        TextView tvDepartmentName ;

        @BindView(R.id.img_department)
        ImageView imgDepartment ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
