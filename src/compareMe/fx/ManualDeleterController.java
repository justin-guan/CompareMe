package compareMe.fx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ManualDeleterController {
    public ImageView duplicateImage;
    public TableView<Path> duplicates;
    public Label currentNumDuplicate;
    public Button previous;
    public Button next;
    public Button cancelAll;
    public Button cancel;
    public Button delete;
    public TableColumn<Path, Boolean> deleteColumn;
    public TableColumn<Path, String> pathColumn;

    private ArrayList<ArrayList<File>> duplicatesList;
    private int currentDuplicate;

    @FXML
    public void initialize() {
        deleteColumn = new TableColumn<>("Delete?");
        pathColumn = new TableColumn<>("Path");
        duplicates.getColumns().addAll(deleteColumn, pathColumn);
        duplicates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        duplicates.setEditable(true);
    }

    public void initWindow(ArrayList<ArrayList<File>> duplicatesList) {
        this.duplicatesList = duplicatesList;
        this.currentDuplicate = 0;
        this.load(currentDuplicate);
    }

    public void cancelAll(ActionEvent actionEvent) {
        duplicatesList.clear();
        this.close();
    }

    public void cancel(ActionEvent actionEvent) {
        this.removeAndLoad();
    }

    public void delete(ActionEvent actionEvent) {
        for (Path p : duplicates.getItems()) {
            if (p.isChecked()) {
                try {
                    Files.delete(Paths.get(p.getPath()));
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Something went wrong: " + e.toString());
                }
            }
        }
        this.removeAndLoad();
    }

    public void previous(ActionEvent actionEvent) {
        this.load(--currentDuplicate);
    }

    public void next(ActionEvent actionEvent) {
        this.load(++currentDuplicate);
    }

    private void removeAndLoad() {
        duplicatesList.remove(currentDuplicate);
        if (currentDuplicate == this.duplicatesList.size()) { //At the end
            this.load(--currentDuplicate);
        } else {
            this.load(currentDuplicate);
        }
    }

    private void load(int index) {
        if (this.duplicatesList.size() == 0) {
            this.close();
            return;
        }
        duplicates.getItems().clear();
        currentNumDuplicate.setText((index + 1) + "/" + this.duplicatesList.size());

        if (index == 0) {
            previous.setDisable(true);
        } else {
            previous.setDisable(false);
        }
        if ((index + 1) == this.duplicatesList.size()) {
            next.setDisable(true);
        } else {
            next.setDisable(false);
        }
        Image image = new Image(this.duplicatesList.get(index).get(0).toURI().toString());
        this.duplicateImage.setImage(image);

        ObservableList<Path> data = FXCollections.observableArrayList();
        for (File f : this.duplicatesList.get(this.currentDuplicate)) {
            data.add(new Path(f.toString()));
        }
        duplicates.setItems(data);

        pathColumn.setCellValueFactory(new PropertyValueFactory<Path, String>("path"));
        deleteColumn.setCellValueFactory(new PropertyValueFactory<Path, Boolean>("checked"));
        deleteColumn.setCellFactory(CheckBoxTableCell.forTableColumn(deleteColumn));
        deleteColumn.setEditable(true);
    }

    private void close() {
        Stage stage = (Stage) cancelAll.getScene().getWindow();
        stage.close();
    }

    public static class Path {
        private StringProperty path;
        private BooleanProperty checked;

        public Path(String p) {
            this.path = new SimpleStringProperty(p);
            this.checked = new SimpleBooleanProperty(false);
        }

        public String getPath() {
            return path.get();
        }

        public boolean isChecked() {
            return checked.get();
        }

        public void setPath(String p) {
            this.path.set(p);
        }

        public void setChecked(boolean checked) {
            this.checked.set(checked);
        }

        public StringProperty pathProperty() {
            return this.path;
        }

        public BooleanProperty checkedProperty() {
            return this.checked;
        }
    }
}
