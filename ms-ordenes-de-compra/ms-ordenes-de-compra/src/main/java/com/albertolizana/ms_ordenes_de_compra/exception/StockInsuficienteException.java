package com.albertolizana.ms_ordenes_de_compra.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String message){
        super(message);
    }
}
