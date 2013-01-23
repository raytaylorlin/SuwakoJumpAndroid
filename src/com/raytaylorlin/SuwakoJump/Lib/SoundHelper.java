package com.raytaylorlin.SuwakoJump.Lib;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundHelper {
    public static HashMap<String, Integer> SoundMap = new HashMap<String, Integer>();
    private static SoundPool sp = new SoundPool(9, AudioManager.STREAM_MUSIC, 100);
    private static MediaPlayer mp = null;

    private static float volumn = 1.0f;

    public static int load(Context context, int rId) {
        return sp.load(context, rId, 1);
    }

    public static void play(Context context, int resource) {
        mp = MediaPlayer.create(context, resource);
        mp.setLooping(true);
        mp.start();
    }

    public static void play(int soundId, boolean isLoop) {
        sp.play(soundId, volumn, volumn, 0, 0, isLoop ? 99 : 1);
    }

    public static void pause() {
        mp.pause();
    }

    public static void resume(){
        mp.start();
    }

    public static void stop() {
        mp.stop();
    }

    public static boolean turnVolumn() {
        volumn = (volumn == 1.0 ? 0 : 1.0f);
        return volumn > 0.5;
    }
}
