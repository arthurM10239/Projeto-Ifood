package com.example.abaInicial;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import com.example.Main;
import com.example.abaLogin.Usuario;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class TelaInicialConfig implements Initializable{
    
    @FXML
    private Pane painelLateralAnimado;
    @FXML
    private Label nomeUsuarioMenuLateral;
    @FXML
    private Label tipoUsuarioMenuLateral;
    @FXML
    private Label emailMenuLateral;
    @FXML
    private Label tipoDeInfoMenuLateral1;
    @FXML
    private Label tipoDeInfoMenuLateral2;
    @FXML
    private Label tipoDeInfoMenuLateral3;
    @FXML
    private Label InfoDB;
    @FXML
    private Label bttAcessarDB;

    @FXML
    private Label bttSairConta;
    @FXML
    private Pane fundoBotaoSairConta;
    @FXML
    private Pane SacolaProdutos;
    @FXML
    private Pane painelMae;
    @FXML
    private Pane LateralSacola;
    @FXML
    private Usuario usuarioAcessando;

    Timeline attBancoDeDados = new Timeline();
    RestaurantesDB BancoDadosRestaurantes = new RestaurantesDB();
    private Set<Node> listaBlocosRestaurantes;
    private SacolaDeProdutos sacolaDeProdutos = new SacolaDeProdutos();
    private long lastCheckTime = 0;
    private int quantRest = 0;
    private int quantPratos = 0;

    private double atualizacaoDB = 5;
    private boolean[] isExpandido = new boolean[3];


    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        listaBlocosRestaurantes = painelMae.lookupAll("#restaurante-Item");
        atualizacaoDB = 0.5;
        adicionarMonitorDeAltura();
        transicaoCor(SacolaProdutos,200);
        botaoSairConta(bttSairConta,200);
        //verificarEAtualizar();
        atualizarInfos();
        expandirProduto();
        
        
    }


    //Informações Restaurante
    void atualizarInfos(){
        definirInfoRestaurante();
        attBancoDeDados = new Timeline(
            new KeyFrame(Duration.seconds(atualizacaoDB), event -> {
                verificarEAtualizar();
            })
        );
        atualizacaoDB = 5;
        attBancoDeDados.setCycleCount(Timeline.INDEFINITE); // Rodar infinitamente
        attBancoDeDados.play();
    }
    void verificarEAtualizar(){

        long currentDbTime = BancoDadosRestaurantes.buscarUltimaModificacao();
        int currentCont = BancoDadosRestaurantes.buscarModificacaoQuantRest();
        int currentContProdutos = BancoDadosRestaurantes.buscarModificacaoQuantPratos();
        
        if (currentDbTime > lastCheckTime || currentCont != quantRest || currentContProdutos != quantPratos) {

            System.out.println("Atualizando Dados");
            definirInfoRestaurante();
            
            lastCheckTime = currentDbTime;
            quantRest = currentCont;
            quantPratos = currentContProdutos;
            
        }
        
    }
    public void definirInfoRestaurante(){

        ArrayList<Restaurante> restaurantes = BancoDadosRestaurantes.buscarInfoRestaurante();

        int cont = 0;
        
        for(Node node : listaBlocosRestaurantes){//informes restaurante
            
            Label nomeRest = (Label)node.lookup("#Nome");
            HBox containerEstrelas = (HBox)node.lookup("#Estrelas");
            Label quantidadeAval = (Label)node.lookup("#QuantAval");
            Label mediaAvaliacao = (Label)node.lookup("#AvalMedia");
            Label donoRest = (Label)node.lookup("#Dono");
            Label tipoPrato = (Label)node.lookup("#TipoDePratos");

            if (cont < restaurantes.size()) {
                
                if (nomeRest != null) {
                    nomeRest.setText(restaurantes.get(cont).getNome());    
                }
                if (containerEstrelas != null) {
                }
                if (quantidadeAval != null) {
                    quantidadeAval.setText(restaurantes.get(cont).getNumAvaliacoes()+" Avaliações");
                }
                if (mediaAvaliacao != null) {
                    mediaAvaliacao.setText(restaurantes.get(cont).getMediaAvaliacao()+"");
                }
                if (donoRest != null) {
                    donoRest.setText("Dono: " + restaurantes.get(cont).getDono());
                }
                if(tipoPrato != null){
                    tipoPrato.setText(restaurantes.get(cont).getTipoCulinaria());
                }
                node.setVisible(true);
            }else {
                node.setVisible(false);
            }
            
            if (cont < restaurantes.size()) {

            Set<Node> listaProdutos = node.lookupAll("#produto-item");
            ArrayList<Produto> produtosDB = BancoDadosRestaurantes.buscarProdutosRestaurante(restaurantes.get(cont).getId());

            int produtoCont = 0;
            
                for (Node produtoNode : listaProdutos){

                    ((Label)produtoNode.lookup("#nome-produto")).setText("nome");;

                    if (produtoCont < produtosDB.size()) {

                        Produto atual = produtosDB.get(produtoCont);

                        Label nomeProduto = (Label)produtoNode.lookup("#nome-produto");
                        Label descricaoProduto = (Label)produtoNode.lookup("#descricao");
                        Label precoProduto = (Label)produtoNode.lookup("#valor-Real");
                        Label centavos = (Label)produtoNode.lookup("#valor-centavo");
                        
                        int preco[] = {
                            (int)Math.round(atual.getPreco() * 100)/100,
                            (int)(Math.round(atual.getPreco() * 100) % 100)
                        };

                        if (nomeProduto != null) {
                            nomeProduto.setText(atual.getNome());
                        }
                        if (descricaoProduto != null) {
                            descricaoProduto.setText(atual.getDescricao());
                        }
                        if (precoProduto != null) {

                            precoProduto.setText(preco[0] + ",");
                            if (preco[1] <= 10) {
                                centavos.setText("0" + preco[1] + "");
                            }else if(preco[1] == 0){
                                centavos.setText("00" + preco[1] + "");
                            }else{
                                centavos.setText(preco[1] + "");
                            }
                            
                        }
                        produtoNode.setVisible(true);
                    }else{
                        produtoNode.setVisible(false);
                    }
                    produtoCont++;

                }
            }
            cont++;
        }
        
    }
    public void receberInfoUsuario(Usuario conta){

        Stage stage = Main.getPrimaryStage();
        stage.sizeToScene(); 
        stage.centerOnScreen();

        usuarioAcessando = conta;

        nomeUsuarioMenuLateral.setText(conta.getNome());
        tipoUsuarioMenuLateral.setText(conta.getAcesso());
        emailMenuLateral.setText(conta.getEmail());
    }
    //Informações Restaurante

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


    //metodos de abrir
    @FXML
    private void abrirMenuLateral(MouseEvent event) { 
        
        if(!isExpandido[0]){

            for (Node paneAlvo : listaBlocosRestaurantes) {
                paneAlvo.toBack();
                if (paneAlvo.getProperties().containsKey("pane_selecionado") &&
                    (boolean) paneAlvo.getProperties().get("pane_selecionado")) {
                    

                    Node bttNode = paneAlvo.lookup("#btt-vizualizar-rest");
                    
                    if (bttNode != null) {
    
                        MouseEvent mouseEvent = new MouseEvent(
                            MouseEvent.MOUSE_CLICKED, 
                            0, 0, 0, 0, 
                            javafx.scene.input.MouseButton.PRIMARY, 
                            1, 
                            false, false, false, false, 
                            true, false, false, true, 
                            false, false, null
                        );
    
                        vizualizarRestaurante(mouseEvent.copyFor(bttNode, bttNode));
                        break; 
                    }
                }
            }
        }

        double LARGURA_INICIAL = 40.0;
        double LARGURA_FINAL = 200.0;
        double ALTURA_INCREMENTO = 100.0; 
        double larguraPara, alturaPara;

        if (usuarioAcessando != null && usuarioAcessando.getAcesso().equals("DONO")) {
            ALTURA_INCREMENTO = 350.0;
            bttSairConta.setLayoutY((ALTURA_INCREMENTO+200)-50);
            fundoBotaoSairConta.setLayoutY(bttSairConta.getLayoutY()+26);
        }
        
        if (isExpandido[0]) {
            larguraPara = LARGURA_INICIAL;
            alturaPara = LARGURA_INICIAL;
        } else {
            larguraPara = LARGURA_FINAL;
            alturaPara = LARGURA_FINAL + ALTURA_INCREMENTO;
        }
        
        Timeline timelineLargura = new Timeline();
        timelineLargura.getKeyFrames().add(
            new KeyFrame(Duration.millis(100),
                new KeyValue(painelLateralAnimado.prefWidthProperty(), larguraPara)
            )
        );
        
        Timeline timelineAltura = new Timeline();
        timelineAltura.getKeyFrames().add(
            new KeyFrame(Duration.millis(150),
                new KeyValue(painelLateralAnimado.prefHeightProperty(), alturaPara)
            )
        );

        if (isExpandido[0]) {
            timelineAltura.play();
            timelineAltura.setOnFinished(e -> timelineLargura.play());
            
        } else {
            timelineLargura.play();
            timelineLargura.setOnFinished(e -> timelineAltura.play());
        }

        isExpandido[0] = !isExpandido[0];
    }
    public void vizualizarRestaurante(MouseEvent e){

        if (isExpandido[0]) {
                    
            for (Node nodeAtual : painelLateralAnimado.getChildren()) {

                if (nodeAtual != null && nodeAtual instanceof ImageView) {
    
                    MouseEvent mouseEvent = new MouseEvent(
                        MouseEvent.MOUSE_CLICKED, 
                        0, 0, 0, 0, 
                        javafx.scene.input.MouseButton.PRIMARY, 
                        1, 
                        false, false, false, false, 
                        true, false, false, true, 
                        false, false, null
                    );
        
                        abrirMenuLateral(mouseEvent.copyFor(nodeAtual, nodeAtual));
                    break; 
                }
            }
                    
            
        }

        final String ESTADO_SELECIONADO_KEY = "pane_selecionado";
        final String LAYOUT_X_ORIGINAL_KEY = "layoutX_original";
        final String LAYOUT_Y_ORIGINAL_KEY = "layoutY_original";

        Set<Node> listaBtt = painelMae.lookupAll("#btt-vizualizar-rest");

        ImageView btt = new ImageView();
        Pane paneAlvo = new Pane();

        for (Node bttAtual : listaBtt) {
            if (bttAtual == e.getSource()) {
                paneAlvo = (Pane) bttAtual.getParent();    
                btt = (ImageView)bttAtual;
            }
        }
        

        if (!paneAlvo.getProperties().containsKey(ESTADO_SELECIONADO_KEY)) {
            paneAlvo.getProperties().put(ESTADO_SELECIONADO_KEY, false);
            paneAlvo.getProperties().put(LAYOUT_X_ORIGINAL_KEY, paneAlvo.getLayoutX());
            paneAlvo.getProperties().put(LAYOUT_Y_ORIGINAL_KEY, paneAlvo.getLayoutY());
        }

        boolean estaSelecionado = (boolean) paneAlvo.getProperties().get(ESTADO_SELECIONADO_KEY);
        double xOriginal = (double) paneAlvo.getProperties().get(LAYOUT_X_ORIGINAL_KEY);
        double yOriginal = (double) paneAlvo.getProperties().get(LAYOUT_Y_ORIGINAL_KEY);

        Timeline animacaoPosicao;

        if (!estaSelecionado) {
            animacaoPosicao = new Timeline(
                new KeyFrame(Duration.millis(200), 
                    // Move para a mesma coordenada fixa para todos os Panes
                    new KeyValue(paneAlvo.layoutXProperty(), 50),
                    new KeyValue(paneAlvo.layoutYProperty(), 100),
                    new KeyValue(btt.layoutXProperty(), 176 + 505),
                    new KeyValue(btt.layoutYProperty(), 178 + 275 ),
                    new KeyValue(btt.rotateProperty(), 180),
                    new KeyValue(paneAlvo.prefWidthProperty(), 200 + 500),
                    new KeyValue(paneAlvo.prefHeightProperty(), 200 + 270)
                )
            );
            paneAlvo.toFront();
            // for (int i = 0; i < (int)DoubleMediaAval; i++) {
            //     estrelas.get(i).setImage(new Image("imagens/estrelaAmarela.png"));    
            // }

            Pane painelProdutos = (Pane) paneAlvo.lookup("#painel-produtos");

            if (painelProdutos != null) {
                
                painelProdutos.prefHeightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        double alturaAtual = newValue.doubleValue();
                        double alturaAntiga = oldValue.doubleValue();
                        
                        Set<Node> listaProdutos = painelProdutos.lookupAll("#produto-item");
                        
                        for (Node produto : listaProdutos) {
                            exibirInfoMenuLateral(alturaAtual, alturaAntiga, (Pane) produto); 
                            if (((Label)produto.lookup("#nome-produto")).getText().equals("nome")) {
                                produto.setVisible(false);
                            }
                        }
                    }
                });
                
                painelProdutos.setVisible(true); 
                Timeline animacaoRest = new Timeline(
                    new KeyFrame(Duration.millis(200), 
                        new KeyValue(painelProdutos.prefWidthProperty(),450),
                        new KeyValue(painelProdutos.prefHeightProperty(),455)
                    )
                );
                animacaoRest.play();
            }
            
        } else {
            animacaoPosicao = new Timeline(
                new KeyFrame(Duration.millis(200), 
                    // Volta para a posição original salva no início
                    new KeyValue(paneAlvo.layoutXProperty(), xOriginal),
                    new KeyValue(paneAlvo.layoutYProperty(), yOriginal),
                    new KeyValue(btt.layoutXProperty(), 176),
                    new KeyValue(btt.layoutYProperty(), 178),
                    new KeyValue(btt.rotateProperty(), 0),
                    new KeyValue(paneAlvo.prefWidthProperty(), 200),
                    new KeyValue(paneAlvo.prefHeightProperty(), 200)
                )
            );

            Pane painelProdutos = (Pane) paneAlvo.lookup("#painel-produtos");
            if (painelProdutos != null) {

                painelProdutos.prefHeightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        double alturaAtual = newValue.doubleValue();
                        double alturaAntiga = oldValue.doubleValue();
                        
                        Set<Node> listaProdutos = painelProdutos.lookupAll("#produto-item");
                        
                        for (Node produto : listaProdutos) {
                            exibirInfoMenuLateral(alturaAtual, alturaAntiga, (Pane) produto); 
                        }
                    }
                });
                
                Timeline animacaoRest = new Timeline(
                    new KeyFrame(Duration.millis(100), 
                        new KeyValue(painelProdutos.prefWidthProperty(),0),
                        new KeyValue(painelProdutos.prefHeightProperty(),0)
                    )
                );
                animacaoRest.setOnFinished(event -> {
                    painelProdutos.setVisible(false); 
                });
                
                animacaoRest.play();
            }
        }
        
        paneAlvo.getProperties().put(ESTADO_SELECIONADO_KEY, !estaSelecionado);
        animacaoPosicao.play();

        if (isExpandido[1]) {
            abrirSacolaProdutos();
        }
    }
    public void abrirSacolaProdutos(){//animacao cresce da direita para esquerda

        if(!isExpandido[1]){//minimizar todos restauranetes abertos
            for (Node paneAlvo : listaBlocosRestaurantes) {
                paneAlvo.toBack();
                if (paneAlvo.getProperties().containsKey("pane_selecionado") &&
                    (boolean) paneAlvo.getProperties().get("pane_selecionado")) {
                    

                    Node bttNode = paneAlvo.lookup("#btt-vizualizar-rest");
                    if (bttNode != null) {
    
                        MouseEvent mouseEvent = new MouseEvent(
                            MouseEvent.MOUSE_CLICKED, 
                            0, 0, 0, 0, 
                            javafx.scene.input.MouseButton.PRIMARY, 
                            1, 
                            false, false, false, false, 
                            true, false, false, true, 
                            false, false, null
                        );
    
                        vizualizarRestaurante(mouseEvent.copyFor(bttNode, bttNode));
                        break; 
                    }
                }
            }
        }
        
        final double posicaoInicial = 799.0;
        final double posicaoFinal = 549.0;
        final double larguraTotal = 250.0; 
        double posicao, largura;

        if (isExpandido[1]) {
            posicao = posicaoInicial;
            largura = 0;
        } else {
            posicao = posicaoFinal;
            largura = larguraTotal;
        }

        Timeline animacaoPosicao = new Timeline(
            new KeyFrame(Duration.millis(200), 
                // Move para a mesma coordenada fixa para todos os Panes
                new KeyValue(LateralSacola.layoutXProperty(), posicao),
                new KeyValue(LateralSacola.prefWidthProperty(), largura)
            )
        );
        animacaoPosicao.play();

        isExpandido[1] = !isExpandido[1];

    }    
    
    public void botaoSairConta(Region valor, int delay){
        Duration duracao = Duration.millis(delay);
        Pane btt = fundoBotaoSairConta;

        Timeline mouseEntra = new Timeline(
                new KeyFrame(duracao, 
                    new KeyValue(btt.prefHeightProperty(), valor.getPrefHeight()),
                    new KeyValue(btt.layoutYProperty(), valor.getLayoutY())
                )
        );
        Timeline mouseSai = new Timeline(
                new KeyFrame(duracao, 
                    new KeyValue(btt.prefHeightProperty(), 0),
                    new KeyValue(btt.layoutYProperty(), valor.getLayoutY()+valor.getPrefHeight())
                )
        );

        valor.setOnMouseEntered(e -> {
            mouseSai.stop();
            mouseEntra.playFromStart();
        });

        valor.setOnMouseExited(e -> {
            mouseEntra.stop();
            mouseSai.playFromStart();
        });
        valor.setOnMouseClicked(e -> {
            if(usuarioAcessando != null){
                sairConta(valor);
            }
        });

    }
    public void sairConta(Region valor){
        Stage stageAtual = (Stage) valor.getScene().getWindow();
        attBancoDeDados.stop();
        stageAtual.close();

        try {
            Main.abrirLogin();
        } catch (Exception e) {
            System.out.println("Erro ao abrir tela de login: " + e.getMessage());
        } 
        
        
    }
    //metodos de abrir


    @FXML
    private void ligarDesligar(MouseEvent e){
        
        Label atual = (Label)e.getSource();
        if (isExpandido[2]) {
            atual.setStyle("-fx-background-color:rgb(73, 73, 73);-fx-alignment:TOP-CENTER;");
            atual.setText("EXIBIR");
        }else{
            atual.setStyle("-fx-background-color:rgb(41, 40, 40);-fx-alignment:CENTER;");
            atual.setText("FECHAR");
        }
        isExpandido[2] = !isExpandido[2];
    }
    public void transicaoCor(Region valor, int delay){
        Duration duracao = Duration.millis(delay);

        final CornerRadii RADIUS = new CornerRadii(20);
        final ObjectProperty<Color> corAtualPropriedade = new SimpleObjectProperty<>();
        // (rgba(197, 197, 197, 0.12))
        final Color COR_INICIAL =Color.rgb(131, 131, 131, 0.0);
        final Color COR_HOVER = Color.rgb(190, 189, 189, 0.1);
        
        corAtualPropriedade.set(COR_INICIAL);
        
        corAtualPropriedade.addListener((obs, oldColor, newColor) -> {
            BackgroundFill fill = new BackgroundFill(newColor, RADIUS, null);
            valor.setBackground(new Background(fill));
        });
        
        Timeline fadeInTimeline = new Timeline(
            new KeyFrame(duracao, new KeyValue(corAtualPropriedade, COR_HOVER))
        );
        
        Timeline fadeOutTimeline = new Timeline(
            new KeyFrame(duracao, new KeyValue(corAtualPropriedade, COR_INICIAL))
        );

        // 4. Ativação via Mouse Events
        valor.setOnMouseEntered(e -> {
            fadeOutTimeline.stop();
            fadeInTimeline.playFromStart();
        });

        valor.setOnMouseExited(e -> {
            fadeInTimeline.stop();
            fadeOutTimeline.playFromStart();
        });
    }
    public void exibirInfoMenuLateral(double alturaAtual,double alturaAntiga,Region conteudo){
        if (alturaAtual >= (conteudo.getLayoutY()+conteudo.getHeight()) && alturaAntiga < (conteudo.getLayoutY()+conteudo.getHeight())) {
            conteudo.setVisible(true);
        } else if (alturaAtual < (conteudo.getLayoutY()+conteudo.getHeight()) && alturaAntiga >= (conteudo.getLayoutY()+conteudo.getHeight())) {
            conteudo.setVisible(false);
        }
    }
    private void adicionarMonitorDeAltura() {
        painelLateralAnimado.prefHeightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double alturaAtual = newValue.doubleValue();
                double alturaAntiga = oldValue.doubleValue();
                
                exibirInfoMenuLateral(alturaAtual, alturaAntiga, nomeUsuarioMenuLateral);
                exibirInfoMenuLateral(alturaAtual, alturaAntiga, tipoUsuarioMenuLateral);
                exibirInfoMenuLateral(alturaAtual, alturaAntiga, emailMenuLateral);
                exibirInfoMenuLateral(alturaAtual, alturaAntiga, bttSairConta);
                exibirInfoMenuLateral(alturaAtual, alturaAntiga, tipoDeInfoMenuLateral1);
                exibirInfoMenuLateral(alturaAtual, alturaAntiga, tipoDeInfoMenuLateral2);
                exibirInfoMenuLateral(alturaAtual, alturaAntiga, tipoDeInfoMenuLateral3);
                if (usuarioAcessando != null && usuarioAcessando.getAcesso().equals("DONO")) {
                    exibirInfoMenuLateral(alturaAtual, alturaAntiga, InfoDB);
                    exibirInfoMenuLateral(alturaAtual, alturaAntiga, bttAcessarDB);
                }
                
            }
        });
    }
    @FXML
    private void expandirProduto(){

        for(Node produto : painelMae.lookupAll("#produto-item")){


            Pane produtoPane = (Pane)produto;
            Label descricao = (Label)produto.lookup("#descricao");
            Label nome = (Label)produto.lookup("#nome-produto");
            Pane preco = (Pane)produto.lookup("#preco-bloco");
            Label bttAddItem = (Label)produtoPane.lookup("#add-sacola");
            Label bttComprar = (Label)produtoPane.lookup("#comprar");
            final int xAntigo = (int)produtoPane.getLayoutX();
            final int yAntigo = (int)produtoPane.getLayoutY();
            

            if (produto.isVisible() && produtoPane != null && descricao != null && nome != null && preco != null) {

                produtoPane.prefHeightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    double alturaAtual = newValue.doubleValue();
                    double alturaAntiga = oldValue.doubleValue();
                    
                    if (bttAddItem != null && bttComprar != null) {
                        exibirInfoMenuLateral(alturaAtual, alturaAntiga, (Region)bttAddItem); 
                        exibirInfoMenuLateral(alturaAtual, alturaAntiga, (Region)bttComprar);
                    }
                }
            });

                produto.setOnMouseEntered(e ->{
                    produtoPane.toFront();
                    Timeline expandir = new Timeline(
                        new KeyFrame(Duration.millis(150), 

                            new KeyValue(produtoPane.layoutXProperty(), 
                            produtoPane.getLayoutX() - 
                            (produtoPane.getLayoutX() > 100 && produtoPane.getLayoutX() < 250 ? 37.5 : 
                            (produtoPane.getLayoutX() > 250 ? 65 : 0))),
                            new KeyValue(produtoPane.layoutYProperty(), produtoPane.getLayoutY() - (produtoPane.getLayoutY() > 250 ? 45:0)),
                            new KeyValue(produtoPane.prefHeightProperty(), 195),
                            new KeyValue(produtoPane.prefWidthProperty(), 190),
                            new KeyValue(descricao.prefWidthProperty(), descricao.getPrefWidth() + 65),
                            new KeyValue(descricao.prefHeightProperty(), descricao.getPrefHeight() + 20),
                            new KeyValue(nome.prefWidthProperty(), nome.getPrefWidth() + 60),
                            new KeyValue(preco.prefWidthProperty(), preco.getPrefWidth() + 60)
                        )
                    );
                    expandir.play();

                });
                
                produto.setOnMouseExited(e -> {
                    produtoPane.toBack();
                    Timeline recolher = new Timeline(
                        new KeyFrame(Duration.millis(150), 
                            new KeyValue(produtoPane.layoutXProperty(), xAntigo),
                            new KeyValue(produtoPane.layoutYProperty(), yAntigo),
                            new KeyValue(produtoPane.prefHeightProperty(), 145),
                            new KeyValue(produtoPane.prefWidthProperty(), 120),
                            new KeyValue(descricao.prefWidthProperty(), 105),
                            new KeyValue(descricao.prefHeightProperty(), 47),
                            new KeyValue(nome.prefWidthProperty(), 115),
                            new KeyValue(preco.prefWidthProperty(), 121)
                        )
                    );
                    recolher.play();

                });
                
            }
        
        }
    
    }

}