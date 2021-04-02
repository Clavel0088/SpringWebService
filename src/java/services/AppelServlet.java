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
import models.Appel;
import models.NullResult;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;


@WebServlet(name = "AppelServlet", urlPatterns = {"/appel"})
public class AppelServlet extends HttpServlet {

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
            out.println("<title>Servlet AppelServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AppelServlet at " + request.getContextPath() + "</h1>");
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
        Gson json =new Gson();
         String etat="";
         String date="";
         int duree=0;
         int appelant=0;
         int appeler=0;
         int idUtilisateur = 0;
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
                    if(item.isFormField() && name.equals("idU"))
                    {
                        String idut = Streams.asString(item.openStream());
                        if(idut != null)
                        {
                            idUtilisateur = Integer.valueOf(idut);
                        }
                    }
                    if(item.isFormField() && name.equals("date"))
                    {
                        date = Streams.asString(item.openStream());
                       
                    }
                     if(item.isFormField() && name.equals("duree"))
                    {
                        String dur = Streams.asString(item.openStream());
                        if(dur != null)
                        {
                            duree = Integer.valueOf(dur);
                        }
                       
                    }
                    if(item.isFormField() && name.equals("appelant"))
                    {
                        String idAppelant = Streams.asString(item.openStream());
                        if(idAppelant != null)
                        {
                            appelant = Integer.valueOf(idAppelant);
                        }
                    }
                    if(item.isFormField() && name.equals("appeler"))
                    {
                        String idAppeler = Streams.asString(item.openStream());
                        if(idAppeler != null)
                        {
                            appeler = Integer.valueOf(idAppeler);
                        }
                    }
                }
                // raha etat=liste 
                if(etat.equalsIgnoreCase("liste")){
                 
                   ArrayList<Appel> appel=Appel.findByIdUtilisateur(idUtilisateur);
                   NullResult nullResult = new NullResult(200, " succes",appel, false);
                   String jsonData=json.toJson(nullResult);
                   PrintWriter out=response.getWriter();
                   out.println(jsonData);
                 } 
                
         // ito ilay ajout,, nasiko etat ilay izy de miditra ato raha etat=ajout
                else if(etat.equalsIgnoreCase("ajout")){
                    try {
                        Appel.ajoutAppel(date, duree, appelant, appeler);
                        ArrayList<Appel> appel=Appel.findByIdUtilisateur(idUtilisateur);
                        NullResult nullResult = new NullResult(200, " succes",appel, false);
                        String jsonData=json.toJson(nullResult);
                        PrintWriter out=response.getWriter();
                        out.println(jsonData);
                    } catch (Exception ex) {
                         ex.printStackTrace();
                         
                         NullResult nullResult = new NullResult(200, " echoué", null, false);
                         String jsonData=json.toJson(nullResult);
                         PrintWriter out=response.getWriter();
                         out.println(jsonData);
                    }
                }
              
                else return;
         }
         catch (Exception ex) {
                ex.printStackTrace();
                NullResult nullResult = new NullResult(200, " echoué", null, false);
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
