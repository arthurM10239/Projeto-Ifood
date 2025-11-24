package com.example.abaLogin;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Acesso {
    
    @FXML
    private Label exibirAcesso;
    @FXML
    private Label exibirEmail;
    @FXML
    private Label exibirNome;
    @FXML
    private Label exibirSenha;

    public void setDados(Usuario conta){
        exibirAcesso.setText("Tipo De Usuario: " + conta.getAcesso());
        exibirNome.setText("Nome: " + conta.getNome());
        exibirEmail.setText("Email: " + conta.getEmail());
        exibirSenha.setText("Senha: " + conta.getSenha());
    }
}
