package com.example.walter.thirdlab;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 */
public class GlRenderer implements Renderer {

    private static final int AXIAL_TILT_DEGRESS = 10;

    private static final float CLEAR_RED = 0.0f;

    private static final float CLEAR_GREEN = 0.0f;

    private static final float CLEAR_BLUE = 0.0f;
    private static final float CLEAR_ALPHA = 0.5f;

    private static final float FOVY = 45.0f;

    private static final float Z_NEAR = 0.1f;

    private static final float Z_FAR = 100.0f;

    private static final float OBJECT_DISTANCE = -30.0f;

    private Sphere mMoon, mEarth, mSun, mJupiter, mNeptune, mVenus, mMercury, mMars, mSaturn, mUran;
    private float directionMove = 25.0f;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];

    private final Context mContext;

    private float mRotationAngle, thetaRotationAngle;

    public GlRenderer(final Context context) {
        this.mContext = context;
        this.mRotationAngle = 0.0f;
        this.thetaRotationAngle = 0.0f;

        /*mMoon = new Sphere(4, 1, R.drawable.moon);
        mEarth = new Sphere(4, 2, R.drawable.earth);
        mSun = new Sphere(4, 5, R.drawable.sun);
        mJupiter = new Sphere(4, 4, R.drawable.jupiter_map);
        mNeptune = new Sphere(4, 4, R.drawable.neptune_map);
        mVenus = new Sphere(4, 2, R.drawable.venus_map);
        mMercury = new Sphere(4, 1, R.drawable.mercury_map);
        mMars = new Sphere(4, 1, R.drawable.mars_map);
        mSaturn = new Sphere(4, 4, R.drawable.saturn_map);
        mUran = new Sphere(4, 3, R.drawable.uranus_map);*/
    }

    public GL10 gl;

    public void loadTexture(){
        mMoon.loadGLTexture(gl, mContext, mMoon.drawable);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, OBJECT_DISTANCE);
        gl.glRotatef(this.thetaRotationAngle, 1, 0, 0);
        gl.glRotatef(this.mRotationAngle, 0, 1, 0);
        //gl.glTranslatef(directionMove, 0.0f, 0.0f);

        if(mMoon != null){
            if(!mMoon.flagLoaded) {
                this.mMoon.loadGLTexture(gl, this.mContext, mMoon.drawable);
            }
            this.mMoon.draw(gl);
        } else if(mEarth != null){
            this.mEarth.draw(gl);
        } else if(mMars != null){
            this.mMars.draw(gl);
        } else if(mJupiter != null){
            this.mJupiter.draw(gl);
        } else if(mVenus != null){
            this.mVenus.draw(gl);
        } else if(mNeptune != null){
            this.mNeptune.draw(gl);
        } else if(mSun != null){
            this.mSun.draw(gl);
        } else if(mSaturn != null){
            this.mSaturn.draw(gl);
        } else if(mMercury != null){
            this.mMercury.draw(gl);
        } else if(mUran != null){
            this.mUran.draw(gl);
        }


        /*gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, OBJECT_DISTANCE);
        gl.glRotatef(this.mRotationAngle * 2, 0, 1, 0);
        this.mEarth.draw(gl);*/

        //directionMove += 0.001f;
        //this.mCilinder.draw(gl);
        //this.mCone.draw(gl);
    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width, final int height) {
        final float aspectRatio = (float) width / (float) (height == 0 ? 1 : height);

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, FOVY, aspectRatio, Z_NEAR, Z_FAR);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(final GL10 gl, final EGLConfig config) {
        //this.mMoon.loadGLTexture(gl, this.mContext, R.drawable.sun);
        //this.mEarth.loadGLTexture(gl, this.mContext, R.drawable.earth);
        //this.mCilinder.loadGLTexture(gl, this.mContext, R.drawable.moon);
        //this.mCone.loadGLTexture(gl, this.mContext, R.drawable.moon);
        this.gl = gl;

        if(mMoon != null){
            this.mMoon.loadGLTexture(gl, this.mContext, R.drawable.moon);
        } else if(mEarth != null){
            this.mEarth.loadGLTexture(gl, this.mContext, R.drawable.earth);
        } else if(mMars != null){
            this.mMars.loadGLTexture(gl, this.mContext, R.drawable.mars_map);
        } else if(mJupiter != null){
            this.mJupiter.loadGLTexture(gl, this.mContext, R.drawable.jupiter_map);
        } else if(mVenus != null){
            this.mVenus.loadGLTexture(gl, this.mContext, R.drawable.venus_map);
        } else if(mNeptune != null){
            this.mNeptune.loadGLTexture(gl, this.mContext, R.drawable.neptune_map);
        } else if(mSun != null){
            this.mSun.loadGLTexture(gl, this.mContext, R.drawable.sun);
        } else if(mSaturn != null){
            this.mSaturn.loadGLTexture(gl, this.mContext, R.drawable.saturn_map);
        } else if(mMercury != null){
            this.mMercury.loadGLTexture(gl, this.mContext, R.drawable.mercury_map);
        } else if(mUran != null){
            this.mUran.loadGLTexture(gl, this.mContext, R.drawable.uranus_map);
        }

        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(CLEAR_RED, CLEAR_GREEN, CLEAR_BLUE, CLEAR_ALPHA);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    public float getmRotationAngle() {
        return mRotationAngle;
    }

    public void setmRotationAngle(float mRotationAngle) {
        this.mRotationAngle = mRotationAngle;
    }

    public float getThetaRotationAngle() {
        return thetaRotationAngle;
    }

    public void setThetaRotationAngle(float thetaRotationAngle) {
        this.thetaRotationAngle = thetaRotationAngle;
    }


    public Sphere getmMoon() {
        return mMoon;
    }

    public void setmMoon(Sphere mMoon) {
        this.mMoon = mMoon;
    }

    public Sphere getmEarth() {
        return mEarth;
    }

    public void setmEarth(Sphere mEarth) {
        this.mEarth = mEarth;
    }

    public Sphere getmSun() {
        return mSun;
    }

    public void setmSun(Sphere mSun) {
        this.mSun = mSun;
    }

    public Sphere getmJupiter() {
        return mJupiter;
    }

    public void setmJupiter(Sphere mJupiter) {
        this.mJupiter = mJupiter;
    }

    public Sphere getmNeptune() {
        return mNeptune;
    }

    public void setmNeptune(Sphere mNeptune) {
        this.mNeptune = mNeptune;
    }

    public Sphere getmVenus() {
        return mVenus;
    }

    public void setmVenus(Sphere mVenus) {
        this.mVenus = mVenus;
    }

    public Sphere getmMercury() {
        return mMercury;
    }

    public void setmMercury(Sphere mMercury) {
        this.mMercury = mMercury;
    }

    public Sphere getmMars() {
        return mMars;
    }

    public void setmMars(Sphere mMars) {
        this.mMars = mMars;
    }

    public Sphere getmSaturn() {
        return mSaturn;
    }

    public void setmSaturn(Sphere mSaturn) {
        this.mSaturn = mSaturn;
    }

    public Sphere getmUran() {
        return mUran;
    }

    public void setmUran(Sphere mUran) {
        this.mUran = mUran;
    }
}
