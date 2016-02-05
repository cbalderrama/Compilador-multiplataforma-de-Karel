/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbalderrama.karel;

import cbalderrama.karel.lexical.Token;
import cbalderrama.karel.lexical.Tokenizer;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Carlos Balderrama
 */
public class ConsoleInterface {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        Tokenizer lector=new Tokenizer();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la ruta del archivo: ");
        System.out.flush();
        String ruta=scanner.nextLine();
        lector.tokenize(ruta);
        for(int i=0;i<lector.Tokens.size();i++)System.out.println(lector.Tokens.get(i).toString());
        System.out.println("--------------------------");
        System.out.println("Análisis finalizado. Resultados: ");
        System.out.println("--------------------------");
        if(lector.Messages.isEmpty()){
            System.out.println("Análisis exitoso.");
        }
        else{
            for(int i=0;i<lector.Messages.size();i++)System.err.println("* "+lector.Messages.get(i));
        }
    }
    
}
