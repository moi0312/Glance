package moi0312.glance.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class IOUtility {
    private static final String TAG = IOUtility.class.getSimpleName();

    /**
     * copyFile
     * @param source
     * @param target
     * @throws Exception
     */
    public static boolean copy(File source, File target){
        try {
            FileInputStream inputStream = new FileInputStream(source);
            FileOutputStream outputStream = new FileOutputStream(target);

            // Transfer bytes from in to out
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
