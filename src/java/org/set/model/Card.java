/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.set.model;

/**
 *
 * @author mer23
 */
public class Card {
    
    public enum Status {
        IN_DECK, IN_BOARD, DISCARDED;
    }
    
    public enum Shape {PEANUT, STADIUM, DIAMOND}
    
    public enum Color {RED, PURPLE, GREEN}
    
    public enum Texture {SOLID, STRIPPED, EMPTY}
    
    public enum Number {ONE, TWO, THREE}
    
    private Status status;
    private Shape shape;
    private Color color;
    private Texture texture;
    private Number number;

    public Card(Shape shape, Color color, Texture texture, Number number) {
        this.shape=shape;
        this.color=color;
        this.texture=texture;
        this.number=number;
        
        this.status= Status.IN_DECK; //a card's life starts in the deck.
    }
        
    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @return the shape
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * @return the number
     */
    public Number getNumber() {
        return number;
    }
}
