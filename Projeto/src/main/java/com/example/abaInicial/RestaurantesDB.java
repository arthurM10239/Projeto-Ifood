package com.example.abaInicial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.example.abaLogin.ConexaoDB;

public class RestaurantesDB {


    public void criarNovoRestaurante(){
        String sql = "insert into restaurantes(nome_restaurante,tipo_culinaria,num_avaliacoes,total_avaliacao)" +
                     "values(?,?,?,?);";
    }
    public ArrayList<Restaurante> buscarInfoRestaurante() {
        String sql = "SELECT * FROM restaurantes";

        ArrayList<Restaurante> listaRestaurantes = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = stmt.executeQuery()) { 

                while (rs.next()) {
                    
                    Restaurante restaurante = new Restaurante();
                    
                    restaurante = new Restaurante(
                    rs.getInt("id_restaurante"), 
                    rs.getString("nome_restaurante"),
                    rs.getString("dono_"),
                    rs.getString("tipo_culinaria"),
                    rs.getInt("num_avaliacoes"),
                    rs.getDouble("total_avaliacoes"),
                    rs.getDouble("media_avaliacao"));
                    
                    listaRestaurantes.add(restaurante); 
                }
                
            }

        }catch (SQLException e) {
            System.err.println("Erro SQL ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }

        
        return listaRestaurantes;
    }
    
    public ArrayList<Produto> buscarProdutosRestaurante(int idRestaurante) {
        String sql = "SELECT * FROM pratos WHERE id_restaurante = ?";

        ArrayList<Produto> listaProdutos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRestaurante);

            try (ResultSet rs = stmt.executeQuery()) { 

                while (rs.next()) {
                    
                    Produto produto = new Produto(
                    rs.getString("nome_prato"), 
                    rs.getString("descricao"),
                    rs.getDouble("preco"));
                    
                    listaProdutos.add(produto); 
                }
                
            }

        }catch (SQLException e) {
            System.err.println("Erro SQL ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }

        
        return listaProdutos;
    }

    public long buscarUltimaModificacao() {
        String sql = "SELECT MAX(data_atualizacao) AS ultima_modificacao FROM restaurantes";


        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Timestamp ts = rs.getTimestamp("ultima_modificacao");
                
                if (ts != null) {
                    return ts.getTime();
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar data de modificação: " + e.getMessage());
        }

        return 0;   
    }
    public int buscarModificacaoQuantRest() {
        String sql = "SELECT COUNT(*) AS total_restaurantes FROM restaurantes";
            
        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_restaurantes");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar contagem de restaurantes: " + e.getMessage());
        }

        return 0; 
    }

    public int buscarModificacaoQuantPratos() {
        String sql = "SELECT COUNT(*) AS total_pratos FROM pratos";
            
        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total_pratos");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar contagem de pratos: " + e.getMessage());
        }

        return 0; 
    }
}
