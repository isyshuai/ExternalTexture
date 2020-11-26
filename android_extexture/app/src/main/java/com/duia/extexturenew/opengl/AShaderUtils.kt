package com.duia.extexturenew.opengl

import android.content.res.Resources
import android.opengl.GLES20
import android.util.Log

/**
 * @auther YuanShuai
 * @created at 2020/11/17 16:43
 * @desc
 */
object AShaderUtils {
    private const val TAG = "ShaderUtils"
    fun checkGLError(op: String?) {
    }

    fun loadShader(shaderType: Int, source: String?): Int {
        var shader = GLES20.glCreateShader(shaderType)
        if (0 != shader) {
            GLES20.glShaderSource(shader, source)
            GLES20.glCompileShader(shader)
            val compiled = IntArray(1)
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0)
            if (compiled[0] == 0) {
                Log.e(TAG, "Could not compile shader:$shaderType")
                Log.e(
                    TAG,
                    "GLES20 Error:" + GLES20.glGetShaderInfoLog(shader)
                )
                GLES20.glDeleteShader(shader)
                shader = 0
            }
        }
        return shader
    }

    fun loadShader(
        res: Resources,
        shaderType: Int,
        resName: String?
    ): Int {
        return loadShader(shaderType, loadFromAssetsFile(resName, res))
    }

    fun createProgram(vertexSource: String?, fragmentSource: String?): Int {
        val vertex = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource)
        if (vertex == 0) return 0
        val fragment = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource)
        if (fragment == 0) return 0
        var program = GLES20.glCreateProgram()
        if (program != 0) {
            GLES20.glAttachShader(program, vertex)
            checkGLError("Attach Vertex Shader")
            GLES20.glAttachShader(program, fragment)
            checkGLError("Attach Fragment Shader")
            GLES20.glLinkProgram(program)
            val linkStatus = IntArray(1)
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0)
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(
                    TAG,
                    "Could not link program:" + GLES20.glGetProgramInfoLog(program)
                )
                GLES20.glDeleteProgram(program)
                program = 0
            }
        }
        return program
    }

    fun createProgram(
        res: Resources,
        vertexRes: String?,
        fragmentRes: String?
    ): Int {
        return createProgram(
            loadFromAssetsFile(vertexRes, res),
            loadFromAssetsFile(fragmentRes, res)
        )
    }

    fun loadFromAssetsFile(
        fname: String?,
        res: Resources
    ): String? {
        val result = StringBuilder()
        try {
            val `is` = res.assets.open(fname!!)
            var ch: Int
            val buffer = ByteArray(1024)
            while (-1 != `is`.read(buffer).also { ch = it }) {
                result.append(String(buffer, 0, ch))
            }
        } catch (e: Exception) {
            return null
        }
        return result.toString().replace("\\r\\n".toRegex(), "\n")
    }
}