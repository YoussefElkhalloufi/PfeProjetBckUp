package com.example.pfeprojet;

import java.sql.* ;
import java.util.ArrayList;

public class Connexion {

    private String url;
    private Connection con ;
    private PreparedStatement ps;//Using PreparedStatement instead of Statement to PREVENT sql Injection ATTACKS
    private ResultSet rs ;

    public String getUrl(){
        return url;
    }

    public Connexion (String url){
        this.url = url ;
        try {
            // Chargeement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Etablissement de la connexion a la BD
            con = DriverManager.getConnection(url);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean createDatabase(String dbName) {
        try {
            String query = "CREATE DATABASE IF NOT EXISTS " + dbName;
            ps = con.prepareStatement(query);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false ;
        }
    }

    public boolean dropDatabase(String dbName) {
        try {
            String query = "DROP DATABASE IF EXISTS " + dbName;
            ps = con.prepareStatement(query);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public boolean createTable(String dbName, String tableName, ArrayList<String> columns) {
        try {
            StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
            query.append(dbName).append(".").append(tableName).append(" (");
            for (String column : columns) {
                query.append(column).append(",");
            }

            query.deleteCharAt(query.length() - 1); // Remove the trailing comma
            query.append(")");

            ps = con.prepareStatement(query.toString());
            ps.executeUpdate() ;
            return true ;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public ResultSet lire(String query, String... params) {

        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean miseAjour(String query, String... params) {
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                pstmt.setString(i + 1, params[i]);
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CompanyInfos> toArray(CompanyInfos c){
        ArrayList<CompanyInfos> companies = new ArrayList<>();
        companies.add(c);
        return companies;
    }
}
