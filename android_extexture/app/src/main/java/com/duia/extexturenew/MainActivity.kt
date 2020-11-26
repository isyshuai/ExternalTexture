package com.duia.extexturenew

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.flutter.embedding.android.FlutterActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.surface).setOnClickListener {
            startActivity(
                FlutterActivity
                    .withCachedEngine("extexture2")
                    .build(this)
            )
            MyApplication.drawTheme = 1
        }
        findViewById<TextView>(R.id.opengl).setOnClickListener {
            startActivity(
                FlutterActivity
                    .withCachedEngine("extexture")
                    .build(this)
            )
            MyApplication.drawTheme = 2
        }
    }
}