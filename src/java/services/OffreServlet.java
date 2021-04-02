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
import models.AchatOffre;
import models.NullResult;
import models.Offre;
import models.Utilisateur;
import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

@WebServlet(name = "OffreServlet", urlPatterns = {"/offre"})
public class OffreServlet extends HttpServlet {

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
            out.println("<title>Servlet OffreServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet OffreServlet at " + request.getContextPath() + "</h1>");
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
        
        String type=request.getParameter("type");
        if(type!=null && type.equalsIgnoreCase("ajout")){
             Gson json=new Gson();
            try {
                
                Offre.ajoutOffre(request.getParameter("nom"),request.getParameter("code"),Double.parseDouble(request.getParameter("prix")),Integer.parseInt(request.getParameter("validite")));
                NullResult nullResult = new NullResult(200, "succes","", false);
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
            Gson json=new Gson();
            try {
                ArrayList<Offre> listOffre=Offre.findAll();
                NullResult nullResult = new NullResult(200, "succes",listOffre, false);
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
        /*if(etat.equalsIgnoreCase("desactiver")){
            int idOffre=Integer.parseInt(request.getParameter("idOffre"));
            try {
                Offre.desactiverOffre(idOffre);
                 NullResult nullResult = new NullResult(200, "succes",null, false);
                    String jsonData=json.toJson(nullResult);
                    PrintWriter out=response.getWriter();
                    out.println(jsonData);
            } catch (Exception ex) {
               ex.printStackTrace();
               NullResult nullResult = new NullResult(200, "echoué",null, true);
                    String jsonData=json.toJson(nullResult);
                    PrintWriter out=response.getWriter();
                    out.println(jsonData);
            }
        }*/
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
          Gson json=new Gson();
          int id = 0;
          String type="";
          int idOffre = 0;
            try {
                ServletFileUpload upload = new ServletFileUpload();
                FileItemIterator iter = upload.getItemIterator(request);
                while (iter.hasNext()) {
                    FileItemStream item = iter.next();
                    String name = item.getFieldName();
                    if(item.isFormField() && name.equals("id"))
                    {
                        String idString = Streams.asString(item.openStream());
                        if(idString!= null){
                            id = Integer.valueOf(idString);
                        }
                    }
                     if(item.isFormField() && name.equals("idOffre"))
                    {
                        String idO = Streams.asString(item.openStream());
                        if(idO!= null){
                            idOffre = Integer.valueOf(idO);
                        }
                    }
                    if(item.isFormField() && name.equals("type"))
                    {
                        String typeO = Streams.asString(item.openStream());
                        if(typeO!= null){
                            type = String.valueOf(typeO);
                        }
                    }
                }
                if(type.equalsIgnoreCase("liste")){
                    ArrayList<Offre> listOffre=AchatOffre.findByIdUtilisateur(id);
                    NullResult nullResult = new NullResult(200, "succes",listOffre, false);
                    String jsonData=json.toJson(nullResult);
                    PrintWriter out=response.getWriter();
                    out.println(jsonData);
                }
                else if(type.equalsIgnoreCase("ajout")){
                    //Verification si credit insuffisant
                    Utilisateur u = Utilisateur.findById(id);
                    Offre o = Offre.findById(idOffre);
                    NullResult nullResult = null;
                    if(u.getCredit()>= o.getPrix()){
                        AchatOffre.ajoutAchatOffre(id,idOffre);
                        Utilisateur.updateSolde(u.getCredit()-o.getPrix(), id);
                        nullResult = new NullResult(200, "succes",null, false);
                        String jsonData=json.toJson(nullResult);
                        PrintWriter out=response.getWriter();
                        out.println(jsonData);
                    }
                    else{
                        nullResult = new NullResult(403, "Votre credit est insuffisant",null, true);
                        String jsonData=json.toJson(nullResult);
                        PrintWriter out=response.getWriter();
                        out.println(jsonData);
                    }
                }
                else {
                     //Verification si credit insuffisant
                    ArrayList<Offre> listOffre=Offre.findAll();
                    NullResult nullResult = new NullResult(200, "succes",listOffre, false);
                    String jsonData=json.toJson(nullResult);
                    PrintWriter out=response.getWriter();
                    out.println(jsonData);
                   
                }
               
            } catch (Exception ex) {
                ex.printStackTrace();
                    NullResult nullResult = new NullResult(403, "echoué",null, true);
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
