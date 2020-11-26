package com.expandable.andoridmoduleflutter.ex

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import com.duia.extexturenew.MyApplication
import com.duia.extexturenew.opengl.OpenGLHelper
import com.expandable.andoridmoduleflutter.ex.EtHelper.cleanTexture
import com.expandable.andoridmoduleflutter.ex.EtHelper.loadGif
import com.expandable.andoridmoduleflutter.ex.EtHelper.loadImg
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.view.TextureRegistry

/**
 * @auther YuanShuai
 * @created at 2020/5/15 15:36
 * @desc 外接纹理平台桥接
 */
class ExTexturePlugin(
    private val surfaceTextureEntry: TextureRegistry,
    private val context: Context
) : FlutterPlugin, MethodCallHandler {

    private val mDrawable = "drawable"
    private val mTextureId = "textureId"
    private val mImg = "img"
    private val mWidth = "width"
    private val mHeight = "height"
    private val mMethodCreate = "create"
    private val mMethodDispose = "dispose"
    private val mHttp = "http"
    private val mAsGif = "asGif"
    private val mDrawType = "drawType"

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        var mResources = context.resources
        var packageName = context.packageName
        when (call.method) {
            mMethodCreate -> {
                try {
                    val img = call.argument<String>(mImg)
                    val width = call.argument<Double>(mWidth)
                    val height = call.argument<Double>(mHeight)
                    val asGif = call.argument<Boolean>(mAsGif)!!
                    val mDrawType = call.argument<Int>(mDrawType)
                    if (MyApplication.drawTheme == 2) {
                        //OpenGl方式
                        var textureId =
                            OpenGLHelper.loadImage(img, surfaceTextureEntry, width, height, context)
                        result.success(textureId)
                        Log.e(
                            "ExTexturePlugin",
                            "(onMethodCall:${Thread.currentThread().stackTrace[2].lineNumber}) openGl createId=" + textureId
                        )
                    } else {
                        //canvas方式
                        img?.let {
                            if (asGif) {
                                result.success(
                                    loadGif(
                                        context,
                                        surfaceTextureEntry,
                                        it,
                                        width,
                                        height
                                    )
                                )
                            } else {
                                var id = loadImg(
                                    context,
                                    surfaceTextureEntry,
                                    it,
                                    width,
                                    height,
                                    mDrawType
                                )
                                result.success(id)
                                Log.e(
                                    "ExTexturePlugin",
                                    "(onMethodCall:${Thread.currentThread().stackTrace[2].lineNumber}) surface createId=" + id
                                )
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(
                        "ExTexturePlugin",
                        "(onMethodCall:${Thread.currentThread().stackTrace[2].lineNumber}) create error !"
                    )
                }
            }
            mMethodDispose -> {
                try {
                    val textureId = call.argument<Long>(mTextureId)!!
                    if (MyApplication.drawTheme == 2) {
                        OpenGLHelper.cleanTexture(textureId)
                        Log.e(
                            "ExTexturePlugin",
                            "(onMethodCall:${Thread.currentThread().stackTrace[2].lineNumber}) openGl cleanId=" + textureId
                        )
                    } else {
                        cleanTexture(textureId)
                        Log.e(
                            "ExTexturePlugin",
                            "(onMethodCall:${Thread.currentThread().stackTrace[2].lineNumber}) surface cleanId=" + textureId
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(
                        "ExTexturePlugin",
                        "(onMethodCall:${Thread.currentThread().stackTrace[2].lineNumber}) clean Error!"
                    )
                }
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    companion object {
        private const val mChannelName = "duia_texture_channel"

        //        @JvmStatic
//        fun registerWith(registrar: Registrar, context: Context) {
//            val channel = MethodChannel(registrar.messenger(), mChannelName)
//            channel.setMethodCallHandler(ExTexturePlugin(registrar.textures(), context))
//        }
        @JvmStatic
        fun registerWith(flutterEngine: FlutterEngine, context: Context) {
            val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, mChannelName)
            channel.setMethodCallHandler(ExTexturePlugin(flutterEngine.renderer, context))
        }
    }

    private lateinit var channel: MethodChannel
    private lateinit var textureRegistry: TextureRegistry
    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(binding.flutterEngine.dartExecutor, "duia_texture_channel")
        channel.setMethodCallHandler(this)
        textureRegistry = binding.textureRegistry
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

}