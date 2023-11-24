package com.powernode.bank.exceptions;

/**
 * 余额不足
 */
public class MoneyNotEnoughException extends Exception{
    public MoneyNotEnoughException(){

    }
    public MoneyNotEnoughException(String msg){
        super(msg);
    }
}
