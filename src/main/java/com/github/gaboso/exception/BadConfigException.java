package com.github.gaboso.exception;

public class BadConfigException extends Exception {

    public BadConfigException() {
        super("Quantidade de dias inválida: Caso CUSTOM_DAYS seja TRUE, " +
                  "é necessário informar valor para todos os dias na variável DAYS da classe Config." +
                  "\nExemplo: Se QUANTITY_OF_DAYS for 5, devem existir 5 valores no array DAYS.");
    }

}