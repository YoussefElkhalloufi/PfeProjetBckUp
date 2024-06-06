package com.example.pfeprojet.Controllers;


import com.example.pfeprojet.Alerts;
import com.example.pfeprojet.ChangingWindows;
import com.example.pfeprojet.Entreprise.Entreprise;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;

import java.io.IOException;


// TODO over all : FINISH
        //TODO : --> FACTURATION
        //TODO : --> PROFIL ENTREPRISE
public class ControllerChiffreAffaire {
    @FXML
    private ComboBox<String> speciDiagAnnee;
    @FXML
    private BarChart<String, Number> barChartAnnee;
    @FXML
    private LineChart<String, Number> lineChartAnnee;
    @FXML
    private PieChart pieChartAnnee;



    @FXML
    private ComboBox<String> deCmbMois;
    @FXML
    private ComboBox<String> aCmbMois;
    @FXML
    private ComboBox<String> speciDiagMois;
    @FXML
    private BarChart<String, Number> barChartMois;
    @FXML
    private LineChart<String, Number> lineChartMois;
    @FXML
    private PieChart pieChartMois;



    private Entreprise e ;
    //private Entreprise e1 = new Entreprise("hennesy_liquor_store");

    void setEntreprise(){
        e  = ControllerDashboardDirecteur.getEntreprise() ;
        if (e == null) {
            e = ControllerDashboardResponsable.getEntreprise();
        }
    }
    public void initialize(){
        setEntreprise();
        String[] mois = {"Janvier","Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};
        double[] chiffreAffaireMois = {e.getChiffreAffaireMois(1),e.getChiffreAffaireMois(2),e.getChiffreAffaireMois(3),e.getChiffreAffaireMois(4),e.getChiffreAffaireMois(5),e.getChiffreAffaireMois(6),e.getChiffreAffaireMois(7),e.getChiffreAffaireMois(8),e.getChiffreAffaireMois(9),e.getChiffreAffaireMois(10),e.getChiffreAffaireMois(11),e.getChiffreAffaireMois(12)};

        chargementChartsMois(mois, chiffreAffaireMois);

        barChartMois.setVisible(true);
        affichageValeurs(barChartMois);
        lineChartMois.setVisible(false);
        pieChartMois.setVisible(false);




        String[] annee ={"2024","2025","2026","2027","2028"};
        double[] chiffreAffaireAnnee={e.getChiffreAffaireAnnee(2024),e.getChiffreAffaireAnnee(2025),e.getChiffreAffaireAnnee(2026),e.getChiffreAffaireAnnee(2027),e.getChiffreAffaireAnnee(2028)};

        chargementChartsAnnee(annee, chiffreAffaireAnnee);

        barChartAnnee.setVisible(true);
        affichageValeurs(barChartAnnee);
        lineChartAnnee.setVisible(false);
        pieChartAnnee.setVisible(false);
    }
    public void chargementChartsMois(String[] xValues, double[] yValues){
        addDataToChart(barChartMois, "Chiffre Affaire" ,xValues, yValues);
        addDataToChart(lineChartMois, "Chiffre Affaire", xValues, yValues);
        addDataToPieChart(pieChartMois, xValues, yValues);
    }
    public void chargementChartsAnnee(String[] xValues, double[] yValues){
        addDataToChart(barChartAnnee,"Chiffre Affaire", xValues, yValues);
        addDataToChart(lineChartAnnee, "Chiffre Affaire", xValues, yValues);
        addDataToPieChart(pieChartAnnee, xValues, yValues);
    }
    public static void addDataToChart(XYChart<String, Number> barChart, String seriesName, String[] xValues, double[] yValues) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(seriesName);

        for (int i = 0; i < xValues.length; i++) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(xValues[i], yValues[i]);
            series.getData().add(data);
        }

        barChart.getData().add(series);
    }

    public static void addDataToPieChart(PieChart pieChart, String[] labels, double[] values) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < labels.length && i < values.length; i++) {
            pieChartData.add(new PieChart.Data(labels[i], values[i]));
        }

        // Bind names to values
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " CA : ", data.pieValueProperty()," DH"
                        )
                )
        );

        pieChart.getData().addAll(pieChartData);
    }

    public static void affichageValeurs(XYChart<String, Number> chart) {
        for (XYChart.Series<String, Number> series : chart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                Tooltip tooltip = new Tooltip();
                tooltip.setText(data.getYValue().toString() + " DH");
                Tooltip.install(data.getNode(), tooltip);
            }
        }
    }

    @FXML
    void diagChangeMois(ActionEvent event) {
        if(speciDiagMois.getValue().equalsIgnoreCase("Diagramme en bâtons")){
            affichageBarChartMois();
        }else if (speciDiagMois.getValue().equalsIgnoreCase("Graphique en ligne")){
            affichageLineChartMois();
        }else{
            affichagePieChartMois();
        }
    }

    @FXML
    void diagChangeAnnee(ActionEvent event) {
        if(speciDiagAnnee.getValue().equalsIgnoreCase("Diagramme en bâtons")){
            affichageBarChartAnnee();
        }else if(speciDiagAnnee.getValue().equalsIgnoreCase("Graphique en ligne")){
            affichageLineChartAnnee();
        }else{
            affichagePieChartAnnee();
        }
    }
    public void affichageBarChartAnnee(){
        barChartAnnee.setVisible(true);
        affichageValeurs(barChartAnnee);
        lineChartAnnee.setVisible(false);
        pieChartAnnee.setVisible(false);
    }
    public void affichageLineChartAnnee(){
        lineChartAnnee.setVisible(true);
        affichageValeurs(lineChartAnnee);
        barChartAnnee.setVisible(false);
        pieChartAnnee.setVisible(false);
    }
    public void affichagePieChartAnnee(){
        pieChartAnnee.setVisible(true);
        barChartAnnee.setVisible(false);
        lineChartAnnee.setVisible(false);
    }
    public void affichageBarChartMois(){
        barChartMois.setVisible(true);
        affichageValeurs(barChartMois);
        lineChartMois.setVisible(false);
        pieChartMois.setVisible(false);
    }
    public void affichageLineChartMois(){
        lineChartMois.setVisible(true);
        affichageValeurs(lineChartMois);
        barChartMois.setVisible(false);
        pieChartMois.setVisible(false);
    }
    public void affichagePieChartMois(){
        pieChartMois.setVisible(true);
        barChartMois.setVisible(false);
        lineChartMois.setVisible(false);
    }

    @FXML
    void filtrerMois(ActionEvent event){
        String deMois = deCmbMois.getValue();
        String aMois = aCmbMois.getValue();

        Alerts a = new Alerts();

        if( deMois != null && aMois != null){
            int deIndex = deCmbMois.getItems().indexOf(deMois);
            int aIndex = deCmbMois.getItems().indexOf(aMois);
                if(deIndex >= 0 && aIndex >= 0 && deIndex < aIndex){

                    String[] mois = new String[(aIndex - deIndex)+1];
                    double[] valeur = new double[(aIndex - deIndex)+1];
                    int j = 0;
                    for(int i = deIndex; i < aIndex+1; i++){
                        mois[j] = deCmbMois.getItems().get(i);
                        System.out.println("valeur de mois  " +(i+1) +": " + e.getChiffreAffaireMois(i + 1));
                        valeur[j] = e.getChiffreAffaireMois(i + 1);
                        j++;
                    }

                    barChartMois.getData().clear();
                    lineChartMois.getData().clear();
                    pieChartMois.getData().clear();


                    chargementChartsMois(mois, valeur);
                        if(speciDiagMois.getValue().equalsIgnoreCase("Diagramme en bâtons")){
                            affichageBarChartMois();
                        }else if(speciDiagMois.getValue().equalsIgnoreCase("Graphique en ligne")){
                            affichageLineChartMois();
                        }else{
                            affichagePieChartMois();
                        }
                }else{
                    System.out.println("Le format des mois est invalide.");
                    a.showWarning("Erreur de format","Le format des mois est invalide.");
                }
        }else{
            System.out.println("Veuillez sélectionner les mois dans les deux ComboBox.");
            a.showWarning("Sélection manquante","Veuillez sélectionner les mois dans les deux ComboBox.");
        }
    }
    @FXML
    void dashboardDirecteur(ActionEvent event) throws IOException {
        ChangingWindows cw = new ChangingWindows();
        if(ControllerDashboardDirecteur.dr == null){
            cw.switchWindow(event, "DashboardResponsable.fxml");
        }else{
            cw.switchWindow(event,"DashboardDirecteur.fxml");
        }
    }
}
