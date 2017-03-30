package main.projetobd.subtelas;

import java.sql.Date;

/**
 *
 * @author Ariel
 */
public class Cliente {
    private String nome;
    private String endereco;
    private String email;
    private char sexo;
    private Date nascimento;
    private int matricula;

    public Cliente(String nome, String endereco, String email, char sexo, Date nascimento) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.sexo = sexo;
        this.nascimento = nascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Date getNascimento() {
        return nascimento;
    }

    public void setNascimento(Date nascimento) {
        this.nascimento = nascimento;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    
    
    
}
