package com.company;

import java.util.Arrays;
import java.util.List;

public class Board {
    private Stack[] stacks;
    private Stack[] collectors;
    private Stack[] nextCards;
    private Deck deck;
    private int deckId;

    public Board(){
        deck = new Deck();
        stacks = new Stack[7];
        collectors = new Stack[4];
        nextCards = new Stack[2];
        deckId = 0;

        prepereBoard();
    }

    public Stack getStack(int index){
        return stacks[index];
    }

    public Stack getCollectors(Card card, boolean find){
        if (find){
            int symbol = card.getSymbol();
            int value = card.getNumber();

            for (int i = 0; i < 4; i++){
                for (Card c : collectors[i].getStack()){
                    if (c.getSymbol() % 2 != symbol % 2){
                        if (c.getNumber() == value - 1){
                            return collectors[i];
                        }
                    }
                }
            }

            System.out.println("niepoprawny ruch");

            return null;
        }

        else{
            int index = card.getSymbol();

            return collectors[index];
        }
    }

    public Stack getNextCards(int index){
        return nextCards[index];
    }

    public void prepereBoard(){
        for (int i = 0; i < 7; i++) {
            stacks[i] = new Stack();

            for (int j = 0; j < i + 1; j++) {
                if (i == j) deck.getCard(deckId).setKnown(true);
                stacks[i].addCard(deck.getCard(deckId));
                deckId++;
            }
        }

        nextCards[0] = new Stack();
        nextCards[1] = new Stack();

        for (int i = 0; i < 24; i++) {
            nextCards[0].addCard(deck.getCard(deckId));
            deckId++;
        }

        for (int i = 0; i < 4; i++){
            collectors[i] = new Stack();
        }

//        displayBoard();
    }

    public void displayBoard(){
        for (int i = 0; i < 7; i++) {
            System.out.println(i + 1 + " " + stacks[i].getStack().size() + " " + stacks[i]);
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public int getHeighestStack(){
        int height = stacks[0].getStackHeight();

        for (Stack s : stacks){
            if (s.getStackHeight() > height){
                height = s.getStackHeight();
            }
        }

        if (height == 0) height = 8;

        return height;
    }

    public void drawCollectors(){
        String line = "";

        for (int i = 1; i < 16 * 4; i++){
            if (i == 31 || i == 33) line = line.concat(" ");
            else if (i == 32) line = line.concat("0");
            else line = line.concat("-");
        }

        for (int i = 0; i < 17; i++){
            line = line.concat(" ");
        }

        for (int i = 1; i < 16 * 2; i++){
            if (i == 15 || i == 17) line = line.concat(" ");
            else if (i == 16) line = line.concat("8");
            else line = line.concat("-");
        }

        System.out.println(line);

        for (int i = 0; i < 8; i++){
            line = "";

            for (int j = 0; j < 4; j++){
                if (collectors[j].getStack().size() == 0){
                    line = line.concat(collectors[j].getEmptyStackLine(i));
                }

                else{
                    line = line.concat(collectors[j].getCard((collectors[j].getStack().size() - 1)).getLine(i));
                }

                line = line.concat(" ");
            }

            for (int j = 0; j < 16; j++){
                line = line.concat(" ");
            }

            for (int j = 1; j >= 0; j--){
                if (nextCards[j].getStack().size() == 0){
                    line = line.concat(nextCards[j].getEmptyStackLine(i));
                }

                else{
                    for (Card c : nextCards[j].getStack()){
                        if (j == 0) c.setKnown(false);
                        else c.setKnown(true);
                    }

                    line = line.concat(nextCards[j].getCard(nextCards[j].getStack().size() - 1).getLine(i));
                }

                line = line.concat(" ");
            }

            System.out.println(line);
        }

        System.out.println();
    }

    public void drawAll(){
        drawCollectors();
        drawStacks();
    }

    public void drawStacks(){
        String line = "";

        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 15; j++){
                if (j == 6 || j == 8) line = line.concat(" ");
                else if (j == 7) line = line.concat((i + 1) + "");
                else line = line.concat("-");
            }

            line = line.concat(" ");
        }

        System.out.println(line);

        int[] cardNum = {0, 0, 0, 0, 0, 0, 0};
        int[] cardIndex = {0, 0, 0, 0, 0, 0, 0};
        boolean[] firstIndex = {true, true, true, true, true, true, true};

        for (int i = 0; i < getHeighestStack(); i++){
            line = "";

            for (int j = 0; j < 7; j++){
                List<Card> currentStack = stacks[j].getStack();

                if (currentStack.size() == 0 && i < 8){
                    line = line.concat(stacks[0].getEmptyStackLine(i));
                }

                else if (cardNum[j] >= currentStack.size()){
                    for (int k = 0; k < 15; k++){
                        line = line.concat(" ");
                    }
                }

                else{
                    if (cardIndex[j] % stacks[j].getCardDisplayHeight(cardNum[j]) == 0 && !firstIndex[j]){
                        cardNum[j] = cardNum[j] + 1;
                        cardIndex[j] = 0;
                        firstIndex[j] = true;

                        if (cardNum[j] >= currentStack.size()){
                            for (int k = 0; k < 15; k++){
                                line = line.concat(" ");
                            }
                        }

                        else{
                            line = line.concat(currentStack.get(cardNum[j]).getLine(cardIndex[j]));
                        }
                    }

                    else{
                        line = line.concat(currentStack.get(cardNum[j]).getLine(cardIndex[j]));

                        firstIndex[j] = false;
                    }

                    cardIndex[j] = cardIndex[j] + 1;
                }

                line = line.concat(" ");
            }

//            System.out.println(Arrays.toString(cardIndex));
//            System.out.println(Arrays.toString(cardNum));

            System.out.println(line);
        }
    }

    public boolean areCollectorsFull(){
        int count = 0;
        boolean result = false;

        for (Stack s : collectors){
            if (s.getStack().size() == 13) count++;
        }

        if (count == 4) result = true;

        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "stacks=" + Arrays.toString(stacks) +
                ", collectors=" + Arrays.toString(collectors) +
                ", nextCards=" + Arrays.toString(nextCards) +
                '}';
    }
}
