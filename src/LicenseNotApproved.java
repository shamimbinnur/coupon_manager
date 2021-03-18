import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LicenseNotApproved implements Initializable {
    @FXML
    TextField nameField;
    @FXML
    TextField emailField;
    @FXML
    TextField licenseField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    Button button = new Button();

    public void submit(ActionEvent e) throws IOException {
        String name = nameField.getText();
        String email = emailField.getText();
        String license = licenseField.getText();
        String mac = new Validation().getMac();
        String randCode = genRandom();
        String rcv = "thaminit00@gmail.com";
        String[] to = { rcv };
        String msg = "From : " + name +"\n" + "Email: "+email+"\n" +"License code : "+license+"\n"+ "Mac address : "+mac+"\n"+ "Confirmation code: "+randCode;
        new Email().sendFromGMail("iamfromapp@gmail.com","AllShouldBeSafe@@",to,"Requesting confirmation code",msg);
        System.out.println("Rand: "+randCode);
        Validation validation = new Validation();
        validation.makeItSubmitted();
        validation.storeTheRand(randCode);

        goToConfirmation(e);
        nameField.setText("");
        emailField.setText("");
        licenseField.setText("");
    }

    public void goToConfirmation(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ConfirmationWindow.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();
    }

    public String genRandom(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        System.out.println(generatedString);
        return  generatedString;
    }


}
