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
public class Appel {
  private int id;
    private String dateAppel;
    private int duree;
   private Utilisateur appelant;
   private Utilisateur appeler;

    public Appel() {
    }

    public Appel(int id, String dateAppel, int duree, Utilisateur appelant, Utilisateur appeler) {
        this.id = id;
        this.dateAppel = dateAppel;
        this.duree = duree;
        this.appelant = appelant;
        this.appeler = appeler;
    }

    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateAppel() {
        return dateAppel;
    }

    public void setDateAppel(String dateAppel) {
        this.dateAppel = dateAppel;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

   
    public Utilisateur getAppelant() {
        return appelant;
    }

    public void setAppelant(Utilisateur appelant) {
        this.appelant = appelant;
    }

    public Utilisateur getAppeler() {
        return appeler;
    }

    public void setAppeler(Utilisateur appeler) {
        this.appeler = appeler;
    }

   
    
    public static ArrayList<Appel> findAll() throws Exception{
       ArrayList<Appel> list=new ArrayList<>();
        Gson gson=new Gson();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from appel");
            while(rs.next()){
                int id=rs.getInt("id");
                 String date= rs.getString("date");
                 int duree= rs.getInt("duree");
                 int idAppelant =rs.getInt("appelant");
                 int idAppeler=rs.getInt("appeler");
                 Utilisateur appelant=Utilisateur.findById(idAppelant);
                 Utilisateur appeler=Utilisateur.findById(idAppeler);
                Appel appel=new Appel(id,date,duree,appelant,appeler);
                list.add(appel);
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
    
    public static Appel findById(int idApp) throws Exception{
       ArrayList<Appel> list=new ArrayList<>();
        Gson gson=new Gson();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from appel where id="+idApp);
            while(rs.next()){
                int id=rs.getInt("id");
                 String date= rs.getString("date");
                 int duree= rs.getInt("duree");
                 int idAppelant =rs.getInt("appelant");
                 int idAppeler=rs.getInt("appeler");
                 Utilisateur appelant=Utilisateur.findById(idAppelant);
                 Utilisateur appeler=Utilisateur.findById(idAppeler);
                Appel appel=new Appel(id,date,duree,appelant,appeler);
                return appel;
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
    
    public static  ArrayList<Appel> findByIdUtilisateur(int idUtilisateur) throws Exception{
       ArrayList<Appel> list=new ArrayList<>();
        Gson gson=new Gson();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select * from appel where appel.appelant="+idUtilisateur);
            while(rs.next()){
                int id=rs.getInt("id");
                 String date= rs.getString("date");
                 int duree= rs.getInt("duree");
                 int idAppelant =rs.getInt("appelant");
                 int idAppeler=rs.getInt("appeler");
                 Utilisateur appelant=Utilisateur.findById(idAppelant);
                 Utilisateur appeler=Utilisateur.findById(idAppeler);
                Appel appel=new Appel(id,date,duree,appelant,appeler);
                list.add(appel);
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            throw e;
            
        }
        finally{
            connex.close();
            stm.close();
        }
       return list;
   }
    
     public static void ajoutAppel(String date,int duree,int appelant,int appeler) throws Exception{
      Connection con =null;
        PreparedStatement pst=null;
        PreparedStatement pst1=null;
        try{
            con=Connexion.getConn();
            con.setAutoCommit(false);
            pst = con.prepareStatement("INSERT INTO appel (date,duree,appelant,appeler) VALUES(?,?,?,?)");
            pst.setDate(1,new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
            pst.setInt(2, duree);
            pst.setInt(3, appelant);
            pst.setInt(4, appeler);
            pst.executeUpdate();
            
             //UPDATE CREDIT
            //Verifier si meme operateur
            Utilisateur uappelant = Utilisateur.findById(appelant);
            Utilisateur uappele = Utilisateur.findById(appeler);
            double montant=0;
            if (uappelant.getOperateur().getId() == uappele.getOperateur().getId()){
                montant=duree*uappelant.getOperateur().getPrixMemeOp();
                if(montant>uappelant.getCredit()){
                 pst1 = con.prepareStatement("UPDATE utilisateur set credit = ? where id=?");
                 pst1.setDouble(1,0);
                 pst1.setInt(2,appelant);
                 pst1.executeUpdate();   
                }
                else{
                    
                 pst1 = con.prepareStatement("UPDATE utilisateur set credit = ? where id=?");
                 pst1.setDouble(1,montant);
                 pst1.setInt(2,appelant);
                 pst1.executeUpdate();
                }
                 
            }
            else{
                montant=duree*uappelant.getOperateur().getPrixAutre();
                
                 if(montant>uappelant.getCredit()){
                 pst1 = con.prepareStatement("UPDATE utilisateur set credit = ? where id=?");
                 pst1.setDouble(1,0);
                 pst1.setInt(2,appelant);
                 pst1.executeUpdate();   
                }
                else{
                    
                 pst1 = con.prepareStatement("UPDATE utilisateur set credit = ? where id=?");
                 pst1.setDouble(1,montant);
                 pst1.setInt(2,appelant);
                 pst1.executeUpdate();
                }
              }
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
