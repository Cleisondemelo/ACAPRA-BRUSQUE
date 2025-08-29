package com.acapra.console.dao;

import com.acapra.console.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.example.Conexao;

public class UserDAO {
    public static String hash(String plain) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(plain.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean create(String nome, String email, String senhaPlain) {
        String sql = "INSERT INTO users (nome, email, senha_hash) VALUES (?,?,?)";
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, hash(senhaPlain));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar o usuário: " + e.getMessage());
            return false;
        }
    }

    public User login(String email, String senhaPlain) {
        String sql = "SELECT id, nome, email, senha_hash, criado_em FROM users WHERE email=?";
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (hash(senhaPlain).equals(rs.getString("senha_hash"))) {
                        User u = new User();
                        u.setId(rs.getInt("id"));
                        u.setNome(rs.getString("nome"));
                        u.setEmail(rs.getString("email"));
                        u.setSenhaHash(rs.getString("senha_hash"));
                        u.setCriadoEm(rs.getTimestamp("criado_em"));
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no login: " + e.getMessage());
        }
        return null;
    }

    public List<User> listAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id, nome, email, senha_hash, criado_em FROM users ORDER BY id DESC";
        try (Connection c = Conexao.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenhaHash(rs.getString("senha_hash"));
                u.setCriadoEm(rs.getTimestamp("criado_em"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar os usuários: " + e.getMessage());
        }
        return list;
    }
}

