package com.example.abaInicial;
import java.util.ArrayList;
import java.util.Set;

import com.example.Main;
import com.example.abaLogin.Usuario;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaInicialConfig implements Initializable{
    
    @FXML
    private Pane painelAnimado;
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
    private Label bttSairConta;
    @FXML
    private Pane fundoBotaoSairConta;
    @FXML
    private Pane SacolaProdutos;
    @FXML
    private Pane painelMae;

    RestaurantesDB BancoDadosRestaurantes = new RestaurantesDB();
    private long lastCheckTime = 0;
    private int quantRest = 0;


    private boolean isExpandido = false;

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        adicionarMonitorDeAltura();
        transicaoCor(SacolaProdutos,200);
        atualizarInfos();
    }

    void atualizarInfos(){
        definirInfoRestaurante();
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(5), event -> {
                verificarEAtualizar();
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE); // Rodar infinitamente
        timeline.play();
    }

    void verificarEAtualizar(){
        long currentDbTime = BancoDadosRestaurantes.buscarUltimaModificacao();
        int currentCont = BancoDadosRestaurantes.buscarModificacaoQuantRest();
        
        if (currentDbTime > lastCheckTime || currentCont != quantRest) {
            System.out.println("MUDANÇA DETECTADA NO DB! Atualizando UI...");
            definirInfoRestaurante();
            
            lastCheckTime = currentDbTime;
            quantRest = currentCont;
        }
        
    }

    public void definirInfoRestaurante(){
        ArrayList<Restaurante> restaurantes = BancoDadosRestaurantes.buscarInfoRestaurante();

        int cont = 0;
        Set<Node> todosRest = painelMae.lookupAll("#restaurante-Item");
        
        for(Node node : todosRest){
            
            Label nomeRest = (Label)node.lookup("#restNome");
            HBox containerEstrelas = (HBox)node.lookup("#restEstrelas");
            Label quantidadeAval = (Label)node.lookup("#restQuantAval");
            Label mediaAvaliacao = (Label)node.lookup("#restAvalMedia");
            Label donoRest = (Label)node.lookup("#restDono");

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
                }
                node.setVisible(true);
            }else {
                node.setVisible(false);
            }
            cont++;
            
        }

    }

    public void receberInfoUsuario(Usuario conta){

        Stage stage = Main.getPrimaryStage();
        stage.sizeToScene(); 
        stage.centerOnScreen();

        nomeUsuarioMenuLateral.setText(conta.getNome());
        tipoUsuarioMenuLateral.setText(conta.getAcesso());
        emailMenuLateral.setText(conta.getEmail());
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

    public void exibirInfoMenuLateral(double alturaAtual,double alturaAntiga,Label conteudo){
        if (alturaAtual >= (conteudo.getLayoutY()+conteudo.getHeight()) && alturaAntiga < (conteudo.getLayoutY()+conteudo.getHeight())) {
            conteudo.setVisible(true);
        } else if (alturaAtual < (conteudo.getLayoutY()+conteudo.getHeight()) && alturaAntiga >= (conteudo.getLayoutY()+conteudo.getHeight())) {
            conteudo.setVisible(false);
        }
    }
    private void adicionarMonitorDeAltura() {
        painelAnimado.prefHeightProperty().addListener(new ChangeListener<Number>() {
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
                
            }
        });
    }

    @FXML
    private void abrirMenuLateral(MouseEvent event) { 
        
        final double LARGURA_INICIAL = 40.0;
        final double LARGURA_FINAL = 200.0;
        final double ALTURA_INCREMENTO = 100.0; 
        double larguraPara, alturaPara;
        
        if (isExpandido) {
            larguraPara = LARGURA_INICIAL;
            alturaPara = LARGURA_INICIAL;
        } else {
            larguraPara = LARGURA_FINAL;
            alturaPara = LARGURA_FINAL + ALTURA_INCREMENTO;
        }
        
        Timeline timelineLargura = new Timeline();
        timelineLargura.getKeyFrames().add(
            new KeyFrame(Duration.millis(100),
                new KeyValue(painelAnimado.prefWidthProperty(), larguraPara)
            )
        );
        
        Timeline timelineAltura = new Timeline();
        timelineAltura.getKeyFrames().add(
            new KeyFrame(Duration.millis(150),
                new KeyValue(painelAnimado.prefHeightProperty(), alturaPara)
            )
        );

        if (isExpandido) {
            timelineAltura.play();
            timelineAltura.setOnFinished(e -> timelineLargura.play());
            
        } else {
            timelineLargura.play();
            timelineLargura.setOnFinished(e -> timelineAltura.play());
        }

        isExpandido = !isExpandido;
    }

    public void vizualizarRestaurante(MouseEvent e){

        final String ESTADO_SELECIONADO_KEY = "pane_selecionado";
        final String LAYOUT_X_ORIGINAL_KEY = "layoutX_original";
        final String LAYOUT_Y_ORIGINAL_KEY = "layoutY_original";

        Node btt = (Node)e.getSource();
        Pane paneAlvo = (Pane) btt.getParent();

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
                    new KeyValue(btt.layoutYProperty(), 178 + 255 ),
                    new KeyValue(btt.rotateProperty(), 180),
                    new KeyValue(paneAlvo.prefWidthProperty(), 200 + 500),
                    new KeyValue(paneAlvo.prefHeightProperty(), 200 + 250)
                )
            );
            paneAlvo.toFront();
            // for (int i = 0; i < (int)DoubleMediaAval; i++) {
            //     estrelas.get(i).setImage(new Image("imagens/estrelaAmarela.png"));    
            // }
            
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
            paneAlvo.toBack();
        }
        
        paneAlvo.getProperties().put(ESTADO_SELECIONADO_KEY, !estaSelecionado);
        animacaoPosicao.play();
    }

    public void entraBotaoSairConta(MouseEvent e){
        Pane btt = fundoBotaoSairConta;

        Timeline botaoSairConta = new Timeline(
                new KeyFrame(Duration.millis(100), 
                    // Move para a mesma coordenada fixa para todos os Panes
                    new KeyValue(btt.prefHeightProperty(), 26),
                    new KeyValue(btt.layoutYProperty(), 254)
                )
        );
        
        botaoSairConta.play();
    }
    public void saiBotaoSairConta(MouseEvent e){
        Pane btt = fundoBotaoSairConta;
        
        Timeline botaoSairConta = new Timeline(
                new KeyFrame(Duration.millis(200), 
                    new KeyValue(btt.prefHeightProperty(), 0),
                    new KeyValue(btt.layoutYProperty(), 280)
                )
        );
        
        botaoSairConta.play();
    }
    
}