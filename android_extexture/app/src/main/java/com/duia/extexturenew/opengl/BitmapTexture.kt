package com.duia.extexturenew.opengl

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLUtils
import com.duia.extexturenew.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * @auther YuanShuai
 * @created at 2020/11/17 16:30
 * @desc 纹理  根据坐标系映射
 */
class BitmapTexture(private val context: Context, private val bit: Bitmap?) {
    private val vertexCount =
        vertexData.size / COORDS_PER_VERTEX

    //每一次取的总的点 大小
    private val vertexStride =
        COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    //位置
    private val vertexBuffer: FloatBuffer

    //纹理
    private val textureBuffer: FloatBuffer
    private var program = 0
    private var avPosition = 0

    //纹理位置
    private var afPosition = 0

    //纹理id
    private var textureId = 0
    fun onSurfaceCreated() {
        val vertexSource = ShaderUtil.readRawTxt(context, R.raw.vertex_shader)
        val fragmentSource = ShaderUtil.readRawTxt(context, R.raw.fragment_shader)
        program = ShaderUtil.createProgram(vertexSource, fragmentSource)
        if (program > 0) {
            //获取顶点坐标字段
            avPosition = GLES20.glGetAttribLocation(program, "av_Position")
            //获取纹理坐标字段
            afPosition = GLES20.glGetAttribLocation(program, "af_Position")
            val textureIds = IntArray(1)
            //创建纹理
            GLES20.glGenTextures(1, textureIds, 0)
            if (textureIds[0] == 0) {
                return
            }
            textureId = textureIds[0]
            //绑定纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId)
            //环绕（超出纹理坐标范围）  （s==x t==y GL_REPEAT 重复）
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT)
            //过滤（纹理像素映射到坐标点）  （缩小、放大：GL_LINEAR线性）
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MIN_FILTER,
                GLES20.GL_LINEAR
            )
            GLES20.glTexParameteri(
                GLES20.GL_TEXTURE_2D,
                GLES20.GL_TEXTURE_MAG_FILTER,
                GLES20.GL_LINEAR
            )

//            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bbb);
            if (bit == null) {
                return
            }
            //设置纹理为2d图片
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bit, 0)
        }
    }

    fun draw() {
        //使用程序
        GLES20.glUseProgram(program)
        GLES20.glEnableVertexAttribArray(avPosition)
        GLES20.glEnableVertexAttribArray(afPosition)
        //设置顶点位置值
        GLES20.glVertexAttribPointer(
            avPosition,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            vertexBuffer
        )
        //设置纹理位置值
        GLES20.glVertexAttribPointer(
            afPosition,
            COORDS_PER_VERTEX,
            GLES20.GL_FLOAT,
            false,
            vertexStride,
            textureBuffer
        )
        //绘制 GLES20.GL_TRIANGLE_STRIP:复用坐标
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(avPosition)
        GLES20.glDisableVertexAttribArray(afPosition)
    }

    companion object {
        //顶点坐标
        var vertexData = floatArrayOf( // in counterclockwise order:
            -1f, -1f, 0.0f,  // bottom left
            1f, -1f, 0.0f,  // bottom right
            -1f, 1f, 0.0f,  // top left
            1f, 1f, 0.0f
        )

        //纹理坐标  对应顶点坐标  与之映射
        var textureData = floatArrayOf( // in counterclockwise order:
            0f, 1f, 0.0f,  // bottom left
            1f, 1f, 0.0f,  // bottom right
            0f, 0f, 0.0f,  // top left
            1f, 0f, 0.0f
        )

        //每一次取点的时候取几个点
        const val COORDS_PER_VERTEX = 3
    }

    init {
        vertexBuffer =
            ByteBuffer.allocateDirect(vertexData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData)
        vertexBuffer.position(0)
        textureBuffer =
            ByteBuffer.allocateDirect(textureData.size * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(textureData)
        textureBuffer.position(0)
    }
}