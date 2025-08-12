package br.com.sergiorafa.odesafiodaestante.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}