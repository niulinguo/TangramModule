package com.niles.tangram;

import com.niles.tangram.common.CellModel;
import com.niles.tangram.cell.CreateCells;
import com.niles.tangram.common.LoadImageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niles
 * Date 2018/9/23 20:00
 * Email niulinguo@163.com
 */
public class Config {

    private final LoadImageListener mLoadImageListener;
    private final List<CellModel> mCellModelList;
    private final boolean mPrintLog;

    Config(LoadImageListener loadImageListener,
           List<CellModel> cellModelList,
           boolean printLog) {
        mLoadImageListener = loadImageListener;
        mCellModelList = cellModelList;
        mPrintLog = printLog;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public LoadImageListener getLoadImageListener() {
        return mLoadImageListener;
    }

    public List<CellModel> getCellModelList() {
        return mCellModelList;
    }

    public boolean isPrintLog() {
        return mPrintLog;
    }

    public static final class Builder {
        private LoadImageListener mLoadImageListener;
        private List<CellModel> mCellModelList = new ArrayList<>();
        private boolean mPrintLog;

        Builder() {
            mCellModelList.addAll(CreateCells.create());
        }

        public LoadImageListener getLoadImageListener() {
            return mLoadImageListener;
        }

        public Builder setLoadImageListener(LoadImageListener loadImageListener) {
            mLoadImageListener = loadImageListener;
            return this;
        }

        public Builder setPrintLog(boolean printLog) {
            mPrintLog = printLog;
            return this;
        }

        public Builder addCellModel(CellModel cellModel) {
            mCellModelList.add(cellModel);
            return this;
        }

        public Config build() {
            check();
            return new Config(mLoadImageListener,
                    mCellModelList,
                    mPrintLog);
        }

        private void check() {
            if (mLoadImageListener == null) {
                throw new RuntimeException("LoadImageListener Is Null");
            }
        }
    }
}
