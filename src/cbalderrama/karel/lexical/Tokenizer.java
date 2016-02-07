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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase que procesa un archivo de código fuente y lo convierte a una lista de Tokens.
 * @author Carlos Balderrama
 */
public class Tokenizer {

    /**
     * Lista de mensajes de ubicación de tokens desconocidos.
     */
    public final List<String> Messages=new ArrayList<>();

    /**
     * Lista de tokens procedentes del archivo de código fuente procesado.
     * @see Token
     */
    public final List<Token> Tokens=new ArrayList<>();
    /**
     * Procesa un archivo con código fuente para convertirlo a una lista de tokens.
     * @param filename Ruta del archivo con código fuente.
     * @throws IOException No se puede cargar el archivo de código fuente.
     */
    public void tokenize(String filename) throws IOException{
        Path path=Paths.get(filename);
        
        //Prepara las expresiones regulares
        String patron="(\\{|\\}|\\(|\\)|;|\\bclass program\\b|\\bprogram\\b|\\bvoid\\b|\\bsucc\\b|\\bpred\\b|\\biszero\\b|\\belse if\\b|\\belse\\b|\\bif\\b|\\biterate\\b|\\bwhile\\b|\\bmove\\b|\\bputbeeper\\b|\\bpickbeeper\\b|\\bturnleft\\b|\\bturnoff\\b|\\breturn\\b|\\&{2}|\\|{2}|\\!|\\bnextToABeeper\\b|\\bnotNextToABeeper\\b|\\banyBeepersInBeeperBag\\b|\\bnotBeepersInBeeperBag\\b|\\bfacingNorth\\b|\\bfacingSouth\\b|\\bfacingWest\\b|\\bfacingEast\\b|\\bnotFacingNorth\\b|\\bnotFacingSouth\\b|\\bnotFacingWest\\b|\\bnotFacingEast\\b|\\bfrontIsClear\\b|\\bfrontIsBlocked\\b|\\bleftIsClear\\b|\\bleftIsBlocked\\b|\\brightIsClear\\b|\\brightIsBlocked\\b|(?:[A-Za-z](?:[A-Za-z]|[0-9])+)|(?:[0-9]+)|(?:\\s+)|.)";
        String patronCad="^([A-Za-z](?:[A-Za-z]|[0-9])*)$";
        String patronNum="^([0-9]+)$";
        Pattern pat=Pattern.compile(patron);
        Pattern patCad=Pattern.compile(patronCad);
        Pattern patNum=Pattern.compile(patronNum);
        int linea=0;
        this.Messages.clear();
        this.Tokens.clear();
        //Lee el archivo
        try(Scanner scanner=new Scanner(path,StandardCharsets.UTF_8.name())){
            while(scanner.hasNextLine()){ 
                linea++;
                //Busca las palabras clave del lenguaje dentro de la línea actual
                String line=scanner.nextLine();
                Matcher m=pat.matcher(line);
                while(m.find()){
                    String valor=m.group(1);
                    TokenType tipo=TokenType.UNKNOWN;
                    if(valor.trim().equals(""))continue;//Si el token está vacío se ignora.
                    
                    //Se identifica el tipo de token
                    switch(valor){
                        case "(" : tipo = TokenType.OPENPAR;break;
                        case ")" : tipo = TokenType.CLOSEPAR;break;
                        case "{" : tipo = TokenType.OPENBR;break;
                        case "}" : tipo = TokenType.CLOSEBR;break;
                        case ";" : tipo = TokenType.SEMICOLON;break;
                        case "class program" : tipo = TokenType.CLASSPROGRAM;break;
                        case "program" : tipo = TokenType.PROGRAM;break;
                        case "void" : tipo = TokenType.VOID;break;
                        case "succ" : tipo = TokenType.SUCC;break;
                        case "pred" : tipo = TokenType.PRED;break;
                        case "iszero" : tipo = TokenType.ISZERO;break;
                        case "else" : tipo = TokenType.ELSE;break;
                        case "if" : tipo = TokenType.IF;break;
                        case "iterate" : tipo = TokenType.ITERATE;break;
                        case "while" : tipo = TokenType.WHILE;break;
                        case "move" : tipo = TokenType.MOVE;break;
                        case "putbeeper" : tipo = TokenType.PUTBEEPER;break;
                        case "pickbeeper" : tipo = TokenType.PICKBEEPER;break;
                        case "turnleft" : tipo = TokenType.TURNLEFT;break;
                        case "turnoff" : tipo = TokenType.TURNOFF;break;
                        case "return" : tipo = TokenType.RETURNVOID;break;
                        case "&&" : tipo = TokenType.AND;break;
                        case "||" : tipo = TokenType.OR;break;
                        case "!" : tipo = TokenType.NOT;break;
                        case "facingNorth" : tipo = TokenType.FACINGNORTH;break;
                        case "notFacingNorth" : tipo = TokenType.NOTFACINGNORTH;break;
                        case "facingSouth" : tipo = TokenType.FACINGSOUTH;break;
                        case "notFacingSouth" : tipo = TokenType.NOTFACINGSOUTH;break;
                        case "facingWest" : tipo = TokenType.FACINGWEST;break;
                        case "notFacingWest" : tipo = TokenType.NOTFACINGWEST;break;
                        case "facingEast" : tipo = TokenType.FACINGEAST;break;
                        case "notFacingEast" : tipo = TokenType.NOTFACINGEAST;break;
                        case "anyBeepersInBeeperBag" : tipo = TokenType.BEEPERSINBAG;break;
                        case "noBeepersInBeeperBag" : tipo = TokenType.NOBEEPERSINBAG;break;
                        case "nextToABeeper" : tipo = TokenType.NEXTTOBEEPER;break;
                        case "notNextToABeeper" : tipo = TokenType.NOTNEXTTOBEEPER;break;
                        case "frontIsClear" : tipo = TokenType.FRONTISCLEAR;break;
                        case "frontIsBlocked" : tipo = TokenType.FRONTISBLOCKED;break;
                        case "leftIsClear" : tipo = TokenType.LEFTISCLEAR;break;
                        case "leftIsBlocked" : tipo = TokenType.LEFTISBLOCKED;break;
                        case "rightIsClear" : tipo = TokenType.RIGHTISCLEAR;break;
                        case "rightIsBlocked" : tipo = TokenType.RIGHTISBLOCKED;break;
                        default:
                        {
                            //Si no se identificó un token por palabra clave se busca un número o una cadena de texto.
                            Matcher mNum=patNum.matcher(valor);
                            Matcher mStr=patCad.matcher(valor);
                            if(mNum.find(0)){//Revisa si es un número
                                tipo=TokenType.NUMBER;
                            }
                            else if(mStr.find(0)){//Revisa si es una cadena de texto
                                tipo=TokenType.STRING;
                            }
                            else
                            {
                                this.Messages.add("No se esperó '"+valor+"' en línea "+String.valueOf(linea)+", "+String.valueOf(m.start()+1));
                            }
                        }
                    }
                    this.Tokens.add(new Token(tipo,new TextPosition(linea,m.start()+1),valor));
                }      
            }
            
        }
        catch(IOException ex){
            throw new IOException("No se pudo cargar el archivo de código fuente.");
        }
    }
    
}
