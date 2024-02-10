package com.example.pfeprojet;

public class CompanyInfos {


    private String companyName;
    private String mailAdress;
    private String password;
    private String location;
    private String phoneNumber;
    private String faxNumber;
    private String activity ;
    private String taxId;

    public CompanyInfos( String companyName, String mailAdress, String password, String location, String phoneNumber, String faxNumber, String activity, String taxId){
        this.companyName = companyName;
        this.mailAdress = mailAdress;
        this.password = password;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber ;
        this.activity = activity;
        this.taxId = taxId;
    }
//GETTERS
    public String getCompanyName(){
        return companyName;
    }
    public String getMailAdress(){return mailAdress;}
    public String getPassword(){
        return password;
    }
    public String getLocation(){
        return location;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getFaxNumber(){
        return faxNumber;
    }
    public String getActivity(){
        return activity;
    }
    public String getTaxId(){return taxId;}


//SETTERS

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }
    public void setMailAdress(String mailAdress){
        this.mailAdress = mailAdress;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setFaxNumber(String faxNumber){
        this.faxNumber = faxNumber ;
    }
    public void setActivity(String activity){
        this.activity = activity;
    }
    public void setTaxId(String taxId){
        this.taxId = taxId;
    }


}
