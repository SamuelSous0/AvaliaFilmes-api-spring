package com.example.avaliafilme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String mailFrom;

    public void enviarEmailRecuperacao(String destinatario, String link) {
    SimpleMailMessage mensagem = new SimpleMailMessage();
    mensagem.setFrom(mailFrom);
    mensagem.setTo(destinatario);
    mensagem.setSubject("AvaliaFilme — Redefinição de senha");
    mensagem.setText("""
            Olá!
            
            Recebemos um pedido para redefinir a senha da sua conta.
            Clique no link abaixo para continuar — ele é válido por 15 minutos:
            
            %s
            
            Caso não tenha feito essa solicitação, desconsidere este e-mail.
            """.formatted(link));
    mailSender.send(mensagem);
}
}