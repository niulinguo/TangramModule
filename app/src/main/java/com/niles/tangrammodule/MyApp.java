package com.niles.tangrammodule;

import android.app.Application;
import android.widget.ImageView;

import com.niles.tangram.Config;
import com.niles.tangram.TangramManager;
import com.niles.tangram.common.LoadImageListener;
import com.squareup.picasso.Picasso;

/**
 * Created by Niles
 * Date 2018/9/23 19:03
 * Email niulinguo@163.com
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TangramManager.init(this, new Config.Builder()
                .setLoadImageListener(new LoadImageListener() {
                    @Override
                    public void loadImage(ImageView imageView, String url) {
                        Picasso
                                .get()
                                .load(url)
                                .into(imageView);
                    }
                })
                .setPrintLog(true)
                .build());
    }
}
