import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MacNotVerified implements Initializable {
    @FXML
    Text txt;
    @FXML
    Text txt2;
    @FXML
    Text txt3;
    public void exit(){
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            txt.setText(new Validation().getStoredMac());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            txt2.setText(new Validation().getMac());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        try {
            txt3.setText(String.valueOf(new Validation().isMacVerified()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
