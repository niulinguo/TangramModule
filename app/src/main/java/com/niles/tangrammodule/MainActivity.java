package com.niles.tangrammodule;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.niles.tangram.TangramFragment;
import com.niles.tangram.common.CommonUtils;
import com.niles.tangram.common.FragmentDataModel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static byte[] getAssertsFile(Context context, String fileName) {
        InputStream inputStream = null;
        AssetManager assetManager = context.getAssets();
        try {
            inputStream = assetManager.open(fileName);
            if (inputStream == null) {
                return null;
            }

            BufferedInputStream bis = null;
            int length;
            try {
                bis = new BufferedInputStream(inputStream);
                length = bis.available();
                byte[] data = new byte[length];
                bis.read(data);

                return data;
            } catch (IOException e) {

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception e) {

                    }
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TangramFragment fragment = TangramFragment.create(new FragmentDataModel.Builder()
                .setJsonPageData(new String(CommonUtils.requireNonNull(getAssertsFile(this, "data.json"))))
                .build());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment, "TangramFragment")
                .commit();
    }
}
