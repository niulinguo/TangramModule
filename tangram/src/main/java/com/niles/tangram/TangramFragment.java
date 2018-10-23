package com.niles.tangram;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niles.tangram.common.CellInfo;
import com.niles.tangram.common.CommonUtils;
import com.niles.tangram.common.FragmentDataModel;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Niles
 * Date 2018/9/23 20:07
 * Email niulinguo@163.com
 */
public class TangramFragment extends Fragment {

    private TangramEngine mTangramEngine;
    private FragmentDataModel mDataModel;

    public static TangramFragment create(FragmentDataModel fragmentDataModel) {
        TangramFragment fragment = new TangramFragment();
        fragment.setArguments(fragmentDataModel.wrapBundle());
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataModel = new FragmentDataModel.Builder(getArguments()).build();

        TangramBuilder.InnerBuilder builder = TangramBuilder.newInnerBuilder(CommonUtils.requireNonNull(getContext()));

        List<CellInfo> cellInfoList = TangramManager.getConfig().getCellInfoList();
        for (CellInfo cellInfo : cellInfoList) {
            String type = cellInfo.getType();
            Class<? extends BaseCell> cellClass = cellInfo.getCellClass();
            Class<? extends ViewHolderCreator.ViewHolder> holderClass = cellInfo.getHolderClass();
            Class<? extends View> viewClass = cellInfo.getViewClass();
            if (holderClass != null) {
                builder.registerCell(type, cellClass, new ViewHolderCreator<>(cellInfo.getLayoutResId(), holderClass, viewClass));
            } else if (cellClass != null) {
                builder.registerCell(type, cellClass, viewClass);
            } else {
                builder.registerCell(type, viewClass);
            }
        }

        mTangramEngine = builder.build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = createView(inflater, container, savedInstanceState);
        initRecyclerView((RecyclerView) rootView.findViewById(R.id.rv_list));
        return rootView;
    }

    private View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = inflater.getContext();
        RecyclerView recyclerView = new RecyclerView(context);
        recyclerView.setId(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        return recyclerView;
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        mTangramEngine.bindView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTangramEngine.onScrolled();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mTangramEngine.setData(new JSONArray(mDataModel.getJsonPageData()));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mTangramEngine.unbindView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mTangramEngine.destroy();
    }
}
