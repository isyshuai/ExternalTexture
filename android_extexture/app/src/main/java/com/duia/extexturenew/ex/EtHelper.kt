package com.expandable.andoridmoduleflutter.ex

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.util.LongSparseArray
import android.view.Surface
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import io.flutter.view.TextureRegistry
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.util.*

/**
 * @fileName: EtHelper
 * @date: 2020/5/11 14:38
 * @auther: YuanShuai
 * @tag: class//
 * @describe:外接纹理
 */
object EtHelper {
    const val version = "0.0.28"
    private val textureLongSparseArray: LongSparseArray<SurfaceTexture?>? = LongSparseArray()
    private val surfaceLongSparseArray: LongSparseArray<Surface?>? = LongSparseArray()
    private val surfaceEntityArray: LongSparseArray<TextureRegistry.SurfaceTextureEntry?>? =
        LongSparseArray()
    private val handlerLongSparseArray: LongSparseArray<Handler?>? = LongSparseArray()
    private val movieNetMap = HashMap<String, Movie>()
    private val movieResourceMap = HashMap<Int, Movie>()
    private const val mHttp = "http"
    private const val mDrawable = "drawable"
//    fun loadGif(
//        context: Context,
//        surfaceTextureEntry: TextureRegistry,
//        imgResource: Int,
//        width: Double?,
//        height: Double?
//    ): Long {
//        return drawGifTexture(context, surfaceTextureEntry, imgResource, null, width, height)
//    }

    fun loadGif(
        context: Context,
        surfaceTextureEntry: TextureRegistry,
        img: String,
        width: Double?,
        height: Double?
    ): Long {
        return if(img.startsWith(mHttp)){
            drawGifTexture(context, surfaceTextureEntry, 0, img, width, height)
        }else{
            val resId = context.resources.getIdentifier(img, mDrawable, context.packageName)
            drawGifTexture(context, surfaceTextureEntry, resId, null, width, height)
        }
    }

    fun loadImg(
        context: Context,
        surfaceTextureEntry: TextureRegistry,
        img: String,
        width: Double?,
        height: Double?,
        drawType:Int?
    ): Long {
         return if(img.startsWith(mHttp)){
            drawImgTexture(context, surfaceTextureEntry, 0, img, width, height,drawType)
        }else{
            val resId = context.resources.getIdentifier(img, mDrawable, context.packageName)
            drawImgTexture(context, surfaceTextureEntry, resId, null, width, height,drawType)
        }

    }

//    fun loadImg(
//        context: Context,
//        surfaceTextureEntry: TextureRegistry,
//        imgResource: Int,
//        width: Double?,
//        height: Double?,
//        drawType:Int?
//    ): Long {
//        return drawImgTexture(context, surfaceTextureEntry, imgResource, null, width, height,drawType)
//    }

    private fun drawImgTexture(
        context: Context,
        surfaceTextureEntry: TextureRegistry,
        imgResource: Int,
        imgUrl: String?,
        width: Double?,
        height: Double?,
        drawType:Int?
    ): Long {
        val entry = surfaceTextureEntry.createSurfaceTexture()
        val surfaceTexture = entry.surfaceTexture()
        val surface = Surface(surfaceTexture)
        if (imgResource > 0) {
            decodeImg(context, imgResource, null, surfaceTexture, surface, width, height,drawType)
        } else {
            decodeImg(context, 0, imgUrl, surfaceTexture, surface, width, height,drawType)
        }
        surfaceLongSparseArray?.put(entry.id(), surface)
        surfaceEntityArray?.put(entry.id(), entry)
        textureLongSparseArray?.put(entry.id(), surfaceTexture)
        return entry.id()
    }

    private fun drawGifTexture(
        context: Context,
        surfaceTextureEntry: TextureRegistry,
        imgResource: Int,
        imgUrl: String?,
        width: Double?,
        height: Double?
    ): Long {
        val entry = surfaceTextureEntry.createSurfaceTexture()
        val surfaceTexture = entry.surfaceTexture()
        val surface = Surface(surfaceTexture)
        var handler: Handler? = null
        handler = if (imgResource > 0) {
            decodeGif(context, imgResource, null, surfaceTexture, surface, width, height)
        } else {
            decodeGif(context, 0, imgUrl, surfaceTexture, surface, width, height)
        }
        handlerLongSparseArray?.put(entry.id(), handler)
        surfaceEntityArray?.put(entry.id(), entry)
        surfaceLongSparseArray?.put(entry.id(), surface)
        textureLongSparseArray?.put(entry.id(), surfaceTexture)
        return entry.id()
    }

    fun cleanTexture(textureId: Long) {
        try {
            textureLongSparseArray?.run {
                this[textureId]?.release()
                remove(textureId)
            }
            surfaceLongSparseArray?.run {
                this[textureId]?.release()
                remove(textureId)
            }
            handlerLongSparseArray?.run {
                this[textureId]?.removeCallbacksAndMessages(null)
                remove(textureId)
            }
            surfaceEntityArray?.run {
                this[textureId]?.release()
                remove(textureId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun decodeGif(
        context: Context,
        imgResource: Int,
        imgUrl: String?,
        surfaceTexture: SurfaceTexture,
        surface: Surface,
        width: Double?,
        height: Double?
    ): Handler {
        var handler = Handler()
        var movie: Movie? = null
        exCheckNotNull(imgUrl, {
            movieNetMap[imgUrl]?.let {
                movie = it
                surfaceTexture.setDefaultBufferSize(movie?.width()!!, movie?.height()!!)
            }
        }, {
            movieResourceMap[imgResource]?.let {
                movie = it
                surfaceTexture.setDefaultBufferSize(movie?.width()!!, movie?.height()!!)
            }
        })
        if (movie == null) {
            Thread(Runnable {
                try {
                    val file = getImagePath(context, imgUrl, imgResource)
                    val inputStream: InputStream = FileInputStream(file)
                    movie = Movie.decodeStream(inputStream)
                    surfaceTexture.setDefaultBufferSize(movie?.width()!!, movie?.height()!!)
                    inputStream.close()
                    exCheckNotNull(imgUrl, {
                        movieNetMap[imgUrl!!] = movie!!
                    }, {
                        movieResourceMap[imgResource] = movie!!
                    })
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }).start()
        }
        var rect = Rect(0, 0, 0, 0)
        var runnable = object : Runnable {
            override fun run() {
                try {
                    movie?.let {
                        var canvas = surface.lockCanvas(rect)
                        canvas.drawFilter = PaintFlagsDrawFilter(
                            0,
                            Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
                        )
                        it?.draw(canvas, 0f, 0f)
                        it?.setTime((System.currentTimeMillis() % it?.duration()!!).toInt())
                        surface.unlockCanvasAndPost(canvas)
                    }
                    handler?.postDelayed(this, 40)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        handler?.post(runnable)
        return handler
    }

    private fun decodeImg(
        context: Context,
        imgResource: Int,
        imgUrl: String?,
        surfaceTexture: SurfaceTexture,
        surface: Surface,
        width: Double?,
        height: Double?,
        drawType:Int?
    ) { //瞬间峰值 250 /mb 不会释放 交给Glide控制 （以demo峰值为例）

        Glide.with(context).asBitmap().load(imgUrl ?: imgResource)
            .into(object : SimpleTarget<Bitmap?>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    val multiple = 2
                    try {
                        var bit = resource.copy(Bitmap.Config.ARGB_4444, true)
                        var nWidth = width!!.toInt() * multiple
                        var nHeight = height!!.toInt() * multiple
                        surfaceTexture.setDefaultBufferSize(nWidth, nHeight)
                        //surfaceTexture.setDefaultBufferSize(bit.width, bit.height)
                        val rect = Rect(0, 0, 0, 0)
                        val canvas = surface.lockCanvas(rect)
                        canvas.drawFilter = PaintFlagsDrawFilter(
                            0,
                            Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG
                        )
                        if(drawType==1) {
                            // 拉伸
                            canvas.drawBitmap(bit, null, rect, null)
                        }else {
                            // 居中
                            var matrix = Matrix()
                            var paint = Paint()
                            paint.isAntiAlias = true
                            val bw: Int = bit.width
                            val bh: Int = bit.height
                            val scale: Float =
                                (1f * width?.toInt() * multiple / bw).coerceAtMost(1f * height?.toInt() * multiple / bh)
                            var bitmap = scaleBitmap(bit, scale)
                            // compute init left, top
                            val bbw: Int = bitmap?.width!!
                            val bbh: Int = bitmap?.height!!
                            var center =
                                Point(width?.toInt() * multiple / 2, height?.toInt() * multiple / 2)
                            var bmpCenter = Point(bbw / 2, bbh / 2)
                            matrix.postScale(
                                1f,
                                1f,
                                center.x.toFloat(),
                                center.y.toFloat()
                            ) // 中心点参数是有用的
                            matrix.postTranslate(
                                center.x - bmpCenter.x.toFloat(),
                                center.y - bmpCenter.y.toFloat()
                            ) // 移动到当前view 的中心
                            var oval = RectF(
                                center.x - bbw / 2.toFloat(), center.y - bbh / 2.toFloat(),
                                center.x + bbw / 2.toFloat(), center.y + bbh / 2.toFloat()
                            )
                            canvas.drawBitmap(bitmap, matrix, paint)
                        }
                        surface.unlockCanvasAndPost(canvas)
                        bit.recycle()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
    }

    /**
     * 根据给定的宽和高进行拉伸
     *
     * @param origin 原图
     * @param scale  缩放比例
     * @return new Bitmap
     */
    private fun scaleBitmap(origin: Bitmap?, scale: Float): Bitmap? {
        if (origin == null) {
            return null
        }
        val height = origin.height
        val width = origin.width
        val matrix = Matrix()
        matrix.postScale(scale, scale) // 使用后乘
        val newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
        if (!origin.isRecycled) {
            origin.recycle()
        }
        return newBM
    }

}

inline fun <T> exCheckNotNull(any: Any?, function: () -> T, default: () -> T): T =
    if (any != null) function() else default()

private fun getImagePath(context: Context, imgUrl: String?, imgResource: Int): File? {
    var cacheFile: File? = null
    val future = Glide.with(context)
        .load(imgUrl ?: imgResource)
        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
    try {
        cacheFile = future.get()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return cacheFile
}




