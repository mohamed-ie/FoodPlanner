package com.soha.foodplanner.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;

import com.soha.foodplanner.common.Constants;

import java.io.File;
import java.io.FileOutputStream;

public class InternalStorageUtils {
    @SuppressWarnings("FieldCanBeLocal")
    private static final String INTERNAL_STORAGE_IMAGE_DIRECTORY = "images";

    public static String saveImage(Context context, Bitmap bitmap, String name) {
        File directory = context.getDir(INTERNAL_STORAGE_IMAGE_DIRECTORY, Context.MODE_PRIVATE);
        File file = new File(directory, name + ".jpg");
        String uri = file.getAbsolutePath();
        if (!file.exists()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        return uri;
    }

}
