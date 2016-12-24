package compareMe;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class CompareMeController {
    /*
    TODO: Change Label to an uneditable textbox
     */
    public Button selectDir1;
    public Button selectDir2;
    public Label directory1;
    public Label directory2;

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
}
