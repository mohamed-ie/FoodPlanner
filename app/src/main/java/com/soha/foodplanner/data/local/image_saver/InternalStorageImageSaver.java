package com.soha.foodplanner.data.local.image_saver;

import android.graphics.Bitmap;

public interface InternalStorageImageSaver {
    String saveImage(Bitmap bitmap, String name);

    boolean deleteImage(String name);
}
