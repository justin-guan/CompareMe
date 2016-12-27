package compareMe;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compare {
    public static List<File> compare(String dir1, String dir2, String algorithm) throws NoSuchAlgorithmException {
        HashMap<String, String> files = new HashMap<String, String>();
        //ArrayList<> collisions = new ArrayList<Pair<String, String>>();
        File directory = new File(dir1);
        File[] directoryList = directory.listFiles();
        if (directoryList != null) {
            for (File f : directoryList) {
                String hash = HashGenerator.generateHash(algorithm, f.getAbsolutePath());
                if (files.containsKey(hash)) { // Collision


                } else { // No Collision

                }
            }
        } else {
            return;
        }
    }
}
