package gles.test;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BitmapRenderer implements Renderer {

    GLSurfaceView curView;
    int iProgId;
    int iPosition;
    float[] fVertices = {
            1f, 1f, 0, 1, 0,
            -1f, -1f, 0, 0, 1,
            1f, -1f, 0, 1, 1,
            -1f, 1f, 0, 0, 0,
            1f, 1f, 0, 1, 0,
            -1f, -1f, 0, 0, 1};
    int iTexCoords1;
    int iTexLoc1;
    int iTexId1;

    int iTexCoords2;
    int iTexLoc2;
    int iTexId2;

    FloatBuffer vertexBuffer;
    String shader;
    Bitmap bitmap1;
    Bitmap bitmap2;

    float r;
    float g;
    float b;
    float a;

    int iR;
    int iG;
    int iB;
    int iA;

    public BitmapRenderer(Bitmap bitmap1, Bitmap bitmap2, float r, float g, float b, float a, String shader) {
        vertexBuffer = ByteBuffer.allocateDirect(fVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(fVertices).position(0);
        this.shader = shader;
        this.bitmap1 = bitmap1;
        this.bitmap2 = bitmap2;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glUseProgram(iProgId);

        vertexBuffer.position(0);
        GLES20.glVertexAttribPointer(iPosition, 3, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer);
        GLES20.glEnableVertexAttribArray(iPosition);

        vertexBuffer.position(3);
        GLES20.glVertexAttribPointer(iTexCoords1, 2, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer);
        GLES20.glEnableVertexAttribArray(iTexCoords1);
        if (bitmap2 != null) {
            GLES20.glVertexAttribPointer(iTexCoords2, 2, GLES20.GL_FLOAT, false, 5 * 4, vertexBuffer);
            GLES20.glEnableVertexAttribArray(iTexCoords2);
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, iTexId1);
        GLES20.glUniform1i(iTexLoc1, 0);

        if (bitmap2 != null) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, iTexId2);
            GLES20.glUniform1i(iTexLoc2, 1);
        } else {
            GLES20.glUniform1f(iR, r);
            GLES20.glUniform1f(iG, g);
            GLES20.glUniform1f(iB, b);
            GLES20.glUniform1f(iA, a);
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 1);

        String strVShader =
                "attribute vec4 a_position;" +
                        "attribute vec2 a_texCoords1;" +
                        "varying vec2 textureCoordinate1;" +
                        "void main()" +
                        "{" +
                        "  gl_Position = a_position;" +
                        "  textureCoordinate1 = a_texCoords1;" +
                        "}";

        if (bitmap2 != null) {
            strVShader =
                    "attribute vec4 a_position;" +
                            "attribute vec2 a_texCoords1;" +
                            "attribute vec2 a_texCoords2;" +
                            "varying vec2 textureCoordinate1;" +
                            "varying vec2 textureCoordinate2;" +
                            "void main()" +
                            "{" +
                            "  gl_Position = a_position;" +
                            "  textureCoordinate1 = a_texCoords1;" +
                            "  textureCoordinate2 = a_texCoords2;" +
                            "}";
        }

        if (bitmap2 == null) {
            shader = String.format(shader, shaderInput1, shaderTexture1);
        } else {
            shader = String.format(shader, shaderInput2, shaderTexture2);
        }

        iProgId = LoadProgram(strVShader, shader);
        iPosition = GLES20.glGetAttribLocation(iProgId, "a_position");
        iTexCoords1 = GLES20.glGetAttribLocation(iProgId, "a_texCoords1");
        iTexLoc1 = GLES20.glGetUniformLocation(iProgId, "inputImageTexture1");

        if (bitmap2 != null) {
            iTexCoords2 = GLES20.glGetAttribLocation(iProgId, "a_texCoords2");
            iTexLoc2 = GLES20.glGetUniformLocation(iProgId, "inputImageTexture2");
        } else {
            iR = GLES20.glGetUniformLocation(iProgId, "i_r");
            iG = GLES20.glGetUniformLocation(iProgId, "i_g");
            iB = GLES20.glGetUniformLocation(iProgId, "i_b");
            iA = GLES20.glGetUniformLocation(iProgId, "i_a");
        }

        iTexId1 = LoadTexture(curView, bitmap1);
        if (bitmap2 != null) {
            iTexId2 = LoadTexture(curView, bitmap2);
        }
    }

    public int LoadTexture(GLSurfaceView view, Bitmap bitmap) {
        Log.d("Utils", "Loadtexture");
        int textures[] = new int[1];
        try {
            GLES20.glGenTextures(1, textures, 0);

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
            Log.d("LoadTexture", "Loaded texture" + ":H:" + bitmap.getHeight() + ":W:" + bitmap.getWidth());
        } catch (Exception e) {
            Log.d("LoadTexture", e.toString() + ":" + e.getMessage() + ":" + e.getLocalizedMessage());
        }
        //bitmap.recycle();
        return textures[0];
    }

    public int LoadShader(String strSource, int iType) {
        Log.d("Utils", "LoadShader");
        int[] compiled = new int[1];
        int iShader = GLES20.glCreateShader(iType);
        GLES20.glShaderSource(iShader, strSource);
        GLES20.glCompileShader(iShader);
        GLES20.glGetShaderiv(iShader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.d("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(iShader));
            return 0;
        }
        return iShader;
    }

    public int LoadProgram(String strVSource, String strFSource) {
        Log.d("Utils", "LoadProgram");
        int iVShader;
        int iFShader;
        int iProgId;
        int[] link = new int[1];

        iVShader = LoadShader(strVSource, GLES20.GL_VERTEX_SHADER);
        if (iVShader == 0) {
            Log.d("Load Program", "Vertex Shader Failed");
            return 0;
        }
        iFShader = LoadShader(strFSource, GLES20.GL_FRAGMENT_SHADER);
        if (iFShader == 0) {
            Log.d("Load Program", "Fragment Shader Failed");
            return 0;
        }

        iProgId = GLES20.glCreateProgram();

        GLES20.glAttachShader(iProgId, iVShader);
        GLES20.glAttachShader(iProgId, iFShader);

        GLES20.glLinkProgram(iProgId);

        GLES20.glGetProgramiv(iProgId, GLES20.GL_LINK_STATUS, link, 0);
        if (link[0] <= 0) {
            Log.d("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(iVShader);
        GLES20.glDeleteShader(iFShader);
        return iProgId;
    }

    public static float rnd(float min, float max) {
        float fRandNum = (float) Math.random();
        return min + (max - min) * fRandNum;
    }

    private final String shaderInput1 =
            "uniform float i_r;" +
                    "uniform float i_g;" +
                    "uniform float i_b;" +
                    "uniform float i_a;";

    private final String shaderInput2 =
            "varying highp vec2 textureCoordinate2;" +
                    "uniform sampler2D inputImageTexture2;";

    private final String shaderTexture1 =
            "vec4(i_r, i_g, i_b, i_a);";

    private final String shaderTexture2 =
            "texture2D(inputImageTexture2, textureCoordinate2);";
}
