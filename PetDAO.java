package com.acapra.console.dao;

import com.acapra.console.model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.Conexao;

public class PetDAO {
    public boolean create(Pet p) {
        String sql = "INSERT INTO pets (nome, especie, raca, idade, descricao, foto_url, cidade, criado_por) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEspecie());
            ps.setString(3, p.getRaca());
            if (p.getIdade() == null) ps.setNull(4, Types.INTEGER); else ps.setInt(4, p.getIdade());
            ps.setString(5, p.getDescricao());
            ps.setString(6, p.getFotoUrl());
            ps.setString(7, p.getCidade());
            if (p.getCriadoPor() == null) ps.setNull(8, Types.INTEGER); else ps.setInt(8, p.getCriadoPor());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) p.setId(rs.getInt(1));
                }
            }
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar o animal: " + e.getMessage());
            return false;
        }
    }

    public List<Pet> listAll() {
        List<Pet> out = new ArrayList<>();
        String sql = "SELECT id, nome, especie, raca, idade, descricao, foto_url, cidade, criado_por, criado_em FROM pets ORDER BY id DESC";
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        } catch (SQLException e) {
            System.out.println("Erro ao listar os animais: " + e.getMessage());
        }
        return out;
    }

    public List<Pet> listByUser(int userId) {
        List<Pet> out = new ArrayList<>();
        String sql = "SELECT id, nome, especie, raca, idade, descricao, foto_url, cidade, criado_por, criado_em FROM pets WHERE criado_por=? ORDER BY id DESC";
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) out.add(map(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os animais por usuário: " + e.getMessage());
        }
        return out;
    }

    private Pet map(ResultSet rs) throws SQLException {
        Pet p = new Pet();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setEspecie(rs.getString("especie"));
        p.setRaca(rs.getString("raca"));
        int idade = rs.getInt("idade");
        p.setIdade(rs.wasNull() ? null : idade);
        p.setDescricao(rs.getString("descricao"));
        p.setFotoUrl(rs.getString("foto_url"));
        p.setCidade(rs.getString("cidade"));
        int cp = rs.getInt("criado_por");
        p.setCriadoPor(rs.wasNull() ? null : cp);
        p.setCriadoEm(rs.getTimestamp("criado_em"));
        return p;
    }
}
