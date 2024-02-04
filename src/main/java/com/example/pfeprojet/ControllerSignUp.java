package com.example.pfeprojet;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class ControllerSignUp {

    @FXML
    private Button ExitButton;
    @FXML
    private Button NextButton;
    private static final double ENLARGE_FACTOR = 1.1;





    @FXML
    private TextField activityTextField;

    @FXML
    private TextField companyNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField faxnumberTextfield;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField phonenumberTextfield;

    @FXML
    private PasswordField pwdConfirmationTextField;

    @FXML
    private PasswordField pwdTextField;

    @FXML
    private TextField taxIdTextField;

    public void onMouseEntered(javafx.scene.input.MouseEvent mouseEvent) {
        ExitButton.setStyle("-fx-background-color: #FF4545; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(ExitButton);
    }

    public void onMouseExited(javafx.scene.input.MouseEvent mouseEvent) {
        ExitButton.setStyle("-fx-background-color:  white; -fx-background-radius: 5em;");
        restoreButtonSize(ExitButton);
    }


    public void onMouseEntered2(javafx.scene.input.MouseEvent mouseEvent) {
        NextButton.setStyle("-fx-background-color:  #59A8A4; -fx-text-fill: white; -fx-background-radius: 5em;");
        enlargeButton(NextButton);
    }

    public void onMouseExited2(javafx.scene.input.MouseEvent mouseEvent) {
        NextButton.setStyle("-fx-background-color:  white; -fx-background-radius: 5em;");
        restoreButtonSize(NextButton);
    }


    private void enlargeButton(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
        scaleTransition.setToX(ENLARGE_FACTOR);
        scaleTransition.setToY(ENLARGE_FACTOR);
        scaleTransition.play();
    }

    private void restoreButtonSize(Button button) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(120), button);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();
    }


    //The code for Button "Suivant" : it creates a NEW Company in the database
    @FXML
    private void addCompany()  {
        String mail = emailTextField.getText();
        String pwd = pwdTextField.getText();
        String companyName = companyNameTextField.getText();
        String location = locationTextField.getText();
        String phoneNumber = phonenumberTextfield.getText();
        String faxNumber = faxnumberTextfield.getText();
        String activity = activityTextField.getText();
        String taxId = taxIdTextField.getText();


        if(mail.isEmpty() || pwd.isEmpty() || companyName.isEmpty() || location.isEmpty() || phoneNumber.isEmpty() || faxNumber.isEmpty() || activity.isEmpty() || taxId.isEmpty()){
            showAlert2("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            String insertQuery = "INSERT INTO `companyinfos`(`CompanyName`, `mailAdress`, " +
                    "`password`, `location`, `phoneNumber`, `faxNumber`, `activity`, `taxId`)" +
                    " VALUES ('"+companyName+"','"+mail+"','"+pwd+"','"+location+"','"+phoneNumber+"'," +
                    "'"+faxNumber+"','"+activity+"','"+taxId+"')";

            Connexion c = new Connexion("jdbc:mysql://localhost:3306/Companies?user=root","com.mysql.cj.jdbc.Driver");


            if(c.miseAjour(insertQuery)){
                showAlert("Company added", "Succès ! Votre entreprise a maintenant un compte actif.","/images/checkSuccess.png");
            }else{
                showAlert("Échec de la création du compte.","Assurez-vous que toutes les informations sont correctes","/images/checkFailed.png");
            }
        }


    }

    //MessageWindow when the user doesnt Enter all the necessary informations
    private void showAlert2(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Create a Font object with the desired font family and size
        Font customFont = Font.font("Gill Sans MT Condensed", 18); // Replace "Your Font Name" with the desired font name

        // Access the Label within the Alert's content area and apply the custom font
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        contentLabel.setFont(customFont);

        alert.showAndWait();
    }



    //MessageWindow when the Company is added succcesfully or when an error is occured during the insert
    private void showAlert(String title, String content, String icon) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);

        // Create a Font object with the desired font family and size
        Font customFont = Font.font("Gill Sans MT Condensed", 20); // Replace "Your Font Name" with the desired font name

        // Access the Label within the Alert's content area and apply the custom font
        Label contentLabel = (Label) alert.getDialogPane().lookup(".content.label");
        contentLabel.setFont(customFont);

        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(icon)));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(55); // Adjust the height as needed
        imageView.setFitWidth(55);  // Adjust the width as needed

        alert.setGraphic(imageView);

        alert.showAndWait();
    }

    //Initialize method is being called when the fxml file is loaded, it adds listeners to the text field we are checking
    public void initialize() {
        // Add listeners to the textProperty of the respective TextFields with a slight delay
        DelayedTextChangeListener emailListener = new DelayedTextChangeListener();
        emailTextField.textProperty().addListener(emailListener);

        DelayedTextChangeListener faxListener = new DelayedTextChangeListener();
        faxnumberTextfield.textProperty().addListener(faxListener);

        DelayedTextChangeListener phoneListener = new DelayedTextChangeListener();
        phonenumberTextfield.textProperty().addListener(phoneListener);
    }


    // When the pause (3 scnds) finishes, it invokes the validate method.
    private class DelayedTextChangeListener implements ChangeListener<String> {
        private final PauseTransition pause = new PauseTransition(Duration.seconds(3)); // 3 second delay after FINISHING TYPING

        DelayedTextChangeListener() {
            pause.setOnFinished(event -> validate());
        }

        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            pause.playFromStart();
        }

        //calls "validateFaxNumber" with the FaxNumber typed in the text field
        //calls "validatePhoneNumber" with the PhoneNumber typed in the text field
        //calls "validateEmail" with the EmailAddress typed in the text field
        private void validate() {
            validateEmail(emailTextField.getText());
            validatePhoneNumber(phonenumberTextfield.getText());
            validateFaxNumber(faxnumberTextfield.getText());
        }
    }


    //it verifies if the PhoneNumber typed is correct ( its form )
        //if the method "isValidPhoneNumber" returns false the program will execute the code between ELSE IF brackets
    private void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.trim().isEmpty()) { //TRIM RETURN : a string whose value is this string, with all leading and trailing space removed
            phonenumberTextfield.clear();
        } else if (!isValidPhoneNumber(phoneNumber)) {
            phonenumberTextfield.setStyle("-fx-prompt-text-fill: red;");
            phonenumberTextfield.setPromptText("Numéro de telephone non valid !!!");
            phonenumberTextfield.clear();
        }
    }
    private void validateFaxNumber(String faxNumber) {
        if (faxNumber.trim().isEmpty()) {
            faxnumberTextfield.clear();
        } else if (!isValidPhoneNumber(faxNumber)) {
            faxnumberTextfield.setStyle("-fx-prompt-text-fill: red;");
            faxnumberTextfield.setPromptText("Numéro de fax non valid !!!");
            faxnumberTextfield.clear();
        }
    }

    //returns true if the phoneNumber passed as an argument matches the phoneRegex ( 10 digits )
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Basic validation: 10 digits
        return phoneNumber.matches("^\\d{10}$");
    }


    private void validateEmail(String email) {
        if (email.trim().isEmpty()) {
            emailTextField.clear();
        } else if (!isValidEmail(email)) {
            emailTextField.setStyle("-fx-prompt-text-fill: red;");
            emailTextField.setPromptText("Adresse email non valid !!!");
            emailTextField.clear();
        }
    }

    // Basic email validation using a regular expression
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
    }



    //Going back to Login Window
    public void switchToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FstWindow.fxml")));

        // Create a new stage for scene1
        Stage newStage = new Stage();
        Scene newScene = new Scene(root);

        // Load the icon for scene1
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/logo.png")));
        newStage.getIcons().add(icon);

        // Set the title for scene1
        newStage.setResizable(false);
        newStage.setTitle("FacturEase");

        // Set the scene and show the stage
        newStage.setScene(newScene);
        newStage.show();

        // Close the current (scene2) stage
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
