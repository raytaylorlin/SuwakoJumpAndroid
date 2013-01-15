package com.raytaylorlin.SuwakoJump;

import android.view.SurfaceHolder;
import com.raytaylorlin.SuwakoJump.View.CommonView;

public class GameViewThread extends TutorialThread {
    public GameViewThread(SurfaceHolder surfaceHolder, CommonView calledView) {//构造器
        super(surfaceHolder, calledView);
    }

    @Override
    protected void threadHandle() {

    }
}
