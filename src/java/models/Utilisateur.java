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
public class Utilisateur {
   private int id;
    private String nom;
    private String login;
    private String mdp;
    private String numero;
    private double credit;
    private Operateur operateur;

    //public Utilisateur(int id1, String nom1, String login1, String numero1) {

    public Utilisateur(int id, String nom, String login, String mdp, String numero, double credit, Operateur operateur) {
        this.id = id;
        this.nom = nom;
        this.login = login;
        this.mdp = mdp;
        this.numero = numero;
        this.credit = credit;
        this.operateur = operateur;
    }

   

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public Operateur getOperateur() {
        return operateur;
    }

    public void setOperateur(Operateur operateur) {
        this.operateur = operateur;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
  
    
    public static Utilisateur login(String login,String mdp) throws Exception{
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select u.*,o.nom as nomOperateur from utilisateur u join operateur o on u.idoperateur = o.id where login ='"+login+"' and mdp='"+mdp+"'");
            while(rs.next()){
                int id=rs.getInt("id");
                String nom= rs.getString("nom");
                String loginval= rs.getString("login");
                String mdpval= rs.getString("mdp");
                String numero= rs.getString("numero");
                double credit= rs.getDouble("credit");
                int idoperateur= rs.getInt("idoperateur");
                String nomoperateur= rs.getString("nomOperateur");
                Utilisateur utilisateur=new Utilisateur(id,nom,loginval,mdpval,numero,credit,new Operateur(idoperateur,nomoperateur));
                return utilisateur;
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
    
    public static void save(String nom,String login,String mdp,String numero,int idOperateur) throws Exception{
     Connection con =null;
        PreparedStatement pst=null;
        try{
            con=Connexion.getConn();
            con.setAutoCommit(false);
            pst = con.prepareStatement("INSERT INTO utilisateur (nom,login,mdp,numero,credit,idOperateur) VALUES(?,?,?,?,10000,?)");
            pst.setString(1,nom);
            pst.setString(2,login);
            pst.setString(3, mdp);
            pst.setString(4,numero);
            pst.setInt(5, idOperateur);
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
    
    public static void updateUtilisateur(String nom,String login,String mdp,int id) throws SQLException, Exception{
        Connection connex=null;
        PreparedStatement pst=null;
         String sql="UPDATE utilisateur SET nom=?,login=?,mdp=? where id="+id;
        
        try{
            connex=Connexion.getConn();
            pst=connex.prepareStatement(sql);
            pst.setString(1,nom);
            pst.setString(2, login);
            pst.setString(3, mdp);
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
    
    public static ArrayList<Utilisateur> findAll() throws Exception{
       ArrayList<Utilisateur> list=new ArrayList<>();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select u.*,o.nom as nomOperateur from utilisateur u join operateur o on u.idoperateur = o.id");
            while(rs.next()){
                int id=rs.getInt("id");
                String nom= rs.getString("nom");
                String loginval= rs.getString("login");
                String mdpval= rs.getString("mdp");
                String numero= rs.getString("numero");
                double credit= rs.getDouble("credit");
                int idoperateur= rs.getInt("idoperateur");
                String nomoperateur= rs.getString("nomOperateur");
                Utilisateur utilisateur=new Utilisateur(id,nom,loginval,mdpval,numero,credit,new Operateur(idoperateur,nomoperateur));
               
                list.add(utilisateur);
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
    
    
     public static Utilisateur findById(int idU) throws Exception{
       ArrayList<Utilisateur> list=new ArrayList<>();
        Connection connex=null;
        Statement stm=null;
        ResultSet rs=null;
        try{
            connex=Connexion.getConn();
            stm=connex.createStatement();
            rs=stm.executeQuery("select u.*,o.nom as nomOperateur from utilisateur u join operateur o on o.id = u.idoperateur where u.id="+idU);
            while(rs.next()){
                String nom= rs.getString("nom");
                String loginval= rs.getString("login");
                String mdpval= rs.getString("mdp");
                String numero= rs.getString("numero");
                double credit= rs.getDouble("credit");
                int idoperateur= rs.getInt("idoperateur");
                String nomoperateur= rs.getString("nomOperateur");
                Operateur operateur=Operateur.findById(idoperateur);
                Utilisateur utilisateur=new Utilisateur(idU,nom,loginval,mdpval,numero,credit,operateur);
                return utilisateur;
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
     
    
    public static void updateSolde(double montant,int id) throws SQLException, Exception{
        Connection connex=null;
        PreparedStatement pst=null;
         String sql="UPDATE utilisateur SET credit="+montant+" where id=?";
        
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
   
}
