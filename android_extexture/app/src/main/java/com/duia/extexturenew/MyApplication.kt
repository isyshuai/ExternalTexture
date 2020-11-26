package com.duia.extexturenew

import android.app.Application
import com.expandable.andoridmoduleflutter.ex.ExTexturePlugin
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.view.FlutterMain


/**
 * @fileName: MyApplication
 * @date: 2020/11/12 18:38
 * @auther: YuanShuai
 * @tag: class//
 * @describe:
 **/
class MyApplication : Application() {
companion object{
    open var drawTheme=1 //1 surface 2 openGl
}

    override fun onCreate() {
        super.onCreate()
        var flutterEngine: FlutterEngine = FlutterEngine(this)
        flutterEngine.navigationChannel.setInitialRoute("openGl 绘制纹理")
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        ExTexturePlugin.registerWith(flutterEngine!!, this)
        FlutterEngineCache
            .getInstance()
            .put("extexture", flutterEngine)

        var flutterEngine2: FlutterEngine = FlutterEngine(this)
        flutterEngine2.navigationChannel.setInitialRoute("canvas 绘制纹理")
        flutterEngine2.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        ExTexturePlugin.registerWith(flutterEngine2!!, this)
        FlutterEngineCache
            .getInstance()
            .put("extexture2", flutterEngine2)
    }
}