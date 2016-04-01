package com.moi0312.glance;

import java.io.File;

public class IOUtility {
    private static final String TAG = IOUtility.class.getSimpleName();

    public static boolean delete(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delete(new File(dir, children[i]));
//                Log.v(TAG, "ssss delete: "+children[i]);
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()){
            return dir.delete();
        }else {
            return false;
        }
    }
}
