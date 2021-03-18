import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmationWindow implements Initializable {
    @FXML
    TextField confirmCodeField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void submit(ActionEvent e) throws IOException {
        Validation validation = new Validation();
        String storedCode = validation.getTTheStoredRand();
        System.out.println(storedCode);
        if(storedCode.equals(confirmCodeField.getText())){
            validation.makeLicenseApproved();
            validation.storeMac();
            goToFinal(e);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation code");
            alert.setHeaderText("Invalid code");
            alert.setContentText("The code you have entered is invalid, Please try again.");
            alert.showAndWait();
        }
    }

    public void goBackToStart(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LicenseNotApproved.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public void goToFinal(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Final.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

}
