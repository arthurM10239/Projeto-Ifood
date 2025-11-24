package com.example.abaLogin;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailService {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587"; // Porta TSL/STARTTLS
    private static final String SENDER_EMAIL = "emailteste32112@gmail.com"; 
    private static final String SENDER_PASSWORD = "jrfsfcymccgjnzcf"; 
    
    public boolean enviarCodigoConfirmacao(String destinatario, String codigo) {

        // 1. Configurações de Propriedades
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        // 2. Cria a Sessão de E-mail com Autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            // 3. Cria a Mensagem
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(destinatario)
            );
            message.setSubject("Código de Confirmação de Cadastro");
            
            String htmlContent = "<h1>Seu Código de Confirmação</h1>"
                               + "<p>Use o seguinte código para verificar sua conta:</p>"
                               + "<h2 style='color: #881919;'>" + codigo + "</h2>"
                               + "<p>Se você não solicitou este código, por favor, ignore este e-mail.</p>";
            
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // 4. Envia a Mensagem
            Transport.send(message);

            System.out.println("Email enviado com sucesso para: " + destinatario);
            return true;

        } catch (MessagingException e) {
            System.err.println("Erro ao enviar o email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}