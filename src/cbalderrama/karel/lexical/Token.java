/*
 * The MIT License
 *
 * Copyright 2016 Carlos Balderrama.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package cbalderrama.karel.lexical;


/**
 * Clase para manejo de tokens.
 * @author Carlos Balderrama
 */
public class Token {

    /**
     * Tipo de token.
     */
    public final TokenType TokenType;

    /**
     * Posición en el archivo de código fuente donde se encontró el token.
     */
    public final TextPosition TokenPosition;

    /**
     * Cadena de caracteres representada por el token.
     */
    public final String Value;

    /**
     * Crea un nuevo token.
     * @param tokenType Tipo de token.
     * @param tokenPosition Posición en el archivo de código fuente donde se encontró el token.
     * @param value Cadena de caracteres representada por el token.
     */
    public Token(TokenType tokenType,TextPosition tokenPosition,String value){
        this.TokenType=tokenType;
        this.TokenPosition=tokenPosition;
        this.Value=value;
    }

    /**
     * Indica si el tipo de token pertenece a un tipo booleano del lenguaje.
     * @return Valor booleano que indica si el tipo token pertenece a un tipo booleano del lenguaje.
     */
    public boolean isBoolean(){
        boolean res=false;
        switch(TokenType){
            case BEEPERSINBAG:
            case FACINGEAST:
            case FACINGSOUTH:
            case FRONTISBLOCKED:
            case FACINGNORTH:
            case FACINGWEST:
            case FRONTISCLEAR:
            case LEFTISBLOCKED:
            case LEFTISCLEAR:
            case NEXTTOBEEPER:
            case NOBEEPERSINBAG:
            case NOTFACINGEAST:
            case NOTFACINGNORTH:
            case NOTFACINGSOUTH:
            case NOTFACINGWEST:
            case NOTNEXTTOBEEPER:
            case RIGHTISBLOCKED:
            case RIGHTISCLEAR:res=true;break;
        }
        return res;
    }

    /**
     * Indica si el tipo de token pertenece a un método ejecutable del lenguaje.
     * @return Valor booleano que indica si el tipo de token pertenece a un método ejecutable del lenguaje.
     */
    public boolean isCommand(){
        boolean res=false;
        switch(TokenType){
            case MOVE:
            case PICKBEEPER:
            case RETURNVOID:
            case PUTBEEPER:
            case TURNLEFT:
            case TURNOFF:res=true;break;
        }
        return res;
    }
    @Override
    public String toString() {
        return "Token{" + "TokenType=" + TokenType + ", TokenPosition=" + TokenPosition + ", Value=" + Value + '}';
    }
    
}
