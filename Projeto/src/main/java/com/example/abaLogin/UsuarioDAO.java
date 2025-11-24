package com.example.abaLogin; // Ajuste o pacote conforme necessário

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    
    // Método para inserir um novo usuário no banco de dados
    public void inserirUsuario(Usuario usuario) throws SQLException {
        
        String sql = "INSERT INTO users (username, email, password_, role) VALUES (?, ?, ?, ?)";
        
        // Uso de try-with-resources para garantir que a Connection e o PreparedStatement sejam fechados
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome()); 
            
            stmt.setString(2, usuario.getEmail()); 
            
            stmt.setString(3, usuario.getSenha()); 

            stmt.setString(4, usuario.getAcesso()); 
            
            stmt.executeUpdate(); 
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário no banco de dados: " + e.getMessage());
            // Você pode querer logar a exceção ou relançá-la com uma mensagem mais amigável
            throw new SQLException("Falha ao registrar o usuário.", e);
        }
    }
    public boolean emailExiste(String email) throws SQLException {
        String sql = "SELECT COUNT(email) FROM users WHERE email = ?";
    
        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Se o COUNT for maior que 0, o email existe.
                    return rs.getInt(1) > 0; 
                }
                return false;
            }
        }
    }
    public boolean nomeUsuarioExiste(String username) throws SQLException {
        String sql = "SELECT COUNT(username) FROM users WHERE username = ?";
    
        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
                return false;
            }
        }
    }


    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT username, email, password_, role FROM users WHERE email = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // ... (Criação do objeto Usuario)
                    String senhaHash = rs.getString("password_"); 
                    // Retorna um objeto Usuario que contém o HASH da senha no campo 'senha'
                    return new Usuario(rs.getString("role"), rs.getString("username"), email, senhaHash); 
                }
                return null; // Usuário não encontrado
            }
        }
    }
    public Usuario buscarPorNome(String username) throws SQLException {
        String sql = "SELECT username, email, password_, role FROM users WHERE username = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // ... (Criação do objeto Usuario)
                    String senhaHash = rs.getString("password_"); 
                    // Retorna um objeto Usuario que contém o HASH da senha no campo 'senha'
                    return new Usuario(rs.getString("role"), username, rs.getString("email"), senhaHash); 
                }
                return null; // Usuário não encontrado
            }
        }
    }

    public Usuario verificarLogin(String usrEmail, String senhaTextoPuro) throws SQLException {
        
        // 1. Busca o usuário pelo usernameEmail (recupera o HASH da senha)
        Usuario usuario = new Usuario(null,null, null, null);
        if (usrEmail.contains("@gmail.com")) {
            usuario = buscarPorEmail(usrEmail);    
        } else {
            usuario = buscarPorNome(usrEmail);
        }
        
        if (usuario == null) {
            return null; // Usuário não existe
        }
        
        String senhaHashSalva = usuario.getSenha(); // Obtém o hash
        
        // Se o login for válido, retorna TRUE
        if (senhaTextoPuro.equals(senhaHashSalva)) {
            return usuario; // Login bem-sucedido
        } else {
            return null; // Senha incorreta
        }
    }
    
}