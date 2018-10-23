package com.niles.tangram;

import com.niles.tangram.cell.CreateCells;
import com.niles.tangram.common.CellInfo;
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
    private final List<CellInfo> mCellInfoList;
    private final boolean mPrintLog;

    private Config(LoadImageListener loadImageListener,
                   List<CellInfo> cellInfoList,
                   boolean printLog) {
        mLoadImageListener = loadImageListener;
        mCellInfoList = cellInfoList;
        mPrintLog = printLog;
    }

    public LoadImageListener getLoadImageListener() {
        return mLoadImageListener;
    }

    public List<CellInfo> getCellInfoList() {
        return mCellInfoList;
    }

    public boolean isPrintLog() {
        return mPrintLog;
    }

    public static final class Builder {
        private LoadImageListener mLoadImageListener;
        private List<CellInfo> mCellInfoList = new ArrayList<>();
        private boolean mPrintLog;

        public Builder() {
            mCellInfoList.addAll(CreateCells.create());
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

        public Builder addCellModel(CellInfo cellInfo) {
            mCellInfoList.add(cellInfo);
            return this;
        }

        public Config build() {
            check();
            return new Config(mLoadImageListener,
                    mCellInfoList,
                    mPrintLog);
        }

        private void check() {
            if (mLoadImageListener == null) {
                throw new RuntimeException("LoadImageListener Is Null");
            }
        }
    }
}
