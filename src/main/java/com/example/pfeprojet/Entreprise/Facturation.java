package com.example.pfeprojet.Entreprise;


import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Facturation {
    public static void main(String[] args) throws FileNotFoundException, SQLException {

        ArrayList<Produit> produits = new ArrayList<>();

        ArrayList<Service> services = new ArrayList<>();


        Client c = new Client("Fc59627","El khalloufi","youssef","","0627860225","");

        Entreprise e = new Entreprise("JBL");

        int numFacture = 1 ;

        if (e.typeInventaire() == 1) {
            ResultSet rs = e.getProduitsParFacture(numFacture);
            while(rs.next()){
                Produit p = new Produit(rs.getString(1),rs.getInt(3),rs.getDouble(2));
                produits.add(p);
            }
        }else if (e.typeInventaire() == 2 ){
            ResultSet rs1 = e.getServicesParFacture(numFacture);
            while(rs1.next()){
                Service s = new Service(rs1.getString(1),rs1.getInt(3),rs1.getDouble(2));
                services.add(s);
            }
        }else {
            ResultSet rs1 = e.getServicesParFacture(numFacture);
            while(rs1.next()){
                Service s = new Service(rs1.getString(1),rs1.getInt(3),rs1.getDouble(2));
                services.add(s);
            }
            ResultSet rs = e.getProduitsParFacture(numFacture);
            while(rs.next()){
                Produit p = new Produit(rs.getString(1),rs.getInt(3),rs.getDouble(2));
                produits.add(p);
            }
        }




        double totalHtProduits = 0 ;
        for(Produit p : produits){
            totalHtProduits += p.getTotal();
        }

        double totalHTServices = 0 ;
        for(Service s : services){
            totalHTServices += s.getTotal();
        }


        String emplacement = "FactureNum" +numFacture +"_" + LocalDate.now()+".pdf";
        double totalHt = totalHtProduits + totalHTServices;
        genererFacture(emplacement, e,
                numFacture, c, produits, services,  totalHtProduits, totalHTServices, totalHt);
    }



    public static void genererFacture(String emplacement,Entreprise entreprise , int numFact, Client client,
                                      ArrayList<Produit> produits, ArrayList<Service> services, double totalHtProduits,
                                      double totalHtServices, double totalHt) throws FileNotFoundException, SQLException {
        // Create "Factures" directory if it doesn't exist
        File facturesDir = new File("Factures");
        if (!facturesDir.exists()) {
            facturesDir.mkdir();
        }

        // Update the path to save the PDF in the "Factures" directory
        String chemin = facturesDir.getPath() + "/" + emplacement;
        PdfWriter ecrivainPdf = new PdfWriter(chemin);
        PdfDocument documentPdf = new PdfDocument(ecrivainPdf);
        documentPdf.setDefaultPageSize(PageSize.A4);

        Document document = new Document(documentPdf);

        Paragraph spaces = new Paragraph();

        // Header: Title
        Paragraph title = new Paragraph("Facture").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(24);
        document.add(title);

        entreprise.setInfosEntreprise();
        // Seller Information
        Paragraph infosVendeur = new Paragraph()
                .add(entreprise.getNomEntreprise() + "\n")
                .add(entreprise.getLocalisation() + "\n")
                .add(entreprise.getVille() + "\n")
                .add("Maroc\n")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(12);

        document.add(infosVendeur);

        Border grayBorder = new SolidBorder(Color.GRAY, 2f);
        Table divider = new Table(new float[]{190f * 3});
        divider.setBorder(grayBorder);
        document.add(divider);

        spaces.add("\n");
        document.add(spaces);

        Table tableInfos = new Table(new float[]{450f, 270f});

        Table infosFacture = new Table(new float[]{100f, 170f});
        infosFacture.addCell(new Cell().add("Facture N° :").setBold().setBorder(Border.NO_BORDER));
        infosFacture.addCell(new Cell().add(String.valueOf(numFact)).setBorder(Border.NO_BORDER));
        infosFacture.addCell(new Cell().add("Date :").setBold().setBorder(Border.NO_BORDER));
        infosFacture.addCell(new Cell().add(LocalDate.now().toString()).setBorder(Border.NO_BORDER));


        Table infosAcheteur = new Table(new float[]{105f, 345f});
        infosAcheteur.addCell(new Cell().add("CIN :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getCin()).setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add("Nom complet :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getNomComplet()).setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add("Adresse :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getAdresse()).setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add("Telephone :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getTelephone()).setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add("E-mail :").setBold().setBorder(Border.NO_BORDER));
        infosAcheteur.addCell(new Cell().add(client.getMail()).setBorder(Border.NO_BORDER));


        tableInfos.addCell(new Cell().add(infosAcheteur).setBorder(Border.NO_BORDER));
        tableInfos.addCell(new Cell().add(infosFacture));

        document.add(tableInfos);

        document.add(spaces);
        if(entreprise.typeInventaire() == 1){
            factureProduits(document, produits,totalHtProduits); //si lentreprise vend juste des produits
        }else if (entreprise.typeInventaire() == 2){
            factureServices(document, services, totalHtServices); //si lentreprise vend juste des services
        }else if (entreprise.typeInventaire() == 0){              //si lentreprise vend des services et des produits
            factureServices(document, services, totalHtServices);
            factureProduits(document, produits,totalHtProduits);
        }

        document.add(spaces);
        Table table = new Table(new float[]{330f, 90f, 150f, 150f});
        if(entreprise.checkTva_Remise() == 2){

            //tva+remise

            ResultSet rs = entreprise.getInfosFacture(numFact);
            double tvaPercentage = 0;
            double remisePrecentage = 0;
            double totalTTC = 0;
            double remise = 0 ;
            double netComm = 0 ;
            double tva = 0;

            if (rs.next()) {
                 tvaPercentage = rs.getDouble(2);
                 remisePrecentage = rs.getDouble(3);
                 totalTTC = rs.getDouble(5);

                 remise = (totalHt * remisePrecentage)/100;
                 netComm = totalHt - remise ;

                 tva = (netComm * tvaPercentage)/100 ;
            }


            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Total HT " ));
            table.addCell(new Cell().add(String.valueOf(formatDouble(totalHt))).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Remise " + formatDouble(remisePrecentage) + " %"));
            table.addCell(new Cell().add(String.valueOf(formatDouble(remise))).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Net commercial"));
            table.addCell(new Cell().add(String.valueOf(formatDouble(netComm))).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Tva " + formatDouble(tvaPercentage) + " %"));
            table.addCell(new Cell().add(String.valueOf(formatDouble(tva))).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Net à payer "));
            table.addCell(new Cell().add(String.valueOf(formatDouble(totalTTC))).setTextAlignment(TextAlignment.CENTER));

            document.add(table);

        }else if (entreprise.checkTva_Remise() == 1){
            //TVA

            ResultSet rs = entreprise.getInfosFacture(numFact);
            double tvaPercentage = 0;
            double tva = 0 ;
            double totalTTC = 0 ;

            if(rs.next()){
                tvaPercentage = rs.getDouble(2);
                totalTTC = rs.getDouble(4);

                tva = (totalHt * tvaPercentage)/100;
            }

            totalTTC += tva ;

            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Total HT " ));
            table.addCell(new Cell().add(String.valueOf(formatDouble(totalHt))).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Tva " + formatDouble(tvaPercentage) + " %"));
            table.addCell(new Cell().add(String.valueOf(formatDouble(tva))).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Net à payer "));
            table.addCell(new Cell().add(String.valueOf(formatDouble(totalTTC))).setTextAlignment(TextAlignment.CENTER));

            document.add(table);
        }else if (entreprise.checkTva_Remise() == 0){
            //remise

            ResultSet rs = entreprise.getInfosFacture(numFact);

            double remisePrecentage = 0;
            double totalTTC = 0;
            double remise = 0 ;
            double netComm = 0 ;


            if (rs.next()) {

                remisePrecentage = rs.getDouble(2);
                totalTTC = rs.getDouble(4);

                remise = (totalHt * remisePrecentage)/100;
                netComm = totalHt - remise ;


            }



            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Total HT " ));
            table.addCell(new Cell().add(String.valueOf(formatDouble(totalHt))).setTextAlignment(TextAlignment.CENTER));


            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Remise " + formatDouble(remisePrecentage) + " %"));
            table.addCell(new Cell().add(String.valueOf(formatDouble(remise))).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Net commercial"));
            table.addCell(new Cell().add(String.valueOf(formatDouble(netComm))).setTextAlignment(TextAlignment.CENTER));

            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Net à payer" ));
            table.addCell(new Cell().add(String.valueOf(formatDouble(totalTTC))).setTextAlignment(TextAlignment.CENTER));

            document.add(table);

        }else if(entreprise.checkTva_Remise() == -1){
            double totalTTC = totalHt;
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add("Net à payer" ));
            table.addCell(new Cell().add(String.valueOf(formatDouble(totalTTC))).setTextAlignment(TextAlignment.CENTER));

            document.add(table);
        }

        document.add(spaces);


        document.add(spaces);
        // Footer: Seller Name and Signature Space
        Paragraph footer = new Paragraph("Le vendeur :").setTextAlignment(TextAlignment.RIGHT).setFontSize(12);
        document.add(footer);

        document.close();

        openPDF(chemin);
    }

    public static void factureProduits(Document document, ArrayList<Produit> produits, double totalHtProduits) {

        Paragraph produitPara = new Paragraph("Produits :").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(12);
        document.add(produitPara);

        Table table = new Table(new float[]{330f, 90f, 150f, 150f});
        table.addCell(new Cell().add("Désignation").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Quantité").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Prix unitaire").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Montant total").setTextAlignment(TextAlignment.CENTER).setBold());


        for (Produit produit : produits) {
            table.addCell(produit.getDesignation());
            table.addCell(new Cell().add(String.valueOf(produit.getQuantite())).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(produit.getPrix())).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(formatDouble(produit.getTotal()))).setTextAlignment(TextAlignment.CENTER));
        }

        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("Montant total HT "));
        table.addCell(new Cell().add(String.valueOf(formatDouble(totalHtProduits))).setTextAlignment(TextAlignment.CENTER));



        document.add(table);
    }


    public static void factureServices(Document document, ArrayList<Service> services, double totalHtServices){

        Paragraph produitPara = new Paragraph("Services :").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(12);
        document.add(produitPara);

        Table table = new Table(new float[]{330f, 90f, 150f, 150f});
        table.addCell(new Cell().add("Désignation").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Nombre Heure").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Prix").setTextAlignment(TextAlignment.CENTER).setBold());
        table.addCell(new Cell().add("Montant total").setTextAlignment(TextAlignment.CENTER).setBold());


        for (Service service : services) {
            table.addCell(service.getDesignation());
            table.addCell(new Cell().add(String.valueOf(service.getNombreHeure())).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(service.getPrix())).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(String.valueOf(formatDouble(service.getTotal()))).setTextAlignment(TextAlignment.CENTER));
        }

        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("Montant total HT "));
        table.addCell(new Cell().add(String.valueOf(formatDouble(totalHtServices))).setTextAlignment(TextAlignment.CENTER));



        document.add(table);
    }


    private static void openPDF(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("Desktop is not supported. Cannot open the PDF file.");
                }
            } else {
                System.out.println("The file does not exist.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatDouble(double value) {
        return String.format("%.2f", value);
    }

}
