package compareMe;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Compare {
    public static List<File> compare(String dir, String algorithm) throws NoSuchAlgorithmException {
        HashMap<String, ArrayList<String>> files = new HashMap<String, ArrayList<String>>();
        File directory = new File(dir);
        File[] directoryList = directory.listFiles();
        if (directoryList != null) {
            for (File f : directoryList) {
                if (f.isFile()) {
                    String hash = HashGenerator.generateHash(algorithm, f.getAbsolutePath());
                    if (!files.containsKey(hash)) {
                        ArrayList<String> file = new ArrayList<String>();
                        file.add(f.getAbsolutePath());
                        files.put(hash, file);
                    } else { // Collision
                        files.get(hash).add(f.getAbsolutePath());
                    }
                }
            }
        } else { // Not a directory or no files
            return null;
        }

        for (ArrayList<String> a : files.values()) {
            if (a.size() > 1) {
                System.out.println(a.toString());
            }
        }
        return null;
    }
}
