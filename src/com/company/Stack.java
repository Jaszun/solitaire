package com.company;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    private List<Card> stack;
    private String[][] emptyStack;

    public Stack(){
        stack = new ArrayList<>();

        visualizeEmptyStack();
    }

    public void addCard(Card card){
        stack.add(card);
    }

    public void removeCard(Card card){
        if (stack.contains(card)){
            stack.remove(card);
            if (stack.size() > 0) getCard(stack.size() - 1).setKnown(true);
        }
    }

    public List<Card> getStack() {
        return stack;
    }

    public Card getCard(int index){
        return stack.get(index);
    }

    public boolean isMoveCorrect(Card card, int type){
        boolean result;

        if (type == 1) result = tryToMoveToStack(card);

        else result = tryToMoveToCollector(card);

        return result;
    }

    private boolean tryToMoveToStack(Card card){
        int color = card.getSymbol() % 2;

        if (stack.size() >= 1){
            if (stack.get(stack.size() - 1).getNumber() - 1 == card.getNumber()) {
                if (stack.get(stack.size() - 1).getSymbol() % 2 != color) {
                    return true;
                }

                else {
                    System.out.println("zły kolor");

                    return false;
                }
            }

            else {
                System.out.println("zły numer");

                return false;
            }
        }

        else{
            if (card.getNumber() == 12) return true;

            else{
                System.out.println("w ten sposób można przełożyć króla");

                return false;
            }
        }
    }

    private boolean tryToMoveToCollector(Card card){
        int color = card.getSymbol() % 2;

        if (stack.size() >= 1) {
            if (stack.get(stack.size() - 1).getNumber() + 1 == card.getNumber()) {
                if (stack.get(stack.size() - 1).getSymbol() % 2 == color) {
                    return true;
                }

                else {
                    System.out.println("zły kolor");

                    return false;
                }
            }

            else {
                System.out.println("zły numer");

                return false;
            }
        }

        else{
            if (card.getNumber() == 0) return true;

            else{
                System.out.println("pierwszą kartą musi być as");

                return false;
            }
        }
    }

    public int getStackHeight(){
        int height;

        switch (stack.size()){
            case 0:
                height = 0;
                break;

            case 1:
                height = 8;
                break;

            default:
                height = 3 * (stack.size() - 1) + 8;
        }

        return height;
    }

    public int getCardDisplayHeight(int index){
        Card card = getCard(index);

        if (card.isKnown() && stack.get(stack.size() - 1).equals(card)){
            return 8;
        }

        else{
            return 3;
        }
    }

    private void visualizeEmptyStack(){
        emptyStack = new String[8][15];

        for (int i = 0; i < 8; i++) {
            if (i == 0 || i == 7) {
                emptyStack[i][0] = "+";
                emptyStack[i][14] = "+";

                for (int j = 1; j < 14; j++) {
                    emptyStack[i][j] = "-";
                }
            }

            else {
                emptyStack[i][0] = "|";
                emptyStack[i][14] = "|";

                for (int j = 1; j < 14; j++) {
                    emptyStack[i][j] = " ";
                }
            }
        }
    }

    public String[][] getEmptyStack() {
        return emptyStack;
    }

    public String getEmptyStackLine(int index){
        String line = "";

        for (String s : emptyStack[index]){
            line = line.concat(s);
        }

        return line;
    }

    @Override
    public String toString() {
        return "Stack{" +
                "stack=" + stack +
                '}';
    }
}
