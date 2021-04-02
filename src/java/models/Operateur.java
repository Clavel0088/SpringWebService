/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import utils.Connexion;

/**
 *
 * @author PHAEL
 */
public class Operateur {
 private int id;
    private String nom;
    private double prixMemeOp;
    private double prixAutre;

    public Operateur() {
    }
    public Operateur(int id,String nom) {
        this.id = id;
        this.nom = nom;
    }
    public Operateur(int id, String nom,  double prixMemeOp, double prixAutre) {
        this.id = id;
        this.nom = nom;
        this.prixMemeOp = prixMemeOp;
        this.prixAutre = prixAutre;
    }

    
   
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrixMemeOp() {
        return prixMemeOp;
    }

    public void setPrixMemeOp(double prixMemeOp) {
        this.prixMemeOp = prixMemeOp;
    }

    public double getPrixAutre() {
        return prixAutre;
    }

    public void setPrixAutre(double prixAutre) {
        this.prixAutre = prixAutre;
    }
    
    
     public static ArrayList<Operateur> findAll() throws Exception{
       ArrayList<Operateur> list=new ArrayList<>();
        //Gson gson=new Gson();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from operateur");
            while(rs.next()){
                int id=rs.getInt("id");
                 String nom= rs.getString("nom");
                 double prixMemeOp=rs.getDouble("prixMemeOp");
                 double prixAutre=rs.getDouble("prixAutre");
                Operateur cat=new Operateur(id,nom,prixMemeOp,prixAutre);
                list.add(cat);
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
        //String jsonData=gson.toJson(list);
       return list;
   }
     
     public static Operateur findById(int idOp) throws Exception{
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from operateur where id="+idOp);
            while(rs.next()){
               int id=rs.getInt("id");
                 String nom= rs.getString("nom");
                 double prixMemeOp=rs.getDouble("prixmemeop");
                 double prixAutre=rs.getDouble("prixautre");
                Operateur operateur=new Operateur(id,nom,prixMemeOp,prixAutre);
                return operateur;
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
       return null;
   }
}