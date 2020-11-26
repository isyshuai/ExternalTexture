package com.duia.extexturenew.opengl

import android.content.Context
import android.opengl.GLES20
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @auther YuanShuai
 * @created at 2020/11/17 16:34
 * @desc
 */
object ShaderUtil {
    private const val TAG = "ShaderUtil"
    fun readRawTxt(context: Context, rawId: Int): String {
        val inputStream = context.resources.openRawResource(rawId)
        val reader =
            BufferedReader(InputStreamReader(inputStream))
        val sb = StringBuffer()
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line).append("\n")
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    fun loadShader(shaderType: Int, source: String?): Int {
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        var shader = GLES20.glCreateShader(shaderType)
        if (shader != 0) {
            //添加代码到shader
            GLES20.glShaderSource(shader, source)
            //编译shader
            GLES20.glCompileShader(shader)
            val compile = IntArray(1)
            //检测是否编译成功
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compile, 0)
            if (compile[0] != GLES20.GL_TRUE) {
                Log.d(TAG, "shader compile error")
                GLES20.glDeleteShader(shader)
                shader = 0
            }
        }
        return shader
    }

    fun createProgram(vertexSource: String?, fragmentSource: String?): Int {
        //获取vertex shader
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource)
        if (vertexShader == 0) {
            return 0
        }
        //获取fragment shader
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource)
        if (fragmentShader == 0) {
            return 0
        }
        //创建一个空的渲染程序
        var program = GLES20.glCreateProgram()
        if (program != 0) {
            //添加vertexShader到渲染程序
            GLES20.glAttachShader(program, vertexShader)
            //添加fragmentShader到渲染程序
            GLES20.glAttachShader(program, fragmentShader)
            //关联为可执行渲染程序
            GLES20.glLinkProgram(program)
            val linsStatus = IntArray(1)
            //检测是否关联成功
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linsStatus, 0)
            if (linsStatus[0] != GLES20.GL_TRUE) {
                Log.d(TAG, "link program error")
                GLES20.glDeleteProgram(program)
                program = 0
            }
        }
        return program
    }
}