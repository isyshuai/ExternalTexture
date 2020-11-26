package com.duia.extexturenew.opengl;


import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;

/**
 * @auther YuanShuai
 * @created at 2020/11/17 16:32
 * @desc
 */
public class MyRender implements ExternalGLThread.Renderer {

    private Context context;
    Double width,height;
    private BitmapTexture bitmapTexture;
    Bitmap bit;
    public MyRender(Context context, Double width, Double height, Bitmap bit) {
        this.context = context;
        this.width=width;
        this.height=height;
        this.bit=bit;
    }

    @Override
    public void onCreate() {
        bitmapTexture = new BitmapTexture(context,bit);
        bitmapTexture.onSurfaceCreated();
    }

    @Override
    public boolean onDraw() {
        //清空颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //设置背景颜色
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        bitmapTexture.draw();
        return true;
    }

    @Override
    public void onDispose() {

    }
}
