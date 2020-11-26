package com.duia.extexturenew.opengl

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import android.util.LongSparseArray
import io.flutter.view.TextureRegistry

/**
 * @fileName: EtHelper
 * @date: 2020/5/11 14:38
 * @auther: YuanShuai
 * @tag: class//
 * @describe:外接纹理OpenGl加载
 */
object OpenGLHelper {
    const val version = "0.0.28"
    private val ExternalGLThreadSparseArray: LongSparseArray<ExternalGLThread?>? = LongSparseArray()
    private val SurfaceTextureEntrySparseArray: LongSparseArray<TextureRegistry.SurfaceTextureEntry?>? =
        LongSparseArray()
    private const val mDrawable = "drawable"
    private const val mHttp = "http"

    /**
     * 适用于图片不改动
     */
    fun loadImage2(
        img: String?,
        surfaceTextureEntry: TextureRegistry,
        width: Double?,
        height: Double?,
        context: Context
    ): Long {
        var surfaceEntry = surfaceTextureEntry.createSurfaceTexture()
        val surfaceTexture = surfaceEntry.surfaceTexture().apply {
            setDefaultBufferSize(width!!.toInt() * 3, height!!.toInt() * 3)
        }
        var a = AFilter(context, "filter/half_color_vertex.sh", "filter/half_color_fragment.sh")
        val resId = context?.resources?.getIdentifier(img, "drawable", context.packageName)
        val bitmap =
            resId?.let { BitmapFactory.decodeResource(context?.resources, it) }
        a.setBitmap(bitmap, width!! * 3, height!! * 3)
        var externalGLThread = ExternalGLThread(surfaceTexture, a)
        externalGLThread.start()
        var textureId = surfaceEntry.id()
        ExternalGLThreadSparseArray?.put(textureId, externalGLThread)
        SurfaceTextureEntrySparseArray?.put(textureId, surfaceEntry)
        return textureId
    }

    /**
     * 适用于图片改动
     */
    fun loadImage(
        img: String?,
        surfaceTextureEntry: TextureRegistry,
        width: Double?,
        height: Double?,
        context: Context?
    ): Long {
        var surfaceEntry = surfaceTextureEntry.createSurfaceTexture()
        val surfaceTexture = surfaceEntry.surfaceTexture().apply {
            setDefaultBufferSize(width!!.toInt() * 3, height!!.toInt() * 3)
        }
        val resId =
            context?.resources?.getIdentifier(img, "drawable", context.packageName)
        val bitmap =
            resId?.let { BitmapFactory.decodeResource(context?.resources, it) }
        var a = MyRender(context, width, height, bitmap)
        var externalGLThread = ExternalGLThread(surfaceTexture, a)
        externalGLThread.start()
        var textureId = surfaceEntry.id()
        ExternalGLThreadSparseArray?.put(textureId!!, externalGLThread)
        SurfaceTextureEntrySparseArray?.put(textureId!!, surfaceEntry)
        return textureId
    }

    fun cleanTexture(textureId: Long) {
        try {
            ExternalGLThreadSparseArray?.run {
                this[textureId]?.dispose()
                remove(textureId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(
                "OpenGLHelper",
                "(cleanTexture:${Thread.currentThread().stackTrace[2].lineNumber}) ExternalGLThreadSparseArray 释放失败"
            )
        }
        try {
            SurfaceTextureEntrySparseArray?.run {
                this[textureId]?.release()
                remove(textureId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(
                "OpenGLHelper",
                "(cleanTexture:${Thread.currentThread().stackTrace[2].lineNumber}) SurfaceTextureEntrySparseArray 释放失败"
            )
        }

    }
}



