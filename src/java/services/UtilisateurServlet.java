/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.NullResult;
import models.Utilisateur;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;


@WebServlet(name = "UtilisateurServlet", urlPatterns = {"/utilisateur"})
public class UtilisateurServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UtilisateurServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UtilisateurServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
       String etat="";
        String nom="";//request.getParameter("nom");
            String login="";//request.getParameter("login");
            String mdp="";//request.getParameter("mdp");
            int idOperateur=0;
        Gson json=new Gson();
        try {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator iter = upload.getItemIterator(request);
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    String name = item.getFieldName();
                    if(item.isFormField() && name.equals("etat"))
                    {
                        etat = Streams.asString(item.openStream());
                    }
                    if(item.isFormField() && name.equals("nom"))
                    {
                        nom = Streams.asString(item.openStream());
                    }
                    if(item.isFormField() && name.equals("login"))
                    {
                        login = Streams.asString(item.openStream());
                    }
                    if(item.isFormField() && name.equals("mdp"))
                    {
                        mdp = Streams.asString(item.openStream());
                    }
                   
                    if(item.isFormField() && name.equals("idOperateur"))
                    {
                       String idOp = Streams.asString(item.openStream());
                        if(idOp!= null)
                        {
                            idOperateur = Integer.valueOf(idOp);
                        }
                    }
            
                }
        if(etat.equalsIgnoreCase("save")){
            try {
                String newPhoneNumber = getRandomNumber(idOperateur);
                Utilisateur.save(nom,login,mdp,newPhoneNumber,idOperateur);
                NullResult nullResult = new NullResult(200, "Vous etes bien inscrits , votre numero est le "+newPhoneNumber, null, false);
                String jsonData=json.toJson(nullResult);
                PrintWriter out=response.getWriter();
                out.println(jsonData);
            } catch (Exception ex) {
                ex.printStackTrace();
                NullResult nullResult = new NullResult(404, "insertion echouée", null, true);
                String jsonData=json.toJson(nullResult);
                PrintWriter out=response.getWriter();
                out.println(jsonData);
            }
        }
        else if(etat.equalsIgnoreCase("login")){
            try {
            Utilisateur utilisateur=Utilisateur.login(login,mdp);
            if(utilisateur != null)
            {
                NullResult nullResult = new NullResult(200, "succes", utilisateur, false);
                        
                String jsonData=json.toJson(nullResult);
                PrintWriter out=response.getWriter();
                out.println(jsonData);
            }
            else
            {
                
                NullResult nullResult = new NullResult(404, "utilisateur introuvable", null, true);        
                String jsonData=json.toJson(nullResult);
                PrintWriter out=response.getWriter();
                out.println(jsonData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        }
        else{
            try {
                ArrayList<Utilisateur> list=Utilisateur.findAll();
                NullResult nullResult = new NullResult(200, "succes", list, false);
                String jsonData=json.toJson(nullResult);
                PrintWriter out=response.getWriter();
                out.println(jsonData);
            } catch (Exception ex) {
                ex.printStackTrace();
                NullResult nullResult = new NullResult(404, "echouée", null, true);
                String jsonData=json.toJson(nullResult);
                PrintWriter out=response.getWriter();
                out.println(jsonData);
            }
        
        }
    }catch (Exception ex) {
                ex.printStackTrace();
                NullResult nullResult = new NullResult(404, "echouée", null, true);
                String jsonData=json.toJson(nullResult);
                PrintWriter out=response.getWriter();
                out.println(jsonData);
            }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
   
    private String getRandomNumber(int idOperateur) {
        Random r = new Random();
      String phoneNumber ="";
      if(idOperateur == 1)
      {
         //telma 
          phoneNumber +="034";
      }
      else if(idOperateur == 2)
      {
          //airtel
          phoneNumber +="033";
      }
      else if(idOperateur == 3)
      {
          //orange
          phoneNumber +="032";
      }
      for(int i =0; i <7 ; i ++)
      {
        int numberRandomTemp = r.nextInt(10); // returns random number between 0 and 9
        phoneNumber+=numberRandomTemp;
       
      }
      return phoneNumber;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
