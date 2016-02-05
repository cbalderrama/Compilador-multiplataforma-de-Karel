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
public class TextPosition {
    final int Line;
    final int Column;
    TextPosition(int line, int column) throws Exception{
        if(line<0||column<0)throw new Exception("La línea o columna no puede ser inferior a 1.");
        this.Line=line;
        this.Column=column;
    }

    @Override
    public String toString() {
        return "TextPosition{" + "Line=" + Line + ", Column=" + Column + '}';
    }
}
