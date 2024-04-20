package com.example.pfeprojet.Controllers;

import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ControllerPersonnelEntreprise {

    @FXML
    private TableView<Object[]> responsableTableView;
    @FXML
    private TextField cinTextRespo;
    @FXML
    private TextField nomTextRespo;
    @FXML
    private TextField prenomTextRespo;
    @FXML
    private TextField mailTextRespo;
    @FXML
    private TextField pwdTextRespo;



    @FXML
    private TableView<Object[]> gestionnaireTableView;
    @FXML
    private TextField cinTextGestio;
    @FXML
    private TextField nomTextGestio;
    @FXML
    private TextField prenomTextGestio;
    @FXML
    private TextField mailTextGestio;
    @FXML
    private TextField pwdTextGestio;





    @FXML
    private TableView<Object[]> vendeurTableView;
    @FXML
    private TextField cinTextVendeur;
    @FXML
    private TextField nomTextVendeur;
    @FXML
    private TextField prenomTextVendeur;
    @FXML
    private TextField mailTextVendeur;
    @FXML
    private TextField pwdTextVendeur;



    private Entreprise e = ControllerDashboardDirecteur.getEntreprise();


    public void initialize(){
        e.populateTableViewWithSelectionHandler(e.getResponsables(),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
        e.populateTableViewWithSelectionHandler(e.getGestionnaires(),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
        e.populateTableViewWithSelectionHandler(e.getVendeurs(), vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
    }
//TODO : Supprimer, modifier(avec mail), afficher
    @FXML
    void ajouterRespo(ActionEvent event) {
        String cin = cinTextRespo.getText();
        String nom = nomTextRespo.getText();
        String prenom = prenomTextRespo.getText();
        String mail = mailTextRespo.getText();
        String pwd = pwdTextRespo.getText();

        Alerts sa = new Alerts();
        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(mail.endsWith("@gmail.com")){
                if(e.insererRespo(cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du responsable est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getResponsables(),responsableTableView, cinTextRespo, nomTextRespo, prenomTextRespo, mailTextRespo, pwdTextRespo);
                    sendMail("Responsable",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du responsable existe deja ! ", "/images/annuler.png");
                }
            }else{
                sa.showWarning("Format Erroné", "Veuillez entrer une adresse e-mail valide se terminant par '@gmail.com'.");
            }
        }
    }

    @FXML
    void ajouterGestio(ActionEvent event) {
        String cin = cinTextGestio.getText();
        String nom = nomTextGestio.getText();
        String prenom = prenomTextGestio.getText();
        String mail = mailTextGestio.getText();
        String pwd = pwdTextGestio.getText();

        Alerts sa = new Alerts();
        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(mail.endsWith("@gmail.com")){
                if(e.insererGestio(cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du gestionnaire est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getGestionnaires(),gestionnaireTableView, cinTextGestio, nomTextGestio, prenomTextGestio, mailTextGestio, pwdTextGestio);
                    sendMail("Gestionnaire",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du gestionnaire existe deja ! ", "/images/annuler.png");
                }
            }else{
                sa.showWarning("Format Erroné", "Veuillez entrer une adresse e-mail valide se terminant par '@gmail.com'.");
            }
        }
    }

    @FXML
    void ajouterVendeur(ActionEvent event) {
        String cin = cinTextVendeur.getText();
        String nom = nomTextVendeur.getText();
        String prenom = prenomTextVendeur.getText();
        String mail = mailTextVendeur.getText();
        String pwd = pwdTextVendeur.getText();

        Alerts sa = new Alerts();
        if(cin.isEmpty() || nom.isEmpty() || prenom.isEmpty() || mail.isEmpty() || pwd.isEmpty()){
            sa.showWarning("Attention", "Certains champs obligatoires sont vides. Assurez-vous de remplir toutes les informations nécessaires.");
        }else{
            if(mail.endsWith("@gmail.com")){
                if(e.insererVendeur(cin, nom, prenom, mail, pwd)){
                    sa.showAlert("Ajout avec succes", "Le compte du Vendeur est bien ajouté ! ", "/images/checked.png");
                    e.populateTableViewWithSelectionHandler(e.getVendeurs(),vendeurTableView, cinTextVendeur, nomTextVendeur, prenomTextVendeur, mailTextVendeur, pwdTextVendeur);
                    sendMail("Vendeur",nom.trim(),prenom.trim(),mail.trim(),pwd);
                }else{
                    sa.showAlert("Ajout Erroné", "Le compte du Vendeur existe deja ! ", "/images/annuler.png");
                }
            }else{
                sa.showWarning("Format Erroné", "Veuillez entrer une adresse e-mail valide se terminant par '@gmail.com'.");
            }
        }
    }

    public void sendMail(String personnel, String nom, String prenom, String mailPersonnel, String pwdPersonnel){
        Alerts sa = new Alerts();
            if(sa.showConfirmationAlert("Confirmation","Souhaitez-vous envoyer un e-mail au personnel contenant leurs informations d'authentification ?")){
                if(EmailSender.check()){
                    String objet = "Informations d'Authentification pour votre Compte sur l'application FacturEase";
                    String message = "Bonjour "+ nom+" "+prenom+",\n" +
                            "\n" +
                            "Nous sommes ravis de vous informer que votre compte pour le poste de" +
                            " ["+personnel+"] dans l'entreprise ["+e.getNomEntreprise()+"] a été créé avec succès. Veuillez trouver ci-dessous vos informations d'accès :\n\n"+
                            "1- Informations d'Authentification pour l'Application :\n" +
                            "Nom d'utilisateur : " + e.getAdresseMail()+"\n"+
                            "Mot de passe : " + e.getMotdepasse()+"\n" +
                            "\n\n" +
                            "2- Informations d'Authentification pour votre Compte Personnel :\n" +
                            "Nom d'utilisateur : " + mailPersonnel+"\n" +
                            "Mot de passe : "+pwdPersonnel+"\n\n"+
                            "Nous vous recommandons vivement de modifier votre mot de passe personnel dès votre première connexion pour des raisons de sécurité.\n" +
                            "\n" +
                            "N'hésitez pas à nous contacter en cas de questions ou de problèmes.\n" +
                            "\n" +
                            "Cordialement,\n" +
                            "L'équipe de FactureEase";
                    EmailSender.sendEmail(mailPersonnel, objet, message);
                    sa.showAlert("Envoi avec succes","L'email a été envoyé avec succes","/images/checked.png");
                }else{
                    sa.showWarning("Echec de l'envoi","Impossible d'envoyer l'e-mail au personnel. Veuillez vérifier votre connexion Internet et réessayer ultérieurement.");
                }
            }
    }

    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        cw.switchWindow(event,"DirecteurDashboard.fxml");
    }
}
