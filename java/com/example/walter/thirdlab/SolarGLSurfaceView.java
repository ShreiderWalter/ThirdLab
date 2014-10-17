package com.example.walter.thirdlab;

import android.content.Context;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


/**
 * Created by walter on 04.10.14.
 */
public class SolarGLSurfaceView extends GLSurfaceView {

    private final GlRenderer mRenderer;

    public SolarGLSurfaceView(Context context) {
        super(context);

        mRenderer = new GlRenderer(context);
        setRenderer(mRenderer);

        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private final float Z_SCALE_FACTOR = 100.0f / 320;
    private float mPreviousX;
    private float mPreviousY;
    private float mPreviousZ;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        requestRender();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                if (y < getHeight() / 2) {
                    dx = dx * -1 ;
                }

                if (x > getWidth() / 2) {
                    //dy = dy * -1 ;
                }
                mRenderer.setmRotationAngle((mRenderer.getmRotationAngle() +
                                             ((dx ) * TOUCH_SCALE_FACTOR)));
                mRenderer.setThetaRotationAngle(mRenderer.getThetaRotationAngle() + dy * Z_SCALE_FACTOR );
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    public GlRenderer getmRenderer() {
        return mRenderer;
    }
}