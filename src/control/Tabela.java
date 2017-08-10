package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.projetobd.TelaLogin;

/**
 *
 * @author Ariel Reis
 */
public class Tabela {
    private String[] columnLabels = null;
    private int[] columnTypes = null;
    private int tableColumnsLength;
    private int tableRowLength;
    
    public Tabela(){
        
    }
    /**
     * Retorna a quantidade de colunas na tabela
     * 
     * @param rs ResultSet de uma conexão
     * @return Retorna a quantidade de colunas da tabela
     * @throws SQLException 
     */
    public int getTableColumnsLength(ResultSet rs) throws SQLException{
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        tableColumnsLength = columnCount;
        return columnCount;
    }
    
    /**
     * Busca os nome de todas as colunas da tabela
     * 
     * @param rs ResultSet de uma conexão
     * @return Retorna um array de String com o nome de todas as colunas
     * @throws SQLException 
     */
    public String[] getColumnLabel(ResultSet rs) throws SQLException{
        rs.beforeFirst();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        String columns[] = new String[columnCount];

        for(int i = 0; i < columnCount; i++){
            columns[i] = resultSetMetaData.getColumnLabel(i + 1);
        }
        columnLabels = columns;
        return columns;
   }
    /**
     * Busca os tipos de todas as colunas da tabela, em ordem
     * 
     * @param rs ResultSet de uma conexão
     * @return Retorna um array de int com o tipo das colunas em ordem (deve ser comparado com o java.sql.Types)
     * @throws SQLException 
     */
    public int[] getColumnType(ResultSet rs) throws SQLException{
        rs.beforeFirst();
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int columnCount = resultSetMetaData.getColumnCount();
        int columns[] = new int[columnCount];

        for(int i = 0; i < columnCount; i++){
            columns[i] = resultSetMetaData.getColumnType(i + 1);
        }
        columnTypes = columns;
        return columns;
   }
    
    
    /**
     * Busca os dados de toda a tabela e os retorna numa Matriz sendo: Horizontal a ordem dos campos da tabela
     * e Vertical os dados destes campos
     * 
     * @param rs ResultSet
     * @return Retorna uma Matriz com todos os dados da tabela
     * @throws Exception 
     */
    public String[][] getAllDataFromTable(ResultSet rs) throws Exception{
    int row = 0;
    getTotalRow(rs);
    getColumnLabel(rs);
    getColumnType(rs);
    getTableColumnsLength(rs);
    if(tableRowLength == 0){
        return null;
    }
    String[][] tabelaFull = new String[tableRowLength][tableColumnsLength];
    rs.beforeFirst();
      while(rs.next()){
        for(int i = 0; i < tableColumnsLength; i++){
           switch(columnTypes[i]){
                case Types.INTEGER:
                    tabelaFull[row][i] = String.valueOf(getTableDataInt(rs, i + 1));
                    break;
                case Types.FLOAT:
                    tabelaFull[row][i] = String.valueOf(getTableDataFloat(rs, i + 1));
                    break;
                case Types.VARCHAR:
                    tabelaFull[row][i] = getTableDataVarchar(rs, i + 1);
                    break;
                case Types.DATE:
                    tabelaFull[row][i] = String.valueOf(getTableDataDate(rs, i + 1));
                    break;
                case Types.CHAR:
                    tabelaFull[row][i] = String.valueOf(getTableDataEnum(rs, i + 1));
                    break;
                default:
                    tabelaFull[row][i] = "";
          }
        }
        row++;
      }
      return tabelaFull;
    }
    
    /**
     * Como o próprio nome diz, retorna o valor de um campo Varchar
     * 
     * @return 
     */
    private String getTableDataVarchar(ResultSet rs, int index){
        try {
            return rs.getString(index);
        } catch (SQLException ex) {
            return null;
        }
    }
    
    /**
     * Como o próprio nome diz, retorna o valor de um campo Inteiro
     * 
     * @return 
     */
    private int getTableDataInt(ResultSet rs, int index){
        try {
            return rs.getInt(index);
        } catch (SQLException ex) {
            throw new Error("Você bugou a desgraça");
        }
    }
    
    /**
     * Como o próprio nome diz, retorna o valor de um campo Float
     * 
     * @return 
     */
    private float getTableDataFloat(ResultSet rs, int index){
        try {
            return rs.getFloat(index);
        } catch (SQLException ex) {
            throw new Error("Você bugou a desgraça");
        }
    }
    
    /**
     * Como o próprio nome diz, retorna o valor de um campo Char
     * 
     * @return 
     */
    private char getTableDataEnum(ResultSet rs, int index){
        try{
            return rs.getString(index).charAt(0);
        }catch(Exception e){
            return '§';
        }
    }
    
    /**
     * Como o próprio nome diz, retorna o valor de um campo Data
     * 
     * @return 
     */
    private Date getTableDataDate(ResultSet rs, int index){
        try{
            return rs.getDate(index);
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * Diz qual a quantidade de linhas tem na tabela
     * 
     * @param rs
     * @throws SQLException 
     */
    private void getTotalRow(ResultSet rs) throws SQLException{
        int count = 0;
        while(rs.next()){
            count++;
        }
        tableRowLength = count;
    }
    
    /**
     * Como o próprio nome diz, insere dados em uma tabela.
     * 
     * @author Alan Felix 
     * @param campos Campos para inserir os valores
     * @param dados Valores a serem inseridos
     * @param tabela Tabela onde será inserido os valores
     */
    public void inserirDadosNaTabela(String[] campos, String[] dados, String tabela){
        String camposSQL = "";
        for(int i = 0; i < campos.length; i++){
            camposSQL = camposSQL + campos[i] + ", ";
        }
        camposSQL = camposSQL.substring(0, camposSQL.length() - 2);
        
        String dadosSQL = "";
        for(int i = 0; i < dados.length; i++) {
            dadosSQL = dadosSQL + "?, ";
        }
        dadosSQL = dadosSQL.substring(0, dadosSQL.length() - 2);
        
        String select = "SELECT " + camposSQL + " from `" + tabela + "`";
        int tipos[] = null;
        try {
            tipos = getColumnType(TelaLogin.conexaoPrincipal.abrirConsulta(select));
            String insert = "INSERT INTO " + tabela + " (" + camposSQL + ") values (" + dadosSQL + ");";
            PreparedStatement ps = MySQLConnection.getConnection().prepareStatement(insert);
            for(int i = 0; i < tipos.length; i++){
                switch(tipos[i]){
                     case Types.INTEGER:
                         ps.setInt(i + 1, Integer.parseInt(dados[i]));
                         break;
                     case Types.FLOAT:
                         ps.setFloat(i + 1, Float.parseFloat(dados[i]));
                         break;
                     case Types.VARCHAR:
                         ps.setString(i + 1, dados[i]);
                         break;
                     case Types.DATE:
                         ps.setDate(i + 1, java.sql.Date.valueOf(dados[i]));
                         break;
                     case Types.CHAR:
                         ps.setString(i + 1, dados[i]);
                         break;
                     default:
                         ps.setString(i + 1, dados[i]);
                }
            }
            ps.execute();
        } catch (Exception e) {
            //manda pra casa da desgraça
            System.out.println(e.toString());
        }
    }  
}
