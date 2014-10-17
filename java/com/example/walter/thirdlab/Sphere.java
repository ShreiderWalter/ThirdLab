package com.example.walter.thirdlab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.example.walter.thirdlab.math.Maths;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 *
 */
public class Sphere {

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // The matrix must be included as a modifier of gl_Position.
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private static final int MAXIMUM_ALLOWED_DEPTH = 7;
    private static final int VERTEX_MAGIC_NUMBER = 5;
    private static final int NUM_FLOATS_PER_VERTEX = 3;
    private static final int NUM_FLOATS_PER_TEXTURE = 2;
    private static final int AMOUNT_OF_NUMBERS_PER_VERTEX_POINT = 3;
    private static final int AMOUNT_OF_NUMBERS_PER_TEXTURE_POINT = 2;
    private final List<FloatBuffer> mVertexBuffer = new ArrayList<FloatBuffer>();
    private final List<float[]> mVertices = new ArrayList<float[]>();
    private final List<FloatBuffer> mTextureBuffer = new ArrayList<FloatBuffer>();
    private final List<float[]> mTexture = new ArrayList<float[]>();
    private final int[] mTextures = new int[1];
    private final int mTotalNumStrips;
    private int mMVPMatrixHandle;

    public Integer drawable;
    public boolean flagLoaded = false;

    public Sphere(final int depth, final float radius, int drawable) {
        this.drawable = drawable;
        final int d = Math.max(1, Math.min(MAXIMUM_ALLOWED_DEPTH, depth));

        this.mTotalNumStrips = Maths.power(2, d - 1) * VERTEX_MAGIC_NUMBER;
        final int numVerticesPerStrip = Maths.power(2, d) * 3;
        final double altitudeStepAngle = Maths.ONE_TWENTY_DEGREES / Maths.power(2, d);
        final double azimuthStepAngle = Maths.THREE_SIXTY_DEGREES / this.mTotalNumStrips;
        double x, y, z, h, altitude, azimuth;

        for (int stripNum = 0; stripNum < this.mTotalNumStrips; stripNum++) {
            final float[] vertices = new float[numVerticesPerStrip * NUM_FLOATS_PER_VERTEX]; // NOPMD
            final float[] texturePoints = new float[numVerticesPerStrip * NUM_FLOATS_PER_TEXTURE]; // NOPMD
            int vertexPos = 0;
            int texturePos = 0;

            altitude = Maths.NINETY_DEGREES;
            azimuth = stripNum * azimuthStepAngle;
            for (int vertexNum = 0; vertexNum < numVerticesPerStrip; vertexNum += 2) {
                y = radius * Math.sin(altitude);
                h = radius * Math.cos(altitude);
                z = h * Math.sin(azimuth);
                x = h * Math.cos(azimuth);
                vertices[vertexPos++] = (float) x;
                vertices[vertexPos++] = (float) y;
                vertices[vertexPos++] = (float) z;

                texturePoints[texturePos++] = (float) (1 - azimuth / Maths.THREE_SIXTY_DEGREES);
                texturePoints[texturePos++] = (float) (1 - (altitude + Maths.NINETY_DEGREES) / Maths.ONE_EIGHTY_DEGREES);

                altitude -= altitudeStepAngle;
                azimuth -= azimuthStepAngle / 2.0;
                y = radius * Math.sin(altitude);
                h = radius * Math.cos(altitude);
                z = h * Math.sin(azimuth);
                x = h * Math.cos(azimuth);
                vertices[vertexPos++] = (float) x;
                vertices[vertexPos++] = (float) y;
                vertices[vertexPos++] = (float) z;

                texturePoints[texturePos++] = (float) (1 - azimuth / Maths.THREE_SIXTY_DEGREES);
                texturePoints[texturePos++] = (float) (1 - (altitude + Maths.NINETY_DEGREES) / Maths.ONE_EIGHTY_DEGREES);

                azimuth += azimuthStepAngle;
            }

            this.mVertices.add(vertices);
            this.mTexture.add(texturePoints);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(numVerticesPerStrip * NUM_FLOATS_PER_VERTEX * Float.SIZE);
            byteBuffer.order(ByteOrder.nativeOrder());
            FloatBuffer fb = byteBuffer.asFloatBuffer();
            fb.put(this.mVertices.get(stripNum));
            fb.position(0);
            this.mVertexBuffer.add(fb);

            byteBuffer = ByteBuffer.allocateDirect(numVerticesPerStrip * NUM_FLOATS_PER_TEXTURE * Float.SIZE);
            byteBuffer.order(ByteOrder.nativeOrder());
            fb = byteBuffer.asFloatBuffer();
            fb.put(this.mTexture.get(stripNum));
            fb.position(0);
            this.mTextureBuffer.add(fb);

        }
    }

    public void loadGLTexture(final GL10 gl, final Context context, final int texture) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), texture);
        gl.glGenTextures(1, this.mTextures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, this.mTextures[0]);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        bitmap = null;
        flagLoaded = true;
    }

    public void draw(final GL10 gl) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, this.mTextures[0]);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glFrontFace(GL10.GL_CW);

        for (int i = 0; i < this.mTotalNumStrips; i++) {
            gl.glVertexPointer(AMOUNT_OF_NUMBERS_PER_VERTEX_POINT, GL10.GL_FLOAT, 0, this.mVertexBuffer.get(i));
            gl.glTexCoordPointer(AMOUNT_OF_NUMBERS_PER_TEXTURE_POINT, GL10.GL_FLOAT, 0, this.mTextureBuffer.get(i));

            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.mVertices.get(i).length / AMOUNT_OF_NUMBERS_PER_VERTEX_POINT);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    public void draw(final GL10 gl, float[] mvpMatrix) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, this.mTextures[0]);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glFrontFace(GL10.GL_CW);

        for (int i = 0; i < this.mTotalNumStrips; i++) {
            gl.glVertexPointer(AMOUNT_OF_NUMBERS_PER_VERTEX_POINT, GL10.GL_FLOAT, 0, this.mVertexBuffer.get(i));
            gl.glTexCoordPointer(AMOUNT_OF_NUMBERS_PER_TEXTURE_POINT, GL10.GL_FLOAT, 0, this.mTextureBuffer.get(i));

            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, this.mVertices.get(i).length / AMOUNT_OF_NUMBERS_PER_VERTEX_POINT);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

}
