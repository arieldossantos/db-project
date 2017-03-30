/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 *
 * @author Ariel Reis
 * 
 * Criptografa a senha do usuário
 */
public class QEncrypt {
    private String encrypted;
    private String decrypted;
    
    public QEncrypt(String password){
        byte[] encrypt = Base64.getEncoder().encode(password.getBytes());
        this.encrypted = new String(encrypt);
        
        byte[] decrypt = Base64.getDecoder().decode(encrypt);
        this.decrypted = new String(decrypt);
    }

    public String getEncrypted() {
        return encrypted;
    }

    /**
     * Não use
     * @deprecated 
     * @return 
     */
    public String getDecrypted() {
        return null;
    }
    
    
}
