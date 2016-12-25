package compareMe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator {
    private MessageDigest md;

    public HashGenerator(String algorithm) throws NoSuchAlgorithmException {
        md = MessageDigest.getInstance(algorithm);
    }

    /**
     * Returns a hash code based on the algorithm specified for the HashGenerator. The path argument specifies
     * what file to generate the hash for.
     *
     * This method will return null on a failure to generate the hash code. Failures include file not found
     * or invalid arguments.
     * @param path An absolute path to a file
     * @return     A string representation of the hash code based on the algorithm specified by HashGenerator
     */
    public String generateHash(String path) {
        FileInputStream file;
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            return null;
        }
        byte[] block = new byte[1024];
        int length = 0;
        try {
            while ((length = file.read(block)) != -1) {
                md.update(block, 0, length);
            }
            return this.toHexString(md.digest());
        } catch (IOException e) {
            return null;
        }
    }

    private String toHexString(byte[] array) {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < array.length; i++) {
            s.append(Integer.toHexString(0xFF & array[i]));
        }
        return s.toString();
    }

}
