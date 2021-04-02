/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import utils.Connexion;

/**
 *
 * @author PHAEL
 */
public class Depot {
  private int id;
    private Compte compte;
    private double frais;
    private String dateDepot;
    private double montant;

    public Depot() {
    }
    
    

    public Depot(int id, Compte compte, double frais, String dateDepot, double montant) {
        this.id = id;
        this.compte = compte;
        this.frais = frais;
        this.dateDepot = dateDepot;
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

    public String getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(String dateDepot) {
        this.dateDepot = dateDepot;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    
    public static void ajoutDepot(double frais,int idCompte,String date,double montant) throws Exception{
      Connection con =null;
        PreparedStatement pst=null;
        try{
            con=Connexion.getConn();
            con.setAutoCommit(false);
            pst = con.prepareStatement("INSERT INTO depot (frais,idCompte,dateDepot,montant) VALUES(?,?,?,?)");
            pst.setDouble(1,frais);
            pst.setInt(2,idCompte);
            pst.setString(3, date);
            pst.setDouble(4, montant);
            pst.executeQuery();
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
    
}
