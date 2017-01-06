package compareMe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class ByteCompare {
    public static ArrayList<ArrayList<File>> secondCheck(ArrayList<ArrayList<File>> duplicates) throws IOException {
        for (int i = 0; i < duplicates.size(); i++) {
            ArrayList<File> sublist = duplicates.get(i);
            byte[] byteArray = Files.readAllBytes(sublist.get(0).toPath());
            ArrayList<File> append = new ArrayList<File>();

            for (int j = 1; j < sublist.size(); j++) {
                byte[] data = Files.readAllBytes(sublist.get(j).toPath());
                if (!Arrays.equals(byteArray, data)) {
                    append.add(sublist.get(j));
                    sublist.remove(j);
                }
            }

            if (append.size() > 0)
                duplicates.add(append);
        }
        return duplicates;
    }
}
