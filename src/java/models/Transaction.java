/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import utils.Connexion;

/**
 *
 * @author PHAEL
 */
public class Transaction {
 private int id;
    private Compte compte;
    private double frais;
    private String dateTransaction;
    private double montant;
    private String type;

    public Transaction() {
    }
    
    

    public Transaction(int id, Compte compte, double frais, String date, double montant,String type) {
        this.id = id;
        this.compte = compte;
        this.frais = frais;
        this.dateTransaction = date;
        this.montant = montant;
    }

    private Transaction(int id, Double montant, String date, int frais) {
        this.id = id;
        this.frais = frais;
        this.dateTransaction = date;
        this.montant = montant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Compte getCompte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateD) {
        this.dateTransaction = dateD;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    
    
   
    
    public static void ajoutTransaction(double frais,int idCompte,double montant,String type) throws Exception{
      Connection con =null;
        PreparedStatement pst=null;
        try{
            con=Connexion.getConn();
            con.setAutoCommit(false);
                                          //INSERT INTO transaction (frais,idCompte,date,montant,type) VALUES(600,1,'2021-03-06',9000,'retrait');
            pst = con.prepareStatement("INSERT INTO transaction (frais,idCompte,date,montant,type) VALUES(?,?,?,?,?)");
         //   pst = con.prepareStatement("INSERT INTO transaction (frais,idCompte,date,montant,type) VALUES(600,1,'2021-03-06',9000,'retrait')");
           pst.setDouble(1,frais);
            pst.setInt(2,idCompte);
            
            //pst.setDate(3, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            pst.setDate(3, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            pst.setDouble(4,montant );
            pst.setString(5,type);
            pst.executeUpdate();
            con.commit();
        }
        catch(Exception ex){
            con.rollback();
            throw ex;
        }finally{
            if(pst!=null)pst.close();
            if(con!=null)con.close();            
        }  
    }
    public static ArrayList<Transaction> findTransaction(String type,int idCompte) throws Exception{
       ArrayList<Transaction> list=new ArrayList<>();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from transaction where idcompte="+idCompte+" and type='"+type+"'");
            while(rs.next()){
                int id=rs.getInt("id");
                 int idC= rs.getInt("idcompte");
                 Double montant= rs.getDouble("montant");
                 String date=rs.getString("date");
                 int frais=rs.getInt("frais");
                Transaction transaction=new Transaction(id,montant,date,frais);
                list.add(transaction);
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            throw e;
            
        }
        finally{
            connex.close();
            stm.close();
        }
       return list;
   }
    
}