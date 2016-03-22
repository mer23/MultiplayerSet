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
public class Game {

    public enum Mode {
        CLASSIC, TIME
    }
    
    public enum Difficulty {
        EASY, NORMAL
    }
    
    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private Card[] board;
    private Mode mode;
    private Difficulty difficulty;
    private int maxPlayers;
    
    private boolean playing;
    
    public Game(Mode mode, Difficulty dif, int maxPlayers) {
        
        this.mode= mode;
        this.difficulty=dif;
        this.maxPlayers= maxPlayers;
        this.players= new ArrayList<>(maxPlayers);
        this.playing= false;
        
        this.deck= dif == Difficulty.EASY? buildDeck(27) : buildDeck(81);
        
        shuffleDeck();
    }
    
    public boolean addPlayer(Player newPlayer) {
        if(this.getPlayers().size() < this.getMaxPlayers()) {
            this.getPlayers().add(newPlayer);
            return true;
        }
        return false;
    }
    
    public void start() {
        this.playing= true;
    }
    
    public Card[] getBoard() {
        return this.board;
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
    
    /**fills every empty(null) slot in the board with cards from the deck.
     * @return a list of the cards dealt.
     */
    public ArrayList<Card> deal() {
        ArrayList<Card> dealt= new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            
            if(deck.isEmpty()) { 
                break; 
            }
            else if (board[i] == null) {
                board[i] = deck.remove(deck.size() - 1);
                dealt.add(board[i]);
            }
        }
        return dealt;
    }
    
    public void setFound(int slot1, int slot2, int slot3) {
        board[slot1]= null;
        board[slot2]= null;
        board[slot3]= null;
        
    }
    
    public boolean isSet(int slot1, int slot2, int slot3) {
        
        Card c1= board[slot1];
        Card c2= board[slot2];
        Card c3= board[slot3];
        
        return shapeCheck(c1, c2, c3) &&
                textureCheck(c1, c2, c3) &&
                colorCheck(c1, c2, c3) &&
                numberCheck(c1, c2, c3);
    }
    
    public boolean noSetInBoard() {
        
        return true;
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
    
    /**
     * @return the players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * @return the mode
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * @return the difficulty
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * @return the maxPlayers
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * @return the playing
     */
    public boolean isPlaying() {
        return playing;
    }
}
