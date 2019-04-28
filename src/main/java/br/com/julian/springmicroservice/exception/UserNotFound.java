package br.com.julian.springmicroservice.exception;

public class UserNotFound extends RuntimeException {

    public UserNotFound(String mensagem) {
        super(mensagem);
    }

}
