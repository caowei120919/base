package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ModuleAdapter;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.dimp.baseandroid.greendao.controller.DbModuleInfoController;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.event.RebuildTableEvent;
import com.datacvg.dimp.presenter.ModuleSettingPresenter;
import com.datacvg.dimp.view.ModuleSettingView;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-28
 * @Description :模块设置 */
public class ModuleSettingActivity extends BaseActivity<ModuleSettingView, ModuleSettingPresenter>
        implements ModuleSettingView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.recycler_module)
    RecyclerView recyclerModule ;

    private List<ModuleInfo> moduleInfos = new ArrayList<>();
    private ModuleAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_module;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvTitle.setText(resources.getString(R.string.module_settings));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        moduleInfos = DbModuleInfoController.getInstance(mContext).getPermissionModuleList();
        for (ModuleInfo bean:moduleInfos) {
            PLog.e(bean.getModule_name());
        }
        adapter = new ModuleAdapter(mContext,moduleInfos);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        recyclerModule.setLayoutManager(manager);
        recyclerModule.setAdapter(adapter);
        //创建item helper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        //绑定到recyclerView上面
        itemTouchHelper.attachToRecyclerView(recyclerModule);
    }

    @OnClick({R.id.img_left})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                /**
                 * 重置模块序列
                 */
                for (int i = 0 ; i < moduleInfos.size() ; i++) {
                    moduleInfos.get(i).setModule_id(i);
                }
                DbModuleInfoController.getInstance(mContext).updateModuleInfoAll(moduleInfos);
                EventBus.getDefault().post(new RebuildTableEvent());
                finish();
                break;
        }
    }

    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView
                , @NonNull RecyclerView.ViewHolder viewHolder) {
            int swipeFlag = 0 ;
            int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
            return makeMovementFlags(dragFlag,swipeFlag);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView
                , @NonNull RecyclerView.ViewHolder viewHolder
                , @NonNull RecyclerView.ViewHolder target) {
            adapter.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            Collections.swap(moduleInfos,viewHolder.getAdapterPosition(),target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
    };
}
