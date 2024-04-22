package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Connexion;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerSignUp extends mouseEvents{



    @FXML
    private Button ExitButton;
    @FXML
    private Button NextButton;


    private static String cmp;
    public static String getCmp(){return cmp;}
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
    private PasswordField pwdConfirmationTextField;

    @FXML
    private PasswordField pwdTextField;

    @FXML
    private TextField taxIdTextField;


    public static String pwdEntreprise ;


    public void onMouseEntered(javafx.scene.input.MouseEvent mouseEvent) {
         onMouseEntered(mouseEvent, ExitButton);
    }

    public void onMouseExited(javafx.scene.input.MouseEvent mouseEvent) {
         onMouseExited(mouseEvent, ExitButton);
    }


    public void onMouseEntered2(javafx.scene.input.MouseEvent mouseEvent) { onMouseEntered2(mouseEvent, NextButton);}

    public void onMouseExited2(javafx.scene.input.MouseEvent mouseEvent) {
         onMouseExited2(mouseEvent, NextButton);
    }



    //The code for Button "Suivant" : it creates a NEW Company in the database
    @FXML
    private void addCompany(ActionEvent event) throws IOException, SQLException {

        String mail = emailTextField.getText();
        String pwd = pwdTextField.getText();
        pwdEntreprise = pwd ;
        String hashpwd = PassworManager.hashPassword(pwd);
        String companyName =  companyNameTextField.getText();
        cmp = companyName.replaceAll("\\s+", "_");
        String location = locationTextField.getText();
        String faxNumber = faxnumberTextfield.getText();
        String activity = activityTextField.getText();
        String taxId = taxIdTextField.getText();

        Alerts sa = new Alerts();
        if (mail.isEmpty() || pwd.isEmpty() || companyName.isEmpty() || location.isEmpty() || faxNumber.isEmpty() || activity.isEmpty() || taxId.isEmpty()) {
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        } else {
            if (!pwdTextField.getText().equals(pwdConfirmationTextField.getText())) {
                sa.showWarning("Attention", "Le mot de passe entré et sa confirmation ne correspondent pas. Veuillez réécrire le mot de passe.");
            } else {
                Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");
                ResultSet rs = c.lire("SELECT * FROM infosEntreprises WHERE AdresseMail = ?", mail);
                ResultSet rs1 = c.lire("SELECT * FROM infosEntreprises WHERE nomEntreprise = ?", companyName);
                if (rs.next()) {
                    sa.showWarning("Attention", "L'email de l'entreprise fournit existe déjà dans notre application.");
                    rs.close();
                } else if(rs1.next()){
                    sa.showWarning("Attention", "Le nom de l'entreprise fournit existe déjà dans notre application.");
                    rs1.close();
                }else{
                    String insertQuery = "INSERT INTO `infosEntreprises`(`nomEntreprise`, `AdresseMail`, `MotdePasse`, `Localisation`, `NumeroDeFax`, `SecteurDactivite`, `IdentificationFiscale`)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?)";

                    if (c.miseAjour(insertQuery, cmp, mail, hashpwd, location, faxNumber, activity, taxId) && c.createDatabase(cmp)) {
                        sa.showAlert("Creation avec succes", "Succès ! Votre entreprise a maintenant un compte actif, et une Base de données sous le nom : '" + cmp.trim() + "'", "/images/checked.png");
                        ChangingWindows ch = new ChangingWindows();
                        ch.switchWindow(event, "DbCreation.fxml");
                    } else {
                        sa.showAlert("Échec de la création du compte.", "Assurez-vous que toutes les informations sont correctes", "/images/checkFailed.png");
                    }
                }
                c.closeResources();
            }
        }
    }




    //Initialize method is being called when the fxml file is loaded, it adds listeners to the text field we are checking
    public void initialize() {
        // Add listeners to the textProperty of the respective TextFields with a slight delay
        DelayedTextChangeListener emailListener = new DelayedTextChangeListener();
        emailTextField.textProperty().addListener(emailListener);

        DelayedTextChangeListener faxListener = new DelayedTextChangeListener();
        faxnumberTextfield.textProperty().addListener(faxListener);

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
            validateFaxNumber(faxnumberTextfield.getText());
        }
    }


    //it verifies if the PhoneNumber typed is correct ( its form )
    //if the method "isValidPhoneNumber" returns false the program will execute the code between ELSE IF brackets
    private void validateFaxNumber(String faxNumber) {
        if (faxNumber.trim().isEmpty()) { //TRIM RETURN : a string whose value is this string, with all leading and trailing spaces removed
            faxnumberTextfield.clear();
        } else if (!isValidFaxNumber(faxNumber)) {
            faxnumberTextfield.setStyle("-fx-prompt-text-fill: red;");
            faxnumberTextfield.setPromptText("Numéro de fax non valid !!!");
            faxnumberTextfield.clear();
        }
    }

    //returns true if the phoneNumber passed as an argument matches the phoneRegex ( 10 digits )
    private boolean isValidFaxNumber(String phoneNumber) {
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
        ChangingWindows ch = new ChangingWindows();
        ch.switchWindow(event, "FstWindow.fxml");
    }
}
