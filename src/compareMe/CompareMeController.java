package compareMe;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class CompareMeController {
    public Button selectDir;
    public TextField directory;
    public CheckBox recursive;
    public CheckBox secondCheck;

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
            ArrayList<ArrayList<File>> duplicates = comparator.compare(directory.getText(), "SHA-256", recursive.isSelected());

            Parent root = FXMLLoader.load(getClass().getResource("compareMeComplete.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Compare Me");
            stage.setScene(new Scene(root, 300, 275));
            stage.show();

            System.out.println(duplicates);
            System.out.println("done");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
