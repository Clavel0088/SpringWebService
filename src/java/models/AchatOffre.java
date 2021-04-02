/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import utils.Connexion;

/**
 *
 * @author PHAEL
 */
public class AchatOffre {
    private Offre offre;
    private Utilisateur utilisateur;
    private String dateAchat;
    private String expiration;
    public AchatOffre(Offre offre, Utilisateur utilisateur, String dateAchat) {
        this.offre = offre;
        this.utilisateur = utilisateur;
        this.dateAchat = dateAchat;
    }

    public AchatOffre(Offre offre, Utilisateur utilisateur, String dateAchat, String expiration) {
        this.offre = offre;
        this.utilisateur = utilisateur;
        this.dateAchat = dateAchat;
        this.expiration = expiration;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
    

    public AchatOffre() {
    }

    public Offre getOffre() {
        return offre;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }
    
    public static ArrayList<Offre> findByIdUtilisateur(int idU) throws Exception{
        ArrayList<Offre> list=new ArrayList<>();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select offre.* ,achatOffre.idutilisateur,achatOffre.dateachat,achatOffre.expiration from achatOffre join offre on achatOffre.idoffre=offre.id where achatOffre.idutilisateur="+idU+" and current_date < achatoffre.expiration");
            while(rs.next()){
                int idUt=rs.getInt("idutilisateur");
                int idOffre=rs.getInt("id");
                Offre offre=Offre.findById(idOffre);
                Utilisateur utilisateur=Utilisateur.findById(idUt);
                String dateAchat=rs.getString("dateachat");
                String expiration=rs.getString("expiration");
                offre.setExpiration(expiration);
                AchatOffre achat=new AchatOffre(offre, utilisateur, dateAchat,expiration);
                list.add(offre);
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
    
    public static void ajoutAchatOffre(int idUtilisateur,int idOffre) throws Exception{
      Connection con =null;
        PreparedStatement pst=null;
        java.sql.Date now = new java.sql.Date( new java.util.Date().getTime() );
        

        try{
            con=Connexion.getConn();
            con.setAutoCommit(false);
            Offre o = Offre.findById(idOffre);
            java.sql.Date expiration= new java.sql.Date( now.getTime() + (24*60*60*1000)*o.getValidite());
            pst = con.prepareStatement("INSERT INTO achatoffre (idUtilisateur,idOffre,dateAchat,expiration) VALUES(?,?,?,?)");
            pst.setInt(1,idUtilisateur);
            pst.setInt(2, idOffre);
            pst.setDate(3, now);
            pst.setDate(4, expiration);
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
}
