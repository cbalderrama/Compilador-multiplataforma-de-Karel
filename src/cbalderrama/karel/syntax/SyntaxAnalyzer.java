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
package cbalderrama.karel.syntax;

import cbalderrama.karel.lexical.Token;
import cbalderrama.karel.lexical.TokenType;
import java.util.List;

/**
 * Clase de analizador sintáctico de tokens.
 * @author Carlos Balderrama
 */
public class SyntaxAnalyzer {
    private final List<Token> Tokens;
    private int ListIterator=-1;
    /**
     * Crea un nuevo analizador sintáctico para una lista de tokens dada.
     * @param tokens Lista de tokens a analizar
     */
    public SyntaxAnalyzer(List<Token> tokens) {
        this.Tokens=tokens;
    }
    private boolean expect(int index,TokenType expectedTokenType){
        if(index<this.Tokens.size()&&index>=0){
            return (this.Tokens.get(index).TokenType==expectedTokenType);
        }
        return false;
    }
    private Token get(int index){
        if(index<this.Tokens.size()&&index>=0){
            return this.Tokens.get(index);
        }
        return null;
    }

    /**
     * Devuelve el último token que fue considerado por el analizador sintáctico luego de llamar a analyze().
     * @return Último token considerado por el analizador sintático.
     */
    public Token getLastAnalyzedToken(){
        return get(ListIterator);
    }
    /**
     * Analiza la lista de tokens e indica si la estructura sintáctica es válida.
     * @return Valor booleano que indica si el código tiene una estructura sintáctica válida.
     */
    public boolean analyze(){
        this.ListIterator=0;
        if(!(expect(ListIterator++, TokenType.CLASSPROGRAM)&&expect(ListIterator++, TokenType.OPENBR))){
            return false;
        }
        int lastOK=ListIterator;
        while(true){
            lastOK=ListIterator;
            if(!analyzeDeclaration())
            {
                break;
            }
        }
        ListIterator=lastOK;
        if(!(expect(ListIterator++, TokenType.PROGRAM)&&expect(ListIterator++, TokenType.OPENPAR)&&expect(ListIterator++, TokenType.CLOSEPAR))){
            return false;
        }
        if(!analyzeBlock())return false;
        return expect(ListIterator++, TokenType.CLOSEBR);
    }
    
    private boolean analyzeDeclaration(){
        if(!(expect(ListIterator, TokenType.VOID))){
            return false;
        }
        ListIterator++;
        if(!analyzeDeclarationSign()){
            return false;
        }
        return analyzeBlock();
    }

    private boolean analyzeBlock(){
        if(!expect(ListIterator++, TokenType.OPENBR)){
            return false;
        }
        int lastOK=ListIterator;
        while(true){
            lastOK=ListIterator;
            if(!analyzeExpression())
            {
                break;
            }
        }
        ListIterator=lastOK;
        return expect(ListIterator++,TokenType.CLOSEBR);
    }

    private boolean analyzeDeclarationSign(){
        if(!expect(ListIterator++, TokenType.STRING)){
            return false;
        }
        return analyzeOptionalParameter();
    }

    private boolean analyzeOptionalParameter(){
        if(!expect(ListIterator++, TokenType.OPENPAR)){
            return false;
        }
        if(!(expect(ListIterator, TokenType.CLOSEPAR)||expect(ListIterator, TokenType.STRING)||expect(ListIterator,TokenType.NUMBER)||expect(ListIterator, TokenType.PRED)||expect(ListIterator, TokenType.SUCC))){
            return false;
        }
        if(expect(ListIterator, TokenType.STRING)||expect(ListIterator,TokenType.NUMBER)||expect(ListIterator, TokenType.PRED)||expect(ListIterator, TokenType.SUCC)){
            if(!analyzeNumber()){
                return false;
            }
        }
        return expect(ListIterator++, TokenType.CLOSEPAR);
    }

    private boolean analyzeExpression(){
        if(!(expect(ListIterator, TokenType.IF)||expect(ListIterator, TokenType.WHILE)||expect(ListIterator, TokenType.ITERATE)||expect(ListIterator, TokenType.STRING)||expect(ListIterator, TokenType.SEMICOLON)||this.get(ListIterator).isCommand())){
            return false;
        }
        Token type=this.get(ListIterator++);
        if(type.TokenType==TokenType.SEMICOLON){
            return true;
        }
        else if(type.TokenType==TokenType.ITERATE){
            if(!expect(ListIterator++,TokenType.OPENPAR)){
                return false;
            }
            if(!analyzeNumber()){
                return false;
            }
            if(!expect(ListIterator++,TokenType.CLOSEPAR)){
                return false;
            }
            return analyzeBlock();
        }
        else if(type.TokenType==TokenType.WHILE){
            if(!expect(ListIterator++,TokenType.OPENPAR)){
                return false;
            }
            if(!analyzeBoolean()){
                return false;
            }
            if(!expect(ListIterator++,TokenType.CLOSEPAR)){
                return false;
            }
            return analyzeBlock();
        }
        else if(type.TokenType==TokenType.IF){
            if(!expect(ListIterator++, TokenType.OPENPAR)){
                return false;
            }
            if(!analyzeBoolean()){
                return false;
            }
            if(!expect(ListIterator++, TokenType.CLOSEPAR)){
                return false;
            }
            if(!analyzeBlock()){
                return false;
            }
            if(expect(ListIterator, TokenType.ELSE)){
                ListIterator++;
                if(!analyzeBlock()){
                    return false;
                }
            }
            return true;
        }
        else if(type.isCommand()){
            return expect(ListIterator++, TokenType.OPENPAR)&&expect(ListIterator++, TokenType.CLOSEPAR)&&expect(ListIterator++, TokenType.SEMICOLON);
        }
        else if(type.TokenType==TokenType.STRING){
            return analyzeOptionalParameter();
        }
        return false;
    }

    private boolean analyzeBoolean(){
        if(!analyzeBooleanAnd()){
            return false;
        }
        if(expect(ListIterator,TokenType.OR)){
            ListIterator++;
            if(!analyzeBooleanAnd()){
                return false;
            }
        }
        return true;
    }

    private boolean analyzeNumber(){
        Token sig=get(ListIterator++);
        if(!(sig.TokenType==TokenType.NUMBER||sig.TokenType==TokenType.STRING||sig.TokenType==TokenType.PRED||sig.TokenType==TokenType.SUCC)){
            return false;
        }
        if(sig.TokenType==TokenType.PRED||sig.TokenType==TokenType.SUCC){
            if(!expect(ListIterator++,TokenType.OPENPAR)){
                return false;
            }
            if(!analyzeNumber()){
                return false;
            }
            if(!expect(ListIterator++,TokenType.CLOSEPAR)){
                return false;
            }
        }
        return true;
    }

    private boolean analyzeBooleanAnd(){
        if(!analyzeBooleanNot()){
            return false;
        }
        if(expect(ListIterator,TokenType.AND)){
            ListIterator++;
            if(!analyzeBooleanNot()){
                return false;
            }
        }
        return true;
    }

    private boolean analyzeBooleanNot(){
        if(expect(ListIterator, TokenType.NOT)){
            ListIterator++;
        }
        return analyzeAtomicClause();
    }

    private boolean analyzeAtomicClause(){
        Token sig=get(ListIterator++);
        if(!(sig.isBoolean()||sig.TokenType==TokenType.ISZERO||sig.TokenType==TokenType.OPENPAR)){
            return false;
        }
        if(sig.TokenType==TokenType.ISZERO){
            if(!(expect(ListIterator++,TokenType.OPENPAR))){
                return false;
            }
            if(!analyzeNumber()){
                return false;
            }
            if(!expect(ListIterator++,TokenType.CLOSEPAR)){
                return false;
            }
        }
        else if(sig.TokenType==TokenType.OPENPAR){
            if(!analyzeBoolean()){
                return false;
            }
            if(!expect(ListIterator++,TokenType.CLOSEPAR)){
                return false;
            }
        }
        return true;
    }


}
