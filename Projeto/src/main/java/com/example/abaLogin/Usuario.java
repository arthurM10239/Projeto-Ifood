package com.example.abaLogin;

public class Usuario {
    private String acesso;
    private String nome;
    private String email;
    private String senha;
    
    public Usuario(String acesso, String nome, String email, String senha){
        this.acesso = acesso;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getAcesso() {
        return acesso;
    }

    public void setAcesso(String acesso) {
        this.acesso = acesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
     

    public String exibirInformacoes(){

        int[] tamanhos = {this.nome.length(),this.acesso.length(),this.email.length()};
        int valorAntigo = tamanhos[0];
        int maiorValor = 0;
        for (int i = 0; i < 3;i++) {
            if (tamanhos[i] > valorAntigo) {
                maiorValor = tamanhos[i];
            }    
            valorAntigo = tamanhos[i];
        }

        String borda = new String();
        for(int i = 0; i < maiorValor+20;i++){
            if(i == 0 || i == 16 || i == maiorValor+19){
                borda += "+";
            }else{
                borda+= "-";
            }
        }

        return String.format(
        "%s"+
        "\n|     Nome      | %-"+maiorValor+"s |\n"+
          "| TipoDeUsuario | %-"+maiorValor+"s |\n"+
          "|     Email     | %-"+maiorValor+"s |\n"+
        "%s"
        ,borda,this.nome,this.acesso,this.email,borda);
    }
}
