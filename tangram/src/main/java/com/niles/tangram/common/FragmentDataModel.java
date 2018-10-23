package com.niles.tangram.common;

import android.os.Bundle;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Niles
 * Date 2018/9/24 11:06
 * Email niulinguo@163.com
 */
public class FragmentDataModel {

    private final String mJsonPageData;

    private FragmentDataModel(String jsonPageData) {
        mJsonPageData = jsonPageData;
    }

    public String getJsonPageData() {
        return mJsonPageData;
    }

    public Bundle wrapBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("mJsonPageData", mJsonPageData);
        return bundle;
    }

    public static final class Builder {
        private String mJsonPageData;

        public Builder() {
        }

        public Builder(Bundle bundle) {
            if (bundle != null) {
                mJsonPageData = bundle.getString("mJsonPageData");
            }
        }

        public Builder setJsonPageData(String jsonPageData) {
            mJsonPageData = jsonPageData;
            return this;
        }

        public FragmentDataModel build() {
            check();
            return new FragmentDataModel(mJsonPageData);
        }

        private void check() {
            if (TextUtils.isEmpty(mJsonPageData)) {
                throw new RuntimeException("Json Page Data Is Null");
            }
            try {
                new JSONArray(mJsonPageData);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
