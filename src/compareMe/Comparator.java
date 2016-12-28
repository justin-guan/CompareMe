package compareMe;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class Comparator {
    HashMap<String, ArrayList<String>> files;

    Comparator() {
        files = new HashMap<String, ArrayList<String>>();
    }

    public ArrayList<ArrayList<File>> compare(String dir, String algorithm) throws NoSuchAlgorithmException {
        this.updateHashMap(dir, algorithm);
        return this.getCollisions(files);
    }

    private void updateHashMap(String dir, String algorithm) throws NoSuchAlgorithmException {
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
                } else {
                    this.updateHashMap(f.getAbsolutePath(), algorithm);
                }
            }
        }
    }

    private ArrayList<ArrayList<File>> getCollisions(HashMap<String, ArrayList<String>> files) {
        ArrayList<ArrayList<File>> collisions = new ArrayList<ArrayList<File>>();
        for (ArrayList<String> a : files.values()) {
            if (a.size() > 1) {
                ArrayList<File> f = new ArrayList<File>();
                for (String file : a) {
                    f.add(new File(file));
                }
                collisions.add(f);
            }
        }
        return collisions;
    }
}
