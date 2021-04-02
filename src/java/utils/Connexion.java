/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PHAEL
 */
public class Connexion {
    final  private static String host = "ec2-54-235-108-217.compute-1.amazonaws.com";
	  final  private static String user = "gjfnjlulmfotzg";
	  final  private static String passwd = "5eef60694e2070cc8890af57cd2acb45ded727d0c5e5555f3f809da92a9f13e4";
	  
	  public  static Connection getConn() throws Exception {
		  Connection connect = null;
	    try {
	      // This will load the MySQL driver, each DB has its own driver
	     
	      // Setup the connection with the DB
	      
               Class.forName("org.postgresql.Driver");
           
           String url = "jdbc:postgresql://ec2-54-235-108-217.compute-1.amazonaws.com:5432/d97r24ki57pkjl?password=5eef60694e2070cc8890af57cd2acb45ded727d0c5e5555f3f809da92a9f13e4&sslmode=require&user=gjfnjlulmfotzg&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
            connect = DriverManager.getConnection(url);
	      // Statements allow to issue SQL queries to the database
	      
	      
	    } catch (Exception e) {
	      throw e;
	    } 
	    return connect;
	  }

   
    
}
