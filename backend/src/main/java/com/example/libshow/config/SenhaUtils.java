package com.example.libshow.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SenhaUtils {

    public static String encode(String senha) {
        try {
            // Cria o digest com algoritmo SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Converte a senha em bytes e aplica o hash
            byte[] hashBytes = md.digest(senha.getBytes());

            // Retorna em Base64 (pode ser hex também)
            return Base64.getEncoder().encodeToString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }
}