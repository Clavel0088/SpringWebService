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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.Connexion;

/**
 *
 * @author Phael
 */
public class Offre {
    private int id;
    private String nom;
   
    private double prix;
    private int validite;
     private String codeOffre;
     private String expiration;
    public Offre() {
    }

    public Offre(int id, String nom, double prix, int validite, String codeOffre) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.validite = validite;
        this.codeOffre = codeOffre;
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
     public String getExpiration() {
        return nom;
    }

    public void setExpiration(String ex) {
        this.expiration = ex;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getValidite() {
        return validite;
    }

    public void setValidite(int validite) {
        this.validite = validite;
    }

    public String getCodeOffre() {
        return codeOffre;
    }

    public void setCodeOffre(String codeOffre) {
        this.codeOffre = codeOffre;
    }

    
    
    public static ArrayList<Offre> findAll() throws Exception{
       ArrayList<Offre> list=new ArrayList<>();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from offre");
            while(rs.next()){
                int id=rs.getInt("id");
                 String nom= rs.getString("nom");
                 double prix=rs.getDouble("prix");
                 int duree=rs.getInt("validite");
                  String codeOffre= rs.getString("code");
                // String activation= rs.getString("code");
                Offre offre=new Offre(id, nom,  prix,duree,codeOffre);
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
    
    
     public static Offre findById(int idO) throws Exception{
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from offre where id="+idO);
            while(rs.next()){
                int id=rs.getInt("id");
                 String nom= rs.getString("nom");
                 double prix=rs.getDouble("prix");
                 int duree=rs.getInt("validite");
                  String codeOffre= rs.getString("code");
                // String activation= rs.getString("code");
                Offre offre=new Offre(id, nom,  prix,duree,codeOffre);
                return offre;
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
     
    public static Offre findByIdUtilisateur(int idU) throws Exception{
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from offre where id="+idU);
            while(rs.next()){
                int id=rs.getInt("id");
                 String nom= rs.getString("nom");
                 String codeOffre= rs.getString("codeOffre");
                 double prix=rs.getDouble("prix");
                 int duree=rs.getInt("duree");
                 String activation= rs.getString("activation");
                Offre offre=new Offre(id, nom,prix, duree,codeOffre );
                return offre;
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
     
     
     
      // id |     nom     | prix | validite |   code
  
      public static void ajoutOffre(String nom,String codeOffre,double prix,int duree) throws Exception{
      Connection con =null;
        PreparedStatement pst=null;
        try{
            con=Connexion.getConn();
            con.setAutoCommit(false);
            pst = con.prepareStatement("INSERT INTO offre (nom,code,prix,validite) VALUES(?,?,?,?)");
            pst.setString(1,nom);
            pst.setString(2,codeOffre);
            pst.setDouble(3, prix);
            pst.setInt(4, duree);
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
      public static void updateOffre(String nom,String codeOffre,double prix,int duree,int id) throws SQLException, Exception{
        Connection connex=null;
        PreparedStatement pst=null;
         String sql="UPDATE offre SET nom=?,codeOffre=?,prix=?,duree=? where id="+id;
        
        try{
            connex=Connexion.getConn();
            pst=connex.prepareStatement(sql);
            pst.setString(1,nom);
            pst.setString(2, codeOffre);
            pst.setDouble(3, prix);
            pst.setInt(4, duree);
            int i=pst.executeUpdate();
            connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            if(pst!=null)pst.close();
            if(connex!=null)connex.close();            
        }
    }
      
       public static void activerOffre(int id) throws SQLException, Exception{
        Connection connex=null;
        PreparedStatement pst=null;
         String sql="UPDATE offre SET activation=true where id="+id;
        
        try{
            connex=Connexion.getConn();
            pst=connex.prepareStatement(sql);
            int i=pst.executeUpdate();
            //connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            if(pst!=null)pst.close();
            if(connex!=null)connex.close();            
        }
    }
       
        public static void desactiverOffre(int id) throws SQLException, Exception{
        Connection connex=null;
        PreparedStatement pst=null;
         String sql="UPDATE offre SET activation=false where id="+id;
         
        try{
            connex=Connexion.getConn();
            pst=connex.prepareStatement(sql);
            int i=pst.executeUpdate();
            //connex.commit();
        }
        catch(Exception ex){
            connex.rollback();
            throw ex;
        }finally{
            if(pst!=null)pst.close();
            if(connex!=null)connex.close();            
        }
    }
        public static void achatOffre(int idPuce,double prix,String modePayement) throws Exception{
            if(modePayement.equalsIgnoreCase("credit")){ 
                Utilisateur.updateSolde(prix, idPuce);
            }
            else{
                Compte.updateCompte(prix, idPuce);
            }
            
        }
        
        //public static void validation(){
        
       // }
}
