package com.niles.tangram.common;

import android.text.TextUtils;
import android.view.View;

import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.viewcreator.ViewHolderCreator;


/**
 * Created by Niles
 * Date 2018/9/24 08:42
 * Email niulinguo@163.com
 */
public class CellModel {

    private final String mType;
    private final Class<? extends View> mViewClass;
    private final Class<? extends BaseCell> mCellClass;
    private final Class<? extends ViewHolderCreator.ViewHolder> mHolderClass;
    private final int mLayoutResId;

    CellModel(String type, Class<? extends View> viewClass, Class<? extends BaseCell> cellClass, Class<? extends ViewHolderCreator.ViewHolder> holderClass, int layoutResId) {
        mType = type;
        mViewClass = viewClass;
        mCellClass = cellClass;
        mHolderClass = holderClass;
        mLayoutResId = layoutResId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getType() {
        return mType;
    }

    public Class<? extends View> getViewClass() {
        return mViewClass;
    }

    public Class<? extends BaseCell> getCellClass() {
        return mCellClass;
    }

    public Class<? extends ViewHolderCreator.ViewHolder> getHolderClass() {
        return mHolderClass;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

    public static final class Builder {
        private String mType;
        private Class<? extends View> mViewClass;
        private Class<? extends BaseCell> mCellClass = BaseCell.class;
        private Class<? extends ViewHolderCreator.ViewHolder> mHolderClass;
        private int mLayoutResId;

        public Builder setType(String type) {
            mType = type;
            return this;
        }

        public Builder setViewClass(Class<? extends View> viewClass) {
            mViewClass = viewClass;
            return this;
        }

        public Builder setCellClass(Class<? extends BaseCell> cellClass) {
            mCellClass = cellClass;
            return this;
        }

        public Builder setHolderClass(Class<? extends ViewHolderCreator.ViewHolder> holderClass) {
            mHolderClass = holderClass;
            return this;
        }

        public Builder setLayoutResId(int layoutResId) {
            mLayoutResId = layoutResId;
            return this;
        }

        public CellModel build() {
            check();
            return new CellModel(mType,
                    mViewClass,
                    mCellClass,
                    mHolderClass,
                    mLayoutResId);
        }

        private void check() {
            if (TextUtils.isEmpty(mType)) {
                throw new RuntimeException("Type Is Null");
            }
            if (mViewClass == null) {
                throw new RuntimeException("View Class Is Null");
            }
            if (mHolderClass != null) {
                if (mCellClass == null) {
                    throw new RuntimeException("Cell Class Is Null");
                }
                if (mLayoutResId == 0) {
                    throw new RuntimeException("Layout Res Id Is 0");
                }
            }
        }
    }
}
