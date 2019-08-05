package com.yocn.meida.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * @Author yocn
 * @Date 2019/8/5 4:11 PM
 * @ClassName BitmapUtil
 */
public class BitmapUtil {
    public static Bitmap rotateBitmap(Bitmap origin, float rotate) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(rotate);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}