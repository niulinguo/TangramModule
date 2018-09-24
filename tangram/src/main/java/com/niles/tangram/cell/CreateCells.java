package com.niles.tangram.cell;

import com.niles.tangram.common.CellModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niles
 * Date 2018/9/24 12:50
 * Email niulinguo@163.com
 */
public class CreateCells {

    public static List<CellModel> create() {
        List<CellModel> cellModelList = new ArrayList<>();
        cellModelList.add(CellModel
                .newBuilder()
                .setType(CellType.SIMPLE_IMG)
                .setViewClass(SimpleImgView.class)
                .build());
        return cellModelList;
    }
}
