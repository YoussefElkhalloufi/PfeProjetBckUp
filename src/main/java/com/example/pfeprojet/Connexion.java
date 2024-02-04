package com.example.pfeprojet;

import java.sql.* ;

public class Connexion {

    private String driver, url;
    private ResultSet rs ;
    private Connection con ;
    private Statement s;

    public Connexion (String url, String driver){
        this.url = url ;
        this.driver = driver ;
        try {
            // Chargeement du driver JDBC
            Class.forName(driver);

            // Etablissement de la connexion a la BD
            con = DriverManager.getConnection(url);

        }catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet lire(String req) {
        try {
            //Creation de l'objet statement
            s = con.createStatement();
            rs = s.executeQuery(req);
            return rs ;
        }catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean miseAjour(String req) {
        try {
            s = con.createStatement();
            int i = s.executeUpdate(req);
            return i > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close statement in the finally block to ensure proper cleanup
            try {
                if (s != null) {
                    s.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
