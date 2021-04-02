/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.Connexion;

/**
 *
 * @author PHAEL
 */
public class Compte {

    
 private int id;
    private String code;
    private double solde;
    private int idutilisateur;

    public Compte() {
    }

    public Compte(int id, String code, double solde,int idu) {
        this.id = id;
        this.code = code;
        this.solde = solde;
        this.idutilisateur = idu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
    
      public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(int idu) {
        this.idutilisateur = idu;
    }

    
    public static ArrayList<Compte> findAll() throws Exception{
       ArrayList<Compte> list=new ArrayList<>();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from compte");
            while(rs.next()){
                int id=rs.getInt("id");
                 String code= rs.getString("code");
                 double solde=rs.getDouble("solde");
                 int idPuce=rs.getInt("idutilisateur");
                 Compte c=new Compte(id, code, solde, idPuce);
                list.add(c);
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
    
    public static Compte findById(int idM) throws Exception{
       
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from compte where idutilisateur="+idM);
            while(rs.next()){
                int id=rs.getInt("id");
                 String code= rs.getString("code");
                 double solde=rs.getDouble("solde");
                 int idPuce=rs.getInt("idutilisateur");
                Compte mvola=new Compte(id, code, solde,idPuce);
                return mvola;
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
    
     public static void updateCompte(double montant,int id) throws SQLException, Exception{
        Connection connex=null;
        PreparedStatement pst=null;
         String sql="UPDATE compte SET solde="+montant+" where idutilisateur=?";
        
        try{
            connex=Connexion.getConn();
            pst=connex.prepareStatement(sql);
            pst.setInt(1, id);
            int i=pst.executeUpdate();
            //connex.commit();
        }
        catch(Exception ex){
            //connex.rollback();
            throw ex;
        }finally{
            if(pst!=null)pst.close();
            if(connex!=null)connex.close();            
        }
    }
     
    public static boolean verifyCode(int idU,String code) throws SQLException, Exception {
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from compte where idutilisateur = "+idU+" and code='"+code+"'");
            while(rs.next()){
               return true;
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
       return false;
    }
}
