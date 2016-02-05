/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbalderrama.karel.lexical;


/**
 *
 * @author Carlos Balderrama
 */
public class Token {
    protected TokenType TokenType;
    protected TextPosition TokenPosition;
    protected String Value;
    Token(TokenType tokenType,TextPosition tokenPosition,String value){
        this.TokenType=tokenType;
        this.TokenPosition=tokenPosition;
        this.Value=value;
    }
}
