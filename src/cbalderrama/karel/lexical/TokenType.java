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
public enum TokenType {
    OPENPAR,
    CLOSEPAR,
    OPENBR,
    CLOSEBR,
    SEMICOLON,
    CLASSPROGRAM,
    VOID,
    STRING,
    PROGRAM,
    NUMBER,
    SUCC,
    PRED,
    IF,
    ELSE,
    ELSEIF,
    ITERATE,
    WHILE,
    NEXTTOBEEPER,
    NOTNEXTTOBEEPER,
    BEEPERSINBAG,
    NOBEEPERSINBAG,
    FACINGNORTH,
    FACINGSOUTH,
    FACINGWEST,
    FACINGEAST,
    NOTFACINGNORTH,
    NOTFACINGSOUTH,
    NOTFACINGWEST,
    NOTFACINGEAST,
    FRONTISCLEAR,
    FRONTISBLOCKED,
    LEFTISCLEAR,
    LEFTISBLOCKED,
    RIGHTISCLEAR,
    RIGHTISBLOCKED,
    AND,
    NOT,
    OR,
    ISZERO,
    MOVE,
    PICKBEEPER,
    PUTBEEPER,
    TURNLEFT,
    TURNOFF,
    RETURNVOID,
    UNKNOWN
}
