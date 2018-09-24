package com.niles.tangram;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.niles.tangram.common.CommonUtils;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.util.IInnerImageSetter;

/**
 * Created by Niles
 * Date 2018/9/23 19:03
 * Email niulinguo@163.com
 */
public class TangramManager {

    private static Application sApp;
    private static Config sConfig;

    public static void init(Application app, Config config) {
        sApp = CommonUtils.requireNonNull(app);
        sConfig = CommonUtils.requireNonNull(config);

        TangramBuilder.switchLog(sConfig.isPrintLog());
        TangramBuilder.init(sApp, new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                sConfig.getLoadImageListener().loadImage(view, url);
            }
        }, ImageView.class);
    }

    public static Config getConfig() {
        return sConfig;
    }
}
