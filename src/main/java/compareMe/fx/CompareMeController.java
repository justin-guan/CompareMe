package compareMe.fx;

import compareMe.Comparator;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.Files;

public class CompareMeController {
    public Button selectDir;
    public TextField directory;
    public CheckBox recursive;
    public CheckBox secondCheck;
    public CheckBox autoDelete;
    public ToggleGroup hashFunction;
    public VBox vbox;

    public void selectDirectory1(ActionEvent actionEvent) {
        directory.setText(this.selectDirectory());
    }

    private String selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selected = directoryChooser.showDialog(selectDir.getScene().getWindow());
        return (selected != null) ? selected.getAbsolutePath() : null;
    }

    public void beginCompare(ActionEvent actionEvent) {
        Task<ArrayList<ArrayList<File>>> task = new Task<ArrayList<ArrayList<File>>>() {
            @Override
            protected ArrayList<ArrayList<File>> call() throws Exception {
                vbox.setDisable(true);
                Comparator comparator = new Comparator();
                RadioButton hash = (RadioButton) hashFunction.getSelectedToggle();
                return comparator.compare(directory.getText(), hash.getText(), recursive.isSelected());
            }
        };

        new Thread(task).start();
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                vbox.setDisable(false);
                try {
                    ArrayList<ArrayList<File>> duplicates = task.getValue();
                    if (duplicates.size() > 0) {
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
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Something went wrong: " + e.toString());
                }
            }
        });
    }
}
