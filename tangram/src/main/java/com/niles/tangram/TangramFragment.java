package com.niles.tangram;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.niles.tangram.common.CellModel;
import com.niles.tangram.common.FragmentDataModel;
import com.niles.tangram.common.CommonUtils;
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

    private static final String ARG_JSON_PAGE_DATA = "ARG_JSON_PAGE_DATA";
    private TangramEngine mTangramEngine;
    private String mJsonPageData;

    public static TangramFragment create(FragmentDataModel fragmentDataModel) {
        TangramFragment fragment = new TangramFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_JSON_PAGE_DATA, fragmentDataModel.getJsonPageData());
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        assert arguments != null;
        mJsonPageData = arguments.getString(ARG_JSON_PAGE_DATA);

        TangramBuilder.InnerBuilder builder = TangramBuilder.newInnerBuilder(CommonUtils.requireNonNull(getContext()));

        List<CellModel> cellModelList = TangramManager.getConfig().getCellModelList();
        for (CellModel cellModel : cellModelList) {
            String type = cellModel.getType();
            Class<? extends BaseCell> cellClass = cellModel.getCellClass();
            Class<? extends ViewHolderCreator.ViewHolder> holderClass = cellModel.getHolderClass();
            Class<? extends View> viewClass = cellModel.getViewClass();
            if (holderClass != null) {
                builder.registerCell(type, cellClass, new ViewHolderCreator<>(cellModel.getLayoutResId(), holderClass, viewClass));
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
        return inflater.inflate(R.layout.fragment_recycler_view_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_list);
        mTangramEngine.bindView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTangramEngine.onScrolled();
            }
        });

        try {
            mTangramEngine.setData(new JSONArray(mJsonPageData));
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
