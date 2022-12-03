package com.company;

import java.util.*;

public class Deck {
    private List<Card> deck;

    public Deck(){
        deck = new ArrayList<>();

        createDeck();
    }

    public void shuffle(){
        Collections.shuffle(deck);
    }

    public List getDeck(){
        return deck;
    }

    public Card getCard(int index){
        return deck.get(index);
    }

    private void createDeck(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 13; j++){
                deck.add(new Card(i, j));
            }
        }

        shuffle();
    }

    @Override
    public String toString() {
        return "Deck[" +
                "deck = " + deck +
                ']';
    }
}
