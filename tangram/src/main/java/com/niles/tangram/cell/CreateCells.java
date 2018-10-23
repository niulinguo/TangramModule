package com.niles.tangram.cell;

import com.niles.tangram.common.CellInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niles
 * Date 2018/9/24 12:50
 * Email niulinguo@163.com
 */
public class CreateCells {

    public static List<CellInfo> create() {
        List<CellInfo> cellInfoList = new ArrayList<>();
        cellInfoList.add(new CellInfo.Builder()
                .setType(CellType.SIMPLE_IMG)
                .setViewClass(SimpleImgView.class)
                .build());
        return cellInfoList;
    }
}
