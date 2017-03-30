/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;
/**
 *
 * @author Ariel Reis
 * 
 * Isso constrói a conexão com o banco de dados MySQL
 */
public class MySQLConnection {
    private static String hostname;
    private static String port;
    private static String user;
    private static String password;
    private static String database;
    private static Connection connection;
    
    public void fecharConexao(){
        try{
          connection.close();
        }catch(Exception e){
            System.out.println("##\t ERRO \t##");
            System.out.println("Sua conexão já está fechada");
            System.out.println(e.toString());
        }
    }
    
    public void abrirConexao(){
        String driverName = "com.mysql.jdbc.Driver";
       try{
           Class.forName(driverName);
       }catch(Exception e){
           System.out.println("##\t ERRO \t##\n" + e.toString());
       }
       String url = "jdbc:mysql://" + MySQLConnection.hostname + ":" + MySQLConnection.port + "/" + MySQLConnection.database;
       try{
           connection = DriverManager.getConnection(url, user, password);
           if(connection != null){
               System.out.println("##\t CONEXÃO REALIZADA \t##");
           }else{
               System.out.println("##\t ERRO \t##");
               System.out.println("O banco não está inicializado");
           }
       }catch(SQLException e){
           System.out.println("##\t ERRO \t##");
           System.out.println(e.toString());
       }catch(Error e){
           System.out.println("##\t ERRO \t##");
           System.out.println(e.toString());
       }catch(Exception e){
           System.out.println("##\t ERRO \t##");
           System.out.println(e.toString());
       }
    }
    
    public MySQLConnection(String hostname, String port, String user, String password, String database){
       this.hostname = hostname;
       this.port = port;
       this.user = user;
       this.password = password;
       this.database = database;
       abrirConexao();
    }

   /**
    * Este método retorna um result set de uma consulta aberta.\n
    * Usa o objeto connection que foi instanciado. (Caso esteja na tela login, será conexaoPrincipal)
    * 
    * 
    * @param query SQL para ser executado no banco
    * @return 
    */
   public ResultSet abrirConsulta(String query){
       ResultSet rs =  null;
       try{
           Statement ps = connection.createStatement();
           rs = ps.executeQuery(query);          
        }catch(SQLException e){
           System.out.println("##\t ERRO \t##");
           System.out.println(e.toString());
        }catch(Exception e){
           System.out.println("##\t ERRO \t##");
           System.out.println(e.toString());
        }
        return rs;
   }
   
   protected static Connection getConnection() {
       return connection;
   }
 
   
   
}
