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

    public void enviarEmailRecuperacao(String destinatario, String codigo) {
    SimpleMailMessage mensagem = new SimpleMailMessage();
    mensagem.setFrom(mailFrom);
    mensagem.setTo(destinatario);
    mensagem.setSubject("AvaliaFilme — Redefinição de senha");
    mensagem.setText("""
            Olá!
            
            Seu código para redefinir a senha é:

            %s

            O código é válido por 15 minutos.
            
            Caso não tenha feito essa solicitação, desconsidere este e-mail.
            """.formatted(codigo));
    mailSender.send(mensagem);
}
}