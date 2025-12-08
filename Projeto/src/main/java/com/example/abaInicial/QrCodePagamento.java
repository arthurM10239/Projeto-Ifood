package com.example.abaInicial;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class QrCodePagamento {
    
    private static final String PIX_STATIC_BASE = 
        "000201" + 
        "26480014br.gov.bcb.pix0126arthurmiguel2006@gmail.com" + 
        "52040000" + 
        "5303986" + 
        "5802BR" + 
        "5924Arthur Miguel Siqueira B" + 
        "6009Sao Paulo" + 
        "610901227-200" + 
        "62240520daqr1905539767537694";

    public void CriarPagamento(String valor, ImageView qrCodeImageView) {
        String valorpagamento = valor;

        if (valorpagamento != null && !valorpagamento.trim().isEmpty() && valorpagamento.matches("^\\d+(\\.\\d+)?$")) {
            BigDecimal qrcodePagamento = new BigDecimal(valorpagamento);
            int size = 200; 

            Image qrCodeImage = gerarImagem(gerarQrCodeString(qrcodePagamento), size);

            if (qrCodeImage != null) {
                qrCodeImageView.setImage(qrCodeImage);
            } else {
                qrCodeImageView.setImage(null);
                System.err.println("Falha na geração do QR Code.");
            }
        } else {
            Image x = new Image("https://media.istockphoto.com/id/1987131601/vector/red-cross-sign-icon-wrong-mark-icon-red-cross-x-symbol-grunge-x-icon.jpg?s=170667a&w=0&k=20&c=ZL2JuvHKmPBlOKdTMsnXSHLH-VJOP-EjD_bYTDrIRo0=");
            qrCodeImageView.setImage(x);
        }
    }

    private Image gerarImagem(String data, int size){
        if (data == null || data.trim().isEmpty()) {
            return null;
        }

        try {
            // 1. Configurações para o QR Code (Codificação, Correção de Erro)
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
            
            // 2. Criação da matriz de bits (BitMatrix) com o ZXing
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(
                data,
                BarcodeFormat.QR_CODE,
                size,
                size,
                hints
            );

            // 3. Converte a BitMatrix para um BufferedImage (formato AWT/Swing)
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // 4. Converte o BufferedImage para um JavaFX Image usando SwingFXUtils
            return SwingFXUtils.toFXImage(bufferedImage, null);

        } catch (WriterException e) {
            System.err.println("Erro ao gerar QR Code: " + e.getMessage());
            return null;
        }
    }
    
    private String gerarQrCodeString(BigDecimal valor) {
        
        String valorFormatado = valor
            .setScale(2, RoundingMode.HALF_UP)
            .toPlainString(); 
        String campoValor54 = "54" + 
                              String.format(Locale.US, "%02d", valorFormatado.length()) + 
                              valorFormatado;

        String pixStringCompleta = PIX_STATIC_BASE + campoValor54;
        String stringParaChecksum = pixStringCompleta + "6304"; 
        String crc16Hex = calcularCRC16(stringParaChecksum);

        return pixStringCompleta + "6304" + crc16Hex;
    }

    private String calcularCRC16(String data) {
        int crc = 0xFFFF; // Valor inicial
        int polynomial = 0x1021; // Polinômio para CRC-CCITT

        byte[] bytes = data.getBytes();

        for (byte b : bytes) {
            crc ^= (b << 8); // XOR com o byte atual

            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ polynomial;
                } else {
                    crc <<= 1;
                }
            }
        }
        
        // O resultado deve ser retornado em 4 dígitos hexadecimais (uppercase)
        return String.format(Locale.US, "%04X", (crc & 0xFFFF));
    }
}