package com.raytaylorlin.SuwakoJump.Lib;

import android.content.Context;
import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileHelper {
    private Context context;

    public FileHelper(Context context) {
        this.context = context;
    }

    //写数据
    public void writeFile(String fileName, String content) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName,
                    Context.MODE_APPEND);
            byte[] bytes = content.getBytes();
            fos.write(bytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读数据
    public String readFile(String fileName) {
        String res = "";
        try {
            FileInputStream fis = this.context.openFileInput(fileName);
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
