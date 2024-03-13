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
import java.util.ArrayList;

public class ControllerDbCreation2 {

    //String dbName = ControllerSignUp.getCmp();
    String dbName ="youssef";
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
    private PasswordField cnfPwdTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField nomTextfield;
    @FXML
    private TextField prenomTextfield;
    @FXML
    private PasswordField  pwdTextfield;



    public void initialize() {
        // Add listeners to the textProperty of the respective TextFields with a slight delay
        ControllerDbCreation2.DelayedTextChangeListener emailListener = new ControllerDbCreation2.DelayedTextChangeListener();
        emailTextfield.textProperty().addListener(emailListener);

        ControllerDbCreation2.DelayedTextChangeListener faxListener = new ControllerDbCreation2.DelayedTextChangeListener();
        TelTextfield.textProperty().addListener(faxListener);

    }
    private class DelayedTextChangeListener implements ChangeListener<String> {
        private final PauseTransition pause = new PauseTransition(Duration.seconds(3)); // 3 second delay after FINISHING TYPING
        DelayedTextChangeListener() { pause.setOnFinished(event -> validate());}
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
    private boolean isValidNumber(String phoneNumber) {return phoneNumber.matches("^\\d{10}$");}

    private void validateEmail(String email) {
        if (email.trim().isEmpty()) {
            emailTextfield.clear();
        } else if (!isValidEmail(email)) {
            emailTextfield.setStyle("-fx-prompt-text-fill: red;");
            emailTextfield.setPromptText("Adresse email non valid !!!");
            emailTextfield.clear();
        }
    }
    private boolean isValidEmail(String email) { return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$");}
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
        ControllerDbCreation cdc = new ControllerDbCreation();
        cdc.AnnulerBtn();
    }

    public void addDirecteur(ActionEvent event) throws IOException {
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
                sa.showAlert2("Attention", "Certains champs obligatoires sont vides." +
                        " Veuillez remplir les champs marqués d'une étoile rouge.");
            }else{
                if(!pwd.equals(cnfPwd)){
                    sa.showAlert2("Attention", "Le mot de passe entré et sa confirmation ne correspondent pas. Veuillez réécrire le mot de passe.");
                }else{
                    Connexion c = new Connexion("jdbc:mysql://localhost:3306/Entreprises?user=root");

                    System.out.println("adresse mail : " + adrMail);


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
                        sa.showAlert("Creation avec succes", "Succès ! Le directeur ' " +prenom+" "+nom +" ' a maintenant un compte actif lié a son entreprise ' " +dbName+" '", "/images/checked.png");
                        ChangingWindows cw = new ChangingWindows();
                        cw.switchWindow(event, "FstWindow.fxml");
                    }

                    c.createTable(dbName,"Responsables", getEmpColumns());
                    c.createTable(dbName, "Gestionnaires", getEmpColumns());
                    c.createTable(dbName, "Vendeurs", getEmpColumns());

                    ChangingWindows cw = new ChangingWindows();
                    cw.switchWindow(event, "FstWindow.fxml");
                    c.closeResources();
                }
            }
    }


    public ArrayList<String> getEmpColumns(){
        ArrayList<String> list = new ArrayList<>();
        list.add("CIN VARCHAR(15) PRIMARY KEY ");
        list.add("Nom VARCHAR(30)");
        list.add("Prenom VARCHAR(30)");
        list.add("AdresseMail VARCHAR(100)");
        list.add("motdePasse Varchar(255)");

        return  list ;
    }
}
