package compareMe.fx;

import compareMe.Comparator;
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Completed.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Compare Me - Completed");
                stage.setScene(new Scene(root, 300, 100));
                stage.show();
            } else if (duplicates.size() > 0) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ManualDeleter.fxml"));
                Parent root = fxmlLoader.load();
                ManualDeleterController controller = fxmlLoader.<ManualDeleterController>getController();
                controller.initWindow(duplicates);
                Stage stage = new Stage();
                stage.setTitle("Compare Me - Manual Deleter");
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Completed.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Compare Me - Completed");
                stage.setScene(new Scene(root, 300, 100));
                stage.show();
            }
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("Something went wrong: " + e.toString());
        }
    }
}
