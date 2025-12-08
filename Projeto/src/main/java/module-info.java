module com.example {
    // Módulos JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.swing; 

    requires java.sql; 
    requires jakarta.mail;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    
    // Permissões
    opens com.example to javafx.fxml;
    opens com.example.abaInicial to javafx.fxml;
    opens com.example.abaLogin to javafx.fxml;

    exports com.example;
    exports com.example.abaInicial;
    exports com.example.abaLogin;
    
}