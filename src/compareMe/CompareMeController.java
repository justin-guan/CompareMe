package compareMe;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.nio.file.Files;

public class CompareMeController {
    public Button selectDir;
    public TextField directory;
    public CheckBox recursive;
    public CheckBox secondCheck;
    public CheckBox autoDelete;
    public ToggleGroup hashFunction;

    public void selectDirectory1(ActionEvent actionEvent) {
        directory.setText(this.selectDirectory());
    }

    private String selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selected = directoryChooser.showDialog(selectDir.getScene().getWindow());
        return (selected != null) ? selected.getAbsolutePath() : null;
    }

    public void beginCompare(ActionEvent actionEvent) {
        try {
            Comparator comparator = new Comparator();
            RadioButton hash = (RadioButton) hashFunction.getSelectedToggle();
            ArrayList<ArrayList<File>> duplicates = comparator.compare(directory.getText(), hash.getText(), recursive.isSelected());

            if (autoDelete.isSelected() && duplicates.size() > 0) {
                for (ArrayList<File> listOfFiles : duplicates) {
                    boolean first = true;
                    for (File f : listOfFiles) {
                        if (first) {
                            first = false;
                            continue;
                        }
                        Files.delete(f.toPath()); // Use package java.nio.file to delete due to better error checking
                    }
                }
            } else {
                Parent root = FXMLLoader.load(getClass().getResource("compareMeComplete.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Compare Me");
                stage.setScene(new Scene(root, 300, 275));
                stage.show();
            }

            System.out.println(duplicates);
            System.out.println("done");
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("Something went wrong: " + e.toString());
        }
    }
}
