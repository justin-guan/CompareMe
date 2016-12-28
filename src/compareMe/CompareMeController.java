package compareMe;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class CompareMeController {
    public Button selectDir1;
    public Button selectDir2;
    public TextField directory1;
    public TextField directory2;

    public void selectDirectory1(ActionEvent actionEvent) {
        directory1.setText(this.selectDirectory());
    }

    public void selectDirectory2(ActionEvent actionEvent) {
        directory2.setText(this.selectDirectory());
    }

    private String selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selected = directoryChooser.showDialog(selectDir1.getScene().getWindow());
        return (selected != null) ? selected.getAbsolutePath() : null;
    }

    public void beginCompare(ActionEvent actionEvent) {
        try {
            Comparator comparator = new Comparator();
            ArrayList<ArrayList<File>> duplicates = comparator.compare(directory1.getText(), "SHA-256");
            System.out.println(duplicates);
            System.out.println("done");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
