package com.soha.foodplanner.data.local.image_saver;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

public class InternalStorageImageSaverImpl implements InternalStorageImageSaver {
    private final File destinationFile;

    public InternalStorageImageSaverImpl(File destinationFile) {
        this.destinationFile = destinationFile;
    }

    @Override
    public String saveImage(Bitmap bitmap, String name) {
        File file = new File(destinationFile, name + ".jpg");
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

    @Override
    public boolean deleteImage(String name) {
        File file = new File(destinationFile, name + ".jpg");
        return file.delete();
    }

}
