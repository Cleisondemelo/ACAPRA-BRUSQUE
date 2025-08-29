package com.acapra.console.model;

public class Pet {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private Integer idade;
    private String descricao;
    private String fotoUrl;
    private String cidade;
    private Integer criadoPor;
    private java.sql.Timestamp criadoEm;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getFotoUrl() { return fotoUrl; }
    public void setFotoUrl(String fotoUrl) { this.fotoUrl = fotoUrl; }
    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
    public Integer getCriadoPor() { return criadoPor; }
    public void setCriadoPor(Integer criadoPor) { this.criadoPor = criadoPor; }
    public java.sql.Timestamp getCriadoEm() { return criadoEm; }
    public void setCriadoEm(java.sql.Timestamp criadoEm) { this.criadoEm = criadoEm; }

    @Override
    public String toString() {
        return "Pet{id=" + id + ", nome='" + nome + "', especie='" + especie + "', raca='" + raca + "', cidade='" + cidade + "'}";
    }
}
