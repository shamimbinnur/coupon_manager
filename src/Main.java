// 	AUTHOR:		SHAMIM BIN NUR
// 	GITHUB:		https://github.com/shamimbinnur
// 	LINKEDIN:	https://www.linkedin.com/in/shamimbinnur
// 	MAIL:		iamshamimbn@gmail.com
// 	SITE:		www.shamimbinnur.me


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends Application implements Initializable {

    @FXML
    TextField quantityField;
    @FXML
    TextField discountField;
    @FXML
    TextField couponField;
    @FXML
    TextField nameField;
    @FXML
    DatePicker dateField;
    @FXML
    Text dateText;
    @FXML
    Text exText;
    @FXML
    Text customerNameText;
    @FXML
    Text statusText;
    @FXML
    Text validityText;
    @FXML
    Text discountText;

    @FXML
    Text stockText;
    @FXML
    Text bookedText;
    @FXML
    Text claimedText;

    @FXML
    Rectangle validityRect;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Validation validation = new Validation();
        if (validation.isLicenseVerified()){
            if(validation.isMacVerified()){
                Parent root = FXMLLoader.load(getClass().getResource("Final.fxml"));
                primaryStage.setTitle("Coupon Manager");
                primaryStage.setScene(new Scene(root));
                primaryStage.centerOnScreen();
                primaryStage.show();
            }
            else if(!validation.isMacVerified()) {
                Parent root = FXMLLoader.load(getClass().getResource("MacNotVerified.fxml"));
                primaryStage.setTitle("Verification");
                primaryStage.setScene(new Scene(root));
                primaryStage.centerOnScreen();
                primaryStage.show();
            }

        }
        else if(!validation.isLicenseVerified()){
            if (validation.isSubmitted()){
                Parent root = FXMLLoader.load(getClass().getResource("ConfirmationWindow.fxml"));
                primaryStage.setTitle("Confirmation Window");
                primaryStage.setScene(new Scene(root));
                primaryStage.centerOnScreen();
                primaryStage.show();
            }
            else if(!validation.isSubmitted()){
                Parent root = FXMLLoader.load(getClass().getResource("LicenseNotApproved.fxml"));
                primaryStage.setTitle("Registration");
                primaryStage.setScene(new Scene(root));
                primaryStage.centerOnScreen();
                primaryStage.show();
            }
        }



    }

    public void generateCoupon(){
        if (!quantityField.getText().isEmpty() && !discountField.getText().isEmpty()){
            double discount = Double.parseDouble(discountField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            int i;
            String couponID;
            System.out.println(quantity);
            System.out.println(discount);
            String expire = "NA";

            if (dateField.getValue() != null){
                expire = dateField.getValue().toString();
            }
            for (i=1; i<=quantity; i++){
                Database database = new Database();
                Random rand = new Random();
                couponID = String.valueOf(    rand.nextInt(999999999)+100000000   );
                System.out.println(couponID);
                if (database.is_Unique(couponID) == 1){
                    System.out.println(database.writeToDatabase(couponID,discount,expire));

                }
                else if(database.is_Unique(couponID) == 0){
                    i--;
                    System.out.println("Duplicate found, Trying again");
                }
            }
            displayInit();
            reset();
            System.out.println(quantity +" Coupon generated");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText(quantity+" coupon has been generated !");
            alert.showAndWait();

        }
    }

    public void check_validity(){
        if (!couponField.getText().isEmpty()){
            Database database = new Database();
            String coupon = couponField.getText();
            if (database.checkValidity(coupon)){

                validityText.setText("Valid");
                customerNameText.setText(database.getInfo(coupon)[0]);
                discountText.setText(database.getInfo(coupon)[1]);
                dateText.setText(database.getInfo(coupon)[2]);
                statusText.setText(database.getInfo(coupon)[3]);
                exText.setText(database.getInfo(coupon)[4]);
                validityRect.setStyle("-fx-fill: #6aff7e");
            }
            else {
                validityText.setText("Invalid");
                customerNameText.setText("");
                discountText.setText("");
                dateText.setText("");
                statusText.setText("");
                exText.setText("");
                validityRect.setStyle("-fx-fill: #f86d6d");
            }
        }
    }

    public void claim(){
        if (!couponField.getText().isEmpty()){
            Database database = new Database();
            if (database.isBooked(couponField.getText()) && !database.isClaimed(couponField.getText())){
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String dateAuto = formatter.format(date);
                boolean done = database.claim(couponField.getText(),dateAuto);
                reset();
                if (done){
                    displayInit();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("Coupon has been claimed !");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Problem occurred");
                    alert.setHeaderText("Something went wrong !");
                    alert.setContentText(null);
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Coupon claiming failed");
                alert.setHeaderText("Coupon is not booked or claimed already !");
                alert.setContentText("Please book a coupon first or try again with another coupon");
                alert.showAndWait();
            }
        }
    }

    public void reset(){
        validityText.setText("");
        customerNameText.setText("");
        discountText.setText("");
        dateText.setText("");
        statusText.setText("");
        couponField.setText("");
        validityRect.setStyle("-fx-fill: #ffffff");
        dateField.setValue(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayInit();
    }
    public void stockInit(){
        Database database = new Database();
        stockText.setText(String.valueOf(database.getStockQ()));
    }
    public void displayInit(){
        Database database = new Database();
        claimedText.setText(String.valueOf(database.getClaimedQ()));
        bookedText.setText(String.valueOf(database.getBookedQ()));
        stockText.setText(String.valueOf(database.getStockQ()));
    }

    public void delete(){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Delete coupon");
        dialog.setHeaderText("Delete coupon code !");
        dialog.setContentText("Please enter the no of coupon:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String name = result.get();
            Database database = new Database();
            database.del(name);
        }
    }
    public void book(){
        if (!couponField.getText().isEmpty()){
            Database database = new Database();
            if (!database.isClaimed(couponField.getText()) && !database.isBooked(couponField.getText())){
                if (database.isDateNA(couponField.getText())){
                    if (dateField.getValue() != null){
                        boolean done = database.book(nameField.getText(),couponField.getText(),dateField.getValue().toString());
                        reset();
                        if (done){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("Booked !");
                            alert.setContentText("Coupon has been booked successfully !");
                            alert.showAndWait();
                        }

                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Coupon booking failed");
                        alert.setHeaderText("Expire date is not assigned !");
                        alert.setContentText("Please enter the expire date.");
                        alert.showAndWait();
                    }
                }
                else if (!database.isDateNA(couponField.getText())){
                    if (dateField.getValue() == null){
                        boolean done = database.bookWithouEx(nameField.getText(),couponField.getText());
                        reset();
                        if(done){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText("Booked");
                            alert.setContentText("Coupon has been booked successfully !");
                            alert.showAndWait();
                        }

                    }
                    else {
                        boolean done = database.book(nameField.getText(),couponField.getText(),dateField.getValue().toString());
                        reset();
                        if (done){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("Coupon has been booked successfully !");
                            alert.showAndWait();
                        }
                    }
                }

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Coupon booking failed");
                alert.setHeaderText("Coupon is already booked or claimed !");
                alert.setContentText("Please try again with another coupon code");
                alert.showAndWait();
            }
        }
    }
}
