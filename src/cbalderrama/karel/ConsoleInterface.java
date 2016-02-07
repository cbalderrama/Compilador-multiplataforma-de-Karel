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
package cbalderrama.karel;

import cbalderrama.karel.lexical.Tokenizer;
import cbalderrama.karel.syntax.SyntaxAnalyzer;
import java.io.IOException;
import java.util.Scanner;

/**
 * Interfaz de línea de comandos para probar el compilador de Karel.
 * @author Carlos Balderrama
 */
public class ConsoleInterface {

    /**
     * @param args Argumentos de línea de comandos.
     * @throws java.io.IOException No se puede cargar el archivo de código fuente.
     */
    public static void main(String[] args) throws IOException{
        Tokenizer lector=new Tokenizer();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la ruta del archivo: ");
        System.out.flush();
        String ruta=scanner.nextLine();
        System.out.println("Iniciando análisis léxico...");
        lector.tokenize(ruta);
        if(lector.Messages.isEmpty()){
            System.out.println("Análisis léxico exitoso.");
        }
        else{
            System.out.println("Análisis léxico fallido.");
            for(int i=0;i<lector.Messages.size();i++)System.err.println("* "+lector.Messages.get(i));
        }
        if(!lector.Messages.isEmpty()){
            return;
        }
        System.out.println();
        System.out.println("Iniciando análisis sintáctico...");
        SyntaxAnalyzer analyzer=new SyntaxAnalyzer(lector.Tokens);
        boolean res=analyzer.analyze();
        if(!res){
            System.out.println("Análisis sintáctico fallido.");
        }
        else{
            System.out.println("Análisis sintáctico exitoso.");
        }
    }
    
}
