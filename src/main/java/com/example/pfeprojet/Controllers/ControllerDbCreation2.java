package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.Connexion;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class ControllerDbCreation2 {

    String dbName = ControllerSignUp.getCmp();
    //String dbName = "Entreprises";
    @FXML
    private Button ExitButton;
    @FXML
    private Button NextButton;

    @FXML
    private TextField TelTextfield;

    @FXML
    private TextField adrTextfield;

    @FXML
    private TextField cinTextfield;

    @FXML
    private TextField cnfPwdTextfield;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField nomTextfield;

    @FXML
    private TextField prenomTextfield;

    @FXML
    private TextField pwdTextfield;

    public void initialize() {
        // Add listeners to the textProperty of the respective TextFields with a slight delay
        ControllerDbCreation2.DelayedTextChangeListener emailListener = new ControllerDbCreation2.DelayedTextChangeListener();
        emailTextfield.textProperty().addListener(emailListener);

        ControllerDbCreation2.DelayedTextChangeListener faxListener = new ControllerDbCreation2.DelayedTextChangeListener();
        TelTextfield.textProperty().addListener(faxListener);

    }
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
            validateEmail(emailTextfield.getText());
            validateFaxNumber(TelTextfield.getText());
        }
    }
    private void validateFaxNumber(String faxNumber) {
        if (faxNumber.trim().isEmpty()) { //TRIM RETURN : a string whose value is this string, with all leading and trailing spaces removed
            TelTextfield.clear();
        } else if (!isValidNumber(faxNumber)) {
            TelTextfield.setStyle("-fx-prompt-text-fill: red;");
            TelTextfield.setPromptText("Numéro de fax non valid !!!");
            TelTextfield.clear();
        }
    }
    private boolean isValidNumber(String phoneNumber) {
        // Basic validation: 10 digits
        return phoneNumber.matches("^\\d{10}$");
    }

    private void validateEmail(String email) {
        if (email.trim().isEmpty()) {
            emailTextfield.clear();
        } else if (!isValidEmail(email)) {
            emailTextfield.setStyle("-fx-prompt-text-fill: red;");
            emailTextfield.setPromptText("Adresse email non valid !!!");
            emailTextfield.clear();
        }
    }
    private boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");
    }

    mouseEvents ms = new mouseEvents();

    public void onMouseEntered(javafx.scene.input.MouseEvent mouseEvent) {
        ms.onMouseEntered(mouseEvent, ExitButton);
    }

    public void onMouseExited(javafx.scene.input.MouseEvent mouseEvent) {
        ms.onMouseExited(mouseEvent, ExitButton);
    }


    public void onMouseEntered2(javafx.scene.input.MouseEvent mouseEvent) {ms.onMouseEntered2(mouseEvent, NextButton);}

    public void onMouseExited2(javafx.scene.input.MouseEvent mouseEvent) {
        ms.onMouseExited2(mouseEvent, NextButton);
    }
    public void AnnulerBtn() {
        Alerts sa = new Alerts();

        boolean confirmed = sa.showConfirmationAlert("Confirmation", "Êtes-vous sûr de vouloir quitter l'application et supprimer votre base de données '"+dbName+"' ?");
        if (confirmed) {
            System.out.println("User clicked 'Yes'");
            Connexion c = new Connexion("jdbc:mysql://localhost:3306/" +dbName+ "?user=root");
            Connexion c1 = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

            if(c.dropDatabase(dbName)&& c1.miseAjour("Delete from infosEntreprises where nomEntreprise = ?", dbName)){
                sa.showAlert("Suppression de la base de donnée","La suppression de votre base de données '"+dbName+"' a été effectuée avec succès.","/images/checked.png");
                Platform.exit();
            }else{
                sa.showAlert("Échec","Échec de la suppression de votre base de donnée '"+dbName+"'.","/images/checkFailed.png");
            }
        } else {
            // User clicked "Cancel", cancel the operation
            System.out.println("User clicked 'No'");
        }
    }





    public void addDirecteur()  {
        Alerts sa = new Alerts();


            String nom = nomTextfield.getText();
            String prenom = prenomTextfield.getText();
            String adrMail = emailTextfield.getText();
            String pwd = pwdTextfield.getText();
            String hashedPassword = PassworManager.hashPassword(pwd);

            String cnfPwd = cnfPwdTextfield.getText();

            String numTel = null ;
            if(!TelTextfield.getText().isEmpty()){ numTel = TelTextfield.getText();}

            String cin = null ;
            if(!cinTextfield.getText().isEmpty()) { cin = cinTextfield.getText();}

            String adr = null ;
            if(!adrTextfield.getText().isEmpty()) { adr = adrTextfield.getText();}

            if(nom.isEmpty() || prenom.isEmpty() || adrMail.isEmpty() || pwd.isEmpty() || cnfPwd.isEmpty()){
                sa.showAlert2("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
            }else{
                if(!pwd.equals(cnfPwd)){
                    sa.showAlert2("Attention", "Le mot de passe entré et sa confirmation ne correspondent pas. Veuillez réécrire le mot de passe.");
                }else{
                    Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

                    System.out.println("adresse mail : " + adrMail);

                    //String nomEntreprise = ControllerSignUp.getCmp();
                    //String nomEntreprise = "teeest teestt" ;

                    String insert = "UPDATE `infosentreprises`\n" +
                            "SET `nomDirecteur` = ?, \n" +
                            "    `prenomDirecteur` = ?, \n" +
                            "    `adresseMailDirecteur` = ?, \n" +
                            "    `cinDirecteur` = ?, \n" +
                            "    `motdePasseDirecteur` = ?, \n" +
                            "    `numeroTelDirecteur` = ?, \n" +
                            "    `adresseDirecteur` = ? \n" +
                            "WHERE `nomEntreprise` = '" +dbName+ "';\n";

                    if(c.miseAjour(insert, nom, prenom,adrMail,cin, hashedPassword, numTel, adr)){
                        sa.showAlert("Creation avec succes", "Succès ! Le directeur " +prenom+" "+nom +" a maintenant un compte actif lié a son entreprise " +dbName, "/images/checked.png");
                    }
                    c.closeResources();
                }
            }


    }
}
