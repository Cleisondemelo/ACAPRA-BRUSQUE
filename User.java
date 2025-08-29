package com.acapra.console.model;

public class User {
    private int id;
    private String nome;
    private String email;
    private String senhaHash;
    private java.sql.Timestamp criadoEm;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public java.sql.Timestamp getCriadoEm() { return criadoEm; }
    public void setCriadoEm(java.sql.Timestamp criadoEm) { this.criadoEm = criadoEm; }

    @Override
    public String toString() {
        return "User id= " + id + ", nome = '" + nome + " ', email = ' " + email +  "', criado em = " +  criadoEm +  " }";
    }
}
