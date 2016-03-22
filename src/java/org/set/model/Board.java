/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.set.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mer23
 */
public class Board {
    
    private ArrayList<Card> deck;
    
    private Card[] board;
    
    
    public Board(boolean easy) {
     
        this.deck= easy? buildDeck(27) : buildDeck(81);
        
        shuffleDeck();
    }
    
    /**
     * Populates this Board's deck with cards. The resulting deck is ordered.
     * @param size the size(in cards) of the deck to be built.
     * @return a new, ordered deck.
     */
    private ArrayList<Card> buildDeck(int size) {
        
        ArrayList<Card> newDeck = new ArrayList(size);

        for(Card.Number number : Card.Number.values()) {
            for(Card.Shape shape : Card.Shape.values()) {
                for(Card.Color color : Card.Color.values()) {
                    for(Card.Texture texture : Card.Texture.values()) {
                        newDeck.add(new Card(shape, color, texture, number));
                    }
                }
            }
            
            /*In case the challenge mode is easy, this 'if' stops the loop after
            * the first 27 cards were pushed to deck.
            */
            if(size == 27)
                return newDeck;
        }
        
        return newDeck;
    }
    
    public void shuffleDeck() {
        Collections.shuffle(this.deck);
    }
    
    public static boolean isSet(Card c1, Card c2, Card c3) {
        
        return shapeCheck(c1, c2, c3) &&
                textureCheck(c1, c2, c3) &&
                colorCheck(c1, c2, c3) &&
                numberCheck(c1, c2, c3);
    }
    
    private static boolean numberCheck(Card c1, Card c2, Card c3) {
        return (c1.getNumber() == c2.getNumber() && c2.getNumber() == c3.getNumber()) ||
                (c1.getNumber() != c2.getNumber() && c2.getNumber() != c3.getNumber() &&
                c1.getNumber() != c3.getNumber());
    }
    
    private static boolean shapeCheck(Card c1, Card c2, Card c3) {
        return (c1.getShape() == c2.getShape() && c2.getShape() == c3.getShape()) ||
                (c1.getShape() != c2.getShape() && c2.getShape() != c3.getShape() &&
                c1.getShape() != c3.getShape());
    }
    
    private static boolean colorCheck(Card c1, Card c2, Card c3) {
        return (c1.getColor() == c2.getColor() && c2.getColor() == c3.getColor()) ||
                (c1.getColor() != c2.getColor() && c2.getColor() != c3.getColor() &&
                c1.getColor() != c3.getColor());
    }
    
    private static boolean textureCheck(Card c1, Card c2, Card c3) {
        return (c1.getTexture() == c2.getTexture() && c2.getTexture() == c3.getTexture()) ||
                (c1.getTexture() != c2.getTexture() && c2.getTexture() != c3.getTexture() &&
                c1.getTexture() != c3.getTexture());
    }
    
    
}
