/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import utils.Connexion;

/**
 *
 * @author PHAEL
 */
public class Retrait {
   private int id;
    private String dateRetrait;
    private double frais;
    private double montant;
    private Compte mvola;

    public Retrait() {
    }

    public Retrait(int id, String dateRetrait, double frais, double montant, Compte mvola) {
        this.id = id;
        this.dateRetrait = dateRetrait;
        this.frais = frais;
        this.montant = montant;
        this.mvola = mvola;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateRetrait() {
        return dateRetrait;
    }

    public void setDateRetrait(String dateRetrait) {
        this.dateRetrait = dateRetrait;
    }

    public double getFrais() {
        return frais;
    }

    public void setFrais(double frais) {
        this.frais = frais;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Compte getMvola() {
        return mvola;
    }

    public void setMvola(Compte mvola) {
        this.mvola = mvola;
    }
    
    public static void retrait(double montant,double frais,int id) throws Exception{
        double retrait=montant+frais;
        Compte mvola=Compte.findById(id);
        if(mvola.getSolde()<retrait){
            throw new Exception("Votre solde est Insifusant");
        }
//        else{, id);
        
    }
    
    public static void ajoutRetrait(String date,double frais,double montant,int idCompte) throws Exception{
      Connection con =null;
        PreparedStatement pst=null;
        try{
            con=Connexion.getConn();
            con.setAutoCommit(false);
            pst = con.prepareStatement("INSERT INTO retrait (date,frais,montant,idCompte) VALUES(?,?,?,?)");
            pst.setString(1, date);
            pst.setDouble(2,frais);
            pst.setDouble(3, montant);
            pst.setInt(4,idCompte);
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
