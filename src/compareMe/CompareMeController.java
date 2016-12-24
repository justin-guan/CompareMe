package compareMe;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class CompareMeController {
    public Label directory1;

    public void selectDirectory1(ActionEvent actionEvent) {
        directory1.setText(this.selectDirectory());
    }

    private String selectDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selected = directoryChooser.showDialog(null);
        return selected.getAbsolutePath();
    }
}
