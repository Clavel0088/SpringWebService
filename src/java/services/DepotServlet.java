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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Compte;
import models.NullResult;
import models.Transaction;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;


@WebServlet(name = "DepotServlet", urlPatterns = {"/depot"})
public class DepotServlet extends HttpServlet {

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
            out.println("<title>Servlet DepotServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DepotServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        double montant = 0;
            int id = 0;
            String type ="depot";
            double frais=0;
            String etat = "";
            String code = ""; 
            Gson json=new Gson();
            try {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator iter = upload.getItemIterator(request);
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    String name = item.getFieldName();
                    if(item.isFormField() && name.equals("montant"))
                    {
                        String montantPost = Streams.asString(item.openStream());
                        if(montantPost != null)
                        {
                            montant = Double.valueOf(montantPost);
                            frais = (int)montant* 0.1;
                        }
                    }
                    if(item.isFormField() && name.equals("id"))
                    {
                        String idPost = Streams.asString(item.openStream());
                        if(idPost != null)
                        {
                            id = Integer.valueOf(idPost);
                        }
                    }
                      if(item.isFormField() && name.equals("code"))
                    {
                        String codeS = Streams.asString(item.openStream());
                          if(codeS != null)
                        {
                            code = String.valueOf(codeS);
                        }
                    }
                    if(item.isFormField() && name.equals("etat"))
                    {
                        String typeT = Streams.asString(item.openStream());
                        if(typeT != null)
                        {
                            etat = String.valueOf(typeT);
                        }
                    }
                }
               if(etat.equalsIgnoreCase("liste")){
                   //list depot historique
                    json=new Gson();
                    try {
                        ArrayList<Transaction> listeTransaction=Transaction.findTransaction(type, id);
                        NullResult nullResult = new NullResult(200, "succes",listeTransaction, false);
                            String jsonData=json.toJson(nullResult);
                            PrintWriter out=response.getWriter();
                            out.println(jsonData);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                            NullResult nullResult = new NullResult(403, "echoué",null, true);
                            String jsonData=json.toJson(nullResult);
                            PrintWriter out=response.getWriter();
                            out.println(jsonData);
                    }
                }
                else{
                   
                   // Verification code secret
                   boolean codeOk = Compte.verifyCode(id,code);
                   if(codeOk){
                        //get solde
                        double solde = Compte.findById(id).getSolde();
                        //update solde
                        Compte.updateCompte(solde+montant, id);
                         //ajout depot
                        Transaction.ajoutTransaction(frais,id,montant, type);
                        NullResult nullResult = new NullResult(200, "Depot avec succes", null, false);
                        String jsonData=json.toJson(nullResult);
                        PrintWriter out=response.getWriter();
                        out.println(jsonData);
                   }
                   else{
                       NullResult nullResult = new NullResult(403, "Code secret incorrect",null, true);
                        String jsonData=json.toJson(nullResult);
                        PrintWriter out=response.getWriter();
                        out.println(jsonData);
                   }
                }
         } catch (Exception ex) {
             ex.printStackTrace();
            NullResult nullResult = new NullResult(404, "Depot echoué", null, true);        
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
