package com.duia.extexturenew.opengl

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20

/**
 * @auther YuanShuai
 * @created at 2020/11/17 16:32
 * @desc
 */
class MyRender(
    private val context: Context?,
    var width: Double?,
    var height: Double?,
    var bit: Bitmap?
) : ExternalGLThread.Renderer {
    private var bitmapTexture: BitmapTexture? = null
    override fun onCreate() {
        bitmapTexture = BitmapTexture(context!!, bit)
        bitmapTexture!!.onSurfaceCreated()
    }

    override fun onDraw(): Boolean {
        //清空颜色
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        //设置背景颜色
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
        bitmapTexture!!.draw()
        return true
    }

    override fun onDispose() {}

}