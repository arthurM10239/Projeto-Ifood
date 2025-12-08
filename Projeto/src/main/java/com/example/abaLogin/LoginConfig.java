package com.example.abaLogin;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

import com.example.Main;
import com.example.abaInicial.TelaInicialConfig;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Ellipse;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginConfig implements Initializable{
    
    private Usuario c1;
    private final UsuarioDAO usuario = new UsuarioDAO();
    private final EmailService emailService = new EmailService();
    private Usuario usuarioTemp; 
    private String codigoCorreto;
    private static final int COOLDOWN_SECONDS = 60; // 1 minuto
    private int tempoRestante = 0;
    private Timeline timeline;

    @FXML private Pane criarConta;
    @FXML private Pane entrarConta;

    @FXML private Label mudarCriarConta;
    @FXML private Label mudarEntrarConta;

    @FXML private Pane telaCompleta;

    @FXML private Button btnCriarConta;

    @FXML private Label txtCriarConta;
    @FXML private Label txtLogarConta;

    @FXML private Label checarDono;
    @FXML private Label checarCliente;
    @FXML private String tipoUsr;

    @FXML private TextField fieldNomeUsuario;
    @FXML private TextField fieldEmail;
    @FXML private TextField fieldSenha;
    @FXML private TextField fieldConfirmSenha;

    @FXML private PasswordField fieldsenhaescondida;
    @FXML private PasswordField fieldsenhaescondida1;
    @FXML private PasswordField fieldconfsenhaescondida;
    @FXML private Label bttVizualizarSenha;

    @FXML private TextField fieldUsuarioEmail;
    @FXML private TextField fieldEntrarSenha;

    @FXML private ImageView alertaConfirmSenha;
    @FXML private ImageView alertaEmail;
    @FXML private ImageView alertaSenha;
    @FXML private ImageView alertaTipo;
    @FXML private ImageView alertaUsuario;

    @FXML private Pane telaDeAviso;
    @FXML private Button botaoTelaAviso;
    @FXML private Label txtErroCadastro;
    @FXML private ImageView emailExistente;
    @FXML private ImageView nomeExistente;
    @FXML private Label dicaSenhaCriar;
    @FXML private Label dicaEmail;
    @FXML private Label dicaNomeUsuario;

    @FXML private Label dicaSenhaEntrar;
    @FXML private Label dicaUsuarioEmail;
    @FXML private ImageView alertaUsuarioEmail;
    @FXML private ImageView alertaEntrarSenha;
    @FXML private ImageView alertaUsuarioExistente;

    @FXML private TextField fieldCodigo;
    @FXML private Label labelErro;
    @FXML private Pane telaConfirmacaoEmail;
    @FXML private Pane carregando;
    @FXML private Pane carregando1;
    @FXML private Label txtEnviandoCodigo;
    
    @FXML private CubicCurve palpebra_olho;
    @FXML private CubicCurve palpebra_olho2;
    @FXML private Ellipse pupila;
    @FXML private Pane painelOlho;

    @FXML private CubicCurve palpebra_olho1;
    @FXML private CubicCurve palpebra_olho21;
    @FXML private Ellipse pupila1;
    @FXML private Pane painelOlho1;


    private double xOffset = 0;
    private double yOffset = 0;
    private double cima = 0.0;
    private double baixo = 0.0;

    //tudo que executa quando app é inicializado
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtCriarConta.setStyle("-fx-background-color: #881919;");
        observarAttInfos();
        cima = palpebra_olho.getControlY1();
        baixo = palpebra_olho2.getControlY1();

        telaCompleta.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (entrarConta.isVisible()) {
                    entrarConta(); 
                    System.out.println("Enter global: Acionando Login.");
                } else if (criarConta.isVisible()) {
                    criarConta();
                }
                event.consume(); 
            }
        });

    }
    //tudo que executa quando app é inicializado

    public void observarAttInfos(){
        fieldNomeUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!criarConta.isVisible()){return;}
            alertaUsuario.setVisible(fieldCondicao(fieldNomeUsuario));

            try {

                if(usuario.buscarPorNome(fieldNomeUsuario.getText()) != null){
                    dicaNomeUsuario.setVisible(true);   
                    dicaNomeUsuario.setText("Ja existe este usuario");    
                } else if (fieldNomeUsuario.getText().contains(" ")) {
                    dicaNomeUsuario.setVisible(true);
                    dicaNomeUsuario.setText("Evite espaço");
                } else if (fieldNomeUsuario.getText().length() > 15) {
                    dicaNomeUsuario.setVisible(true);
                    dicaNomeUsuario.setText("Maximo de 15 caracteres");
                }
                else{
                    dicaNomeUsuario.setVisible(false);   
                }
                
                nomeExistente.setVisible( usuario.buscarPorNome(fieldNomeUsuario.getText()) == null ? false : true);
                
            }catch (SQLException e) {
                System.err.println("ERRO DE BANCO DE DADOS: Falha ao registrar o usuário. " + e.getMessage());
            }
            
        });
        fieldEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!criarConta.isVisible()){return;}
            alertaEmail.setVisible(fieldCondicao(fieldEmail));
            

            try {
                
                if (usuario.buscarPorEmail(fieldEmail.getText() + "@gmail.com") != null) {
                    dicaEmail.setVisible(true);
                    dicaEmail.setText("este email ja esta sendo utilizado");
                }else if (fieldEmail.getText().contains("@gmail.com")) {
                    dicaEmail.setVisible(true);
                    dicaEmail.setText("sem @gmail.com");
                }else if (fieldEmail.getText().contains(" ")) {
                    dicaEmail.setVisible(true);
                    dicaEmail.setText("Evite espaço");
                } else {
                    dicaEmail.setVisible(false);
                }
                emailExistente.setVisible(usuario.buscarPorEmail(fieldEmail.getText() + "@gmail.com") == null ?false:true);
                 
            }catch (SQLException e) {
                System.err.println("ERRO DE BANCO DE DADOS: Falha ao registrar o usuário. " + e.getMessage());
            }

        });
        fieldSenha.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!criarConta.isVisible()){return;}
            alertaConfirmSenha.setVisible( fieldConfirmSenha.getText().equals(fieldSenha.getText()) ? false : true);
            alertaSenha.setVisible(fieldCondicao(fieldSenha));
            if (fieldSenha.getText().contains(" ")) {
                dicaSenhaCriar.setVisible(true);
                dicaSenhaCriar.setText("Evite espaço");
            }
            else if(fieldSenha.getText().length() < 8 && !fieldSenha.getText().isEmpty()){
                dicaSenhaCriar.setVisible(true);
                dicaSenhaCriar.setText("minimo de 8 caracteres");
            }
            else{
                dicaSenhaCriar.setVisible(false);
            }

            

        });
        fieldsenhaescondida.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!criarConta.isVisible()){return;}
            alertaConfirmSenha.setVisible( fieldConfirmSenha.getText().equals(fieldsenhaescondida.getText()) ? false : true);
            alertaSenha.setVisible(fieldCondicao(fieldsenhaescondida));
            if (fieldsenhaescondida.getText().contains(" ")) {
                dicaSenhaCriar.setVisible(true);
                dicaSenhaCriar.setText("Evite espaço");
            }
            else if(fieldsenhaescondida.getText().length() < 8 && !fieldsenhaescondida.getText().isEmpty()){
                dicaSenhaCriar.setVisible(true);
                dicaSenhaCriar.setText("minimo de 8 caracteres");
            }
            else{
                dicaSenhaCriar.setVisible(false);
            }

            

        });
        fieldConfirmSenha.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!criarConta.isVisible()){return;}
            alertaConfirmSenha.setVisible( fieldConfirmSenha.getText().equals(fieldSenha.getText()) ? false : true);
        });
        fieldconfsenhaescondida.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!criarConta.isVisible()){return;}
            alertaConfirmSenha.setVisible( fieldconfsenhaescondida.getText().equals(fieldSenha.getText()) ? false : true);
        });
        fieldUsuarioEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!entrarConta.isVisible()){return;}
            alertaUsuarioEmail.setVisible(fieldCondicao(fieldUsuarioEmail));
            try {
                if (fieldUsuarioEmail.getText().contains("@gmail.com")) {
                    if (usuario.buscarPorEmail(fieldUsuarioEmail.getText()) == null) {
                        
                        dicaUsuarioEmail.setVisible(true);
                        dicaUsuarioEmail.setText("Email nao existente");
                        alertaUsuarioExistente.setVisible(false);

                    } else {
                        dicaUsuarioEmail.setVisible(false);
                        alertaUsuarioExistente.setVisible(true);
                    }
                } else {
                    if (usuario.buscarPorNome(fieldUsuarioEmail.getText()) == null) {

                        dicaUsuarioEmail.setVisible(true);
                        dicaUsuarioEmail.setText("usuario nao existente");
                        alertaUsuarioExistente.setVisible(false);

                    } else {
                        dicaUsuarioEmail.setVisible(false);
                        alertaUsuarioExistente.setVisible(true);
                    }
                }
                if (fieldUsuarioEmail.getText().contains(" ")) {dicaUsuarioEmail.setText("Evite espaço");}
                if(fieldUsuarioEmail.getText().isEmpty()){dicaUsuarioEmail.setVisible(false);}
            }catch (SQLException e) {
                System.err.println("ERRO DE BANCO DE DADOS: Falha ao registrar o usuário. " + e.getMessage());
            }

        });
        fieldEntrarSenha.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!entrarConta.isVisible()){return;}
            alertaEntrarSenha.setVisible(fieldCondicao(fieldEntrarSenha));
            dicaSenhaEntrar.setVisible(false);
            if (fieldEntrarSenha.getText().contains(" ")) {
                dicaSenhaEntrar.setVisible(true);    
                dicaSenhaEntrar.setText("Evite Espaço");
            }
        });
    }
    //arrastar janela
    @FXML
    public void onMousePressed(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        xOffset = event.getScreenX() - stage.getX();
        yOffset = event.getScreenY() - stage.getY();
    }
    @FXML
    public void onMouseDragged(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
    //arrastar janela

    //botoesFuncoes
    public void fecharTela(MouseEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void minimizarJanela(MouseEvent event){
        javafx.scene.Node source = (javafx.scene.Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();   
        stage.setIconified(true);
    }
    //botoesFuncoes

    public void mudarTela(){

        if ( criarConta.isVisible() ){
            telaLogin();
        }else {
            telaCriar();
        }
    }


    public void telaLogin(){

        criarConta.setVisible(false);
        entrarConta.setVisible(true);

        alertaUsuario.setVisible(false);
        alertaEmail.setVisible(false);
        alertaTipo.setVisible(false);
        alertaSenha.setVisible(false);
        alertaConfirmSenha.setVisible(false);
        nomeExistente.setVisible(false);
        emailExistente.setVisible(false);
        dicaSenhaCriar.setVisible(false);
        dicaNomeUsuario.setVisible(false);
        dicaEmail.setVisible(false);
        checarDono.setStyle("-fx-background-color: #b3b3b3ff;-fx-background-radius:8;-fx-border-color: transparent;");
        checarCliente.setStyle("-fx-background-color: #b3b3b3ff;-fx-background-radius:8;-fx-border-color: transparent;");
        checarDono.setScaleX(1.0);
        checarDono.setScaleY(1.0);
        checarCliente.setScaleX(1.0);
        checarCliente.setScaleY(1.0);

        tipoUsr = null;

        fieldSenha.setVisible(false);
        fieldsenhaescondida.setVisible(true);
        fieldConfirmSenha.setVisible(false);
        fieldconfsenhaescondida.setVisible(true);

        timeline = new Timeline(
                new KeyFrame(Duration.millis(100), 
                    new KeyValue(palpebra_olho.controlY1Property(), cima),
                    new KeyValue(palpebra_olho.controlY2Property(), cima),
                    new KeyValue(palpebra_olho2.controlY1Property(), baixo),
                    new KeyValue(palpebra_olho2.controlY2Property(), baixo),
                    new KeyValue(pupila.layoutXProperty(),20)
                )
            );
        timeline.play();

        fieldsenhaescondida.setText("");
        fieldconfsenhaescondida.setText("");
        fieldNomeUsuario.setText("");
        fieldEmail.setText("");
        fieldSenha.setText("");
        fieldConfirmSenha.setText("");
        
        txtCriarConta.setStyle("-fx-background-color: #a82020;");
        txtLogarConta.setStyle("-fx-background-color: #881919;");
    }
    public void telaCriar(){
        criarConta.setVisible(true);
        entrarConta.setVisible(false);

        alertaUsuarioEmail.setVisible(false);
        alertaEntrarSenha.setVisible(false);
        dicaSenhaEntrar.setVisible(false);
        dicaUsuarioEmail.setVisible(false);

        fieldEntrarSenha.setVisible(false);
        fieldsenhaescondida1.setVisible(true);

        timeline = new Timeline(
                new KeyFrame(Duration.millis(100), 
                    new KeyValue(palpebra_olho1.controlY1Property(), cima),
                    new KeyValue(palpebra_olho1.controlY2Property(), cima),
                    new KeyValue(palpebra_olho21.controlY1Property(), baixo),
                    new KeyValue(palpebra_olho21.controlY2Property(), baixo)
                )
            );
        timeline.play();

        fieldsenhaescondida1.setText("");
        fieldUsuarioEmail.setText("");
        fieldEntrarSenha.setText("");

        txtCriarConta.setStyle("-fx-background-color: #881919;");
        txtLogarConta.setStyle("-fx-background-color: #a82020;");
    }


    public void escolherTipoUsuario(MouseEvent event) {
        ScaleTransition crescer = new ScaleTransition(Duration.millis(100));
        ScaleTransition diminuir = new ScaleTransition(Duration.millis(100));
        crescer.setToX(1.1);
        crescer.setToY(1.1);
        diminuir.setToX(1.0);
        diminuir.setToY(1.0);
        Label clicado = (Label) event.getSource();
        if (clicado == checarCliente) {
            checarCliente.setStyle("-fx-text-fill:white;-fx-background-color: #00ff22ff;-fx-background-radius:8;-fx-border-color: #4bdd27ff; -fx-border-width: 2;-fx-border-radius:8;");
            checarDono.setStyle("-fx-background-color: #b3b3b3ff;-fx-background-radius:8;-fx-border-color: transparent;");
            tipoUsr = "CLIENTE";
            crescer.setNode(checarCliente);
            diminuir.setNode(checarDono);
            
        } else if (clicado == checarDono) {
            checarDono.setStyle("-fx-text-fill:white;-fx-background-color: #00ff22ff;-fx-background-radius:8;-fx-border-color: #4bdd27ff; -fx-border-width: 2;-fx-border-radius:8;");
            checarCliente.setStyle("-fx-background-color:#b3b3b3ff;-fx-background-radius:8;-fx-border-color: transparent;");
            tipoUsr = "DONO/ADM";
            crescer.setNode(checarDono);
            diminuir.setNode(checarCliente);
        }
        crescer.play();
        diminuir.play();
    }

    public boolean fieldCondicao(TextField info){
        if ( info.getText().length() > 0 ){
            return false;
        }
        return true;
    }

    public void fecharAviso(){
        telaDeAviso.setVisible(false);
    }

    //funcao para botao de criar
    public void criarConta(){
        String acesso = tipoUsr;
        String nome = fieldNomeUsuario.getText();
        String email = fieldEmail.getText();
        String senha = fieldSenha.getText();
        codigoCorreto = gerarCodigoAleatorio(6);

        if (nome.length() > 0 && !dicaNomeUsuario.isVisible() && email.length() > 0 && senha.length() > 0 && 
        fieldConfirmSenha.getText().equals(senha) && senha.length() >= 8 && 
        !fieldNomeUsuario.getText().contains(" ") && 
        !fieldSenha.getText().contains(" ") && !fieldEmail.getText().contains(" ")) {

            alertaUsuario.setVisible(false);
            alertaEmail.setVisible(false);
            alertaTipo.setVisible(false);
            alertaSenha.setVisible(false);
            alertaConfirmSenha.setVisible(false);
            nomeExistente.setVisible(false);
            emailExistente.setVisible(false);
            dicaSenhaCriar.setVisible(false);

            try {
                //aviso
                if (usuario.nomeUsuarioExiste(nome) && usuario.emailExiste(email)) {
                    txtErroCadastro.setText(" O nome de usuario informado já está cadastrado.\nO e-mail informado já está cadastrado.");
                    txtErroCadastro.setVisible(true);
                    nomeExistente.setVisible(true);
                    emailExistente.setVisible(true);
                    telaDeAviso.setVisible(true);
                    return;
                }
                else if (usuario.nomeUsuarioExiste(nome)) {
                    txtErroCadastro.setText("O nome de usuario informado já está cadastrado.");
                    txtErroCadastro.setVisible(true);
                    nomeExistente.setVisible(true);
                    telaDeAviso.setVisible(true);
                    return;
                }
                else if (usuario.emailExiste(email)) {
                    txtErroCadastro.setText("O e-mail informado já está cadastrado.");
                    txtErroCadastro.setVisible(true);
                    emailExistente.setVisible(true);
                    telaDeAviso.setVisible(true);
                    return;
                }
                else if (acesso == null) {
                    txtErroCadastro.setText("Selecione um tipo de usuario");
                    txtErroCadastro.setVisible(true);
                    alertaTipo.setVisible(true);
                    telaDeAviso.setVisible(true);
                    return;
                }

                //aviso
                email+= "@gmail.com";
                boolean sucessoEnvio = emailService.enviarCodigoConfirmacao(email, codigoCorreto);
                
                if (sucessoEnvio) {
                    
                    c1 = new Usuario(acesso,nome,email,senha);
                    carregando.setVisible(true);
                    carregando1.setVisible(true);
                    txtEnviandoCodigo.setVisible(true);
                    RotateTransition carregarBranco = new RotateTransition(Duration.seconds(1), carregando);
                    RotateTransition carregarPreto = new RotateTransition(Duration.seconds(1), carregando1);
                    carregarBranco.setByAngle(360);
                    carregarBranco.setCycleCount(5);
                    carregarBranco.setAutoReverse(true);
                    carregarPreto.setByAngle(-360);
                    carregarPreto.setCycleCount(5);
                    carregarPreto.setAutoReverse(true);
                    
                    carregarBranco.setOnFinished(e -> {
                        carregando.setVisible(false);
                        carregando1.setVisible(false);
                        txtEnviandoCodigo.setVisible(false);
                        if (!telaConfirmacaoEmail.isVisible()) {telaConfirmacaoEmail.setVisible(true);labelErro.setText("Por favor, insira o código. ( Enter para confirmar )");}
                    });
                    carregarBranco.play();
                    carregarPreto.play();

                }else {
                    // FALHA NO ENVIO
                    txtErroCadastro.setText("Falha ao enviar o e-mail de confirmação. \nVerifique o endereço ou a conexão.");
                    telaDeAviso.setVisible(true);
                }
                
            }catch (SQLException e) {
            System.err.println("ERRO DE BANCO DE DADOS: Falha ao registrar o usuário. " + e.getMessage());
            } 
            
        
        } 
        else {//Exibir alertas
            
            alertaUsuario.setVisible(fieldCondicao(fieldNomeUsuario));
            if(dicaNomeUsuario.isVisible()){alertaUsuario.setVisible(true);}
            alertaEmail.setVisible(fieldCondicao(fieldEmail));
            alertaTipo.setVisible(tipoUsr == null ? true : false);
            alertaSenha.setVisible(fieldSenha.getText().length() >= 8 ? false : true);
            if(fieldSenha.getText().length() < 8 && !fieldSenha.getText().isEmpty()){
                dicaSenhaCriar.setVisible(true);
                dicaSenhaCriar.setText("minimo de 8 caracteres");
            }else{
                dicaSenhaCriar.setVisible(false);
            }
            alertaConfirmSenha.setVisible( fieldConfirmSenha.getText().equals(fieldSenha.getText()) ? false : true);
        }

    }
    //funcao para botao de criar

    //funcao para botao de entrar
    public void entrarConta(){

        String usrEmail = fieldUsuarioEmail.getText();
        String senha = fieldEntrarSenha.getText();
        
        try {
            c1 = usuario.verificarLogin(usrEmail, senha);
        }
        catch (SQLException e) {
            System.err.println("ERRO DE BANCO DE DADOS: Falha ao registrar o usuário. " + e.getMessage());
        }

        if (c1 != null) {

            alertaUsuarioEmail.setVisible(false);
            alertaEntrarSenha.setVisible(false);
            dicaSenhaEntrar.setVisible(false);
            dicaUsuarioEmail.setVisible(false);
            
            try {
                Object controllerObj = Main.setRoot("AbaInicial");

                if (controllerObj instanceof TelaInicialConfig) {
                    TelaInicialConfig proximoController = (TelaInicialConfig) controllerObj;
                    proximoController.receberInfoUsuario(c1);
                    System.out.println("\nUsuario "+c1.getNome()+" Logando...\n"+"Informacoes de "+
                    c1.getNome()+"\n"+c1.exibirInformacoes());
                }
            }
            catch (IOException e) {
                System.err.println("Erro ao carregar a próxima tela: " + e.getMessage());
                e.printStackTrace();
            }

        }else{ 

            try {
                if (usrEmail.contains("@gmail.com")) {
                    if (usuario.buscarPorEmail(usrEmail) == null) {
                        dicaUsuarioEmail.setVisible(true);
                        dicaUsuarioEmail.setText("Email nao existente");
                    } else if(c1 == null && !fieldEntrarSenha.getText().isEmpty() && !fieldEntrarSenha.getText().contains(" ")) {
                        dicaSenhaEntrar.setVisible(true);
                        dicaSenhaEntrar.setText("Senha Incorreta");
                    }
                } else {
                    if (usuario.buscarPorNome(usrEmail) == null) {
                        dicaUsuarioEmail.setVisible(true);
                        dicaUsuarioEmail.setText("usuario nao existente");
                    } else if(c1 == null && !fieldEntrarSenha.getText().isEmpty() && !fieldEntrarSenha.getText().contains(" ")) {
                        dicaSenhaEntrar.setVisible(true);
                        dicaSenhaEntrar.setText("Senha Incorreta");
                    }
                }
                
            }catch (SQLException e) {
                System.err.println("ERRO DE BANCO DE DADOS: Falha ao registrar o usuário. " + e.getMessage());
            }
            
            alertaEntrarSenha.setVisible(fieldCondicao(fieldEntrarSenha));
            alertaUsuarioEmail.setVisible(fieldCondicao(fieldUsuarioEmail));
            if (usrEmail.isEmpty()) {
                dicaUsuarioEmail.setVisible(false);
            }
        
        }

    }
    //funcao para botao de entrar

    //metodos para codigo de email
    private String gerarCodigoAleatorio(int tamanho) {
        if (tamanho <= 0) return "";
        Random random = new Random();
        // Garante que o número gerado tenha o tamanho exato de dígitos
        int min = (int) Math.pow(10, tamanho - 1); // Ex: 100000 para tamanho=6
        int max = (int) Math.pow(10, tamanho) - 1; // Ex: 999999 para tamanho=6
        int codigo = random.nextInt(max - min + 1) + min;
        return String.valueOf(codigo);
    }
    @FXML
    public void verificarCodigo() {
        String codigoDigitado = fieldCodigo.getText();

        if (codigoDigitado.isEmpty()) {
             labelErro.setText("Por favor, insira o código. ( Enter para confirmar )");
             return;
        }

        if (codigoDigitado.equals(codigoCorreto)) {
            try {
                
                usuario.inserirUsuario(c1);
                Object controllerObj = Main.setRoot("AbaInicial");

                if (controllerObj instanceof TelaInicialConfig) {
                    TelaInicialConfig proximoController = (TelaInicialConfig) controllerObj;
                    proximoController.receberInfoUsuario(c1);

                    System.out.println("\nUsuario Novo Criado "+"\nInformacoes de "+
                    c1.getNome()+"\n"+c1.exibirInformacoes());
                }
                
            } catch (SQLException e) {
                // Erro ao tentar salvar no banco após a confirmação
                labelErro.setText("Erro ao finalizar o cadastro (DB). Tente novamente.");
                System.err.println("Erro SQL ao inserir usuário: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                // Erro ao carregar a próxima tela FXML
                labelErro.setText("Erro ao carregar a \npróxima tela. Contate \no suporte.");
                System.err.println("Erro IO ao mudar para ContaCriada: " + e.getMessage());
                e.printStackTrace();
            }

        } else {
            labelErro.setText("Código de confirmação inválido. Tente novamente.");
        }
    }
    public void fecharConfirmacaoEmail(){
        telaConfirmacaoEmail.setVisible(false);
    }
    //metodos para codigo de email

    @FXML
    public void vizualizarSenha(MouseEvent event){

        if (entrarConta.isVisible()) {
            if(fieldEntrarSenha.isVisible()){
            fieldsenhaescondida1.setText(fieldEntrarSenha.getText());
            fieldEntrarSenha.setVisible(false);
            fieldsenhaescondida1.setVisible(true);

            timeline = new Timeline(
                new KeyFrame(Duration.millis(100), 
                    new KeyValue(palpebra_olho1.controlY1Property(), cima),
                    new KeyValue(palpebra_olho1.controlY2Property(), cima),
                    new KeyValue(palpebra_olho21.controlY1Property(), baixo),
                    new KeyValue(palpebra_olho21.controlY2Property(), baixo)
                )
            );

            }else {
                fieldEntrarSenha.setText(fieldsenhaescondida1.getText());
                fieldsenhaescondida1.setVisible(false);
                fieldEntrarSenha.setVisible(true);
                
                cima = palpebra_olho1.getControlY1();
                baixo = palpebra_olho21.getControlY1();
                timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), 
                        new KeyValue(palpebra_olho1.controlY1Property(), palpebra_olho1.getEndY()-9),
                        new KeyValue(palpebra_olho1.controlY2Property(), palpebra_olho1.getEndY()-9),
                        new KeyValue(palpebra_olho21.controlY1Property(), palpebra_olho21.getEndY()+7),
                        new KeyValue(palpebra_olho21.controlY2Property(), palpebra_olho21.getEndY()+7)
                            
                    )
                );
            }
        }else{
            if(fieldSenha.isVisible()){
            fieldsenhaescondida.setText(fieldSenha.getText());
            fieldconfsenhaescondida.setText(fieldConfirmSenha.getText());
            fieldSenha.setVisible(false);
            fieldsenhaescondida.setVisible(true);
            fieldConfirmSenha.setVisible(false);
            fieldconfsenhaescondida.setVisible(true);

            timeline = new Timeline(
                new KeyFrame(Duration.millis(100), 
                    new KeyValue(palpebra_olho.controlY1Property(), cima),
                    new KeyValue(palpebra_olho.controlY2Property(), cima),
                    new KeyValue(palpebra_olho2.controlY1Property(), baixo),
                    new KeyValue(palpebra_olho2.controlY2Property(), baixo),
                    new KeyValue(pupila.layoutXProperty(),20)
                )
            );

            }else {
                fieldSenha.setText(fieldsenhaescondida.getText());
                fieldConfirmSenha.setText(fieldconfsenhaescondida.getText());
                fieldsenhaescondida.setVisible(false);
                fieldSenha.setVisible(true);
                fieldconfsenhaescondida.setVisible(false);
                fieldConfirmSenha.setVisible(true);
                
                cima = palpebra_olho.getControlY1();
                baixo = palpebra_olho2.getControlY1();
                timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), 
                        new KeyValue(palpebra_olho.controlY1Property(), palpebra_olho.getEndY()-9),
                        new KeyValue(palpebra_olho.controlY2Property(), palpebra_olho.getEndY()-9),
                        new KeyValue(palpebra_olho2.controlY1Property(), palpebra_olho2.getEndY()+7),
                        new KeyValue(palpebra_olho2.controlY2Property(), palpebra_olho2.getEndY()+7)
                            
                    )
                );
            }
        }
        
        timeline.play();
    }

}
