package com.company;

public class Card {
    private int symbol; // 0 - Hearts | 1 - Spades | 2 - Diamonds | 3 - Clubs
//    private final String[] symbolIcon = {"♡", "♠", "♢", "♣"};
    private final String[] symbolIcon = {"@", "$", "#", "€"};
    private int number; // 0 - Ace | 10 - Jack | 11 - Queen | 12 - King
    private boolean known;

    private String[][] lines;

    public Card(int symbol, int number){
        this.symbol = symbol;
        this.number = number;
        known = false;

        visualizeCard();
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;

        visualizeCard();
    }

    private void visualizeCard(){
        if (known){
            lines = new String[8][15];

            String numberString;

            switch (number) {
                case 0:
                    numberString = "A";
                    break;
                case 10:
                    numberString = "J";
                    break;
                case 11:
                    numberString = "Q";
                    break;
                case 12:
                    numberString = "K";
                    break;
                default:
                    numberString = String.valueOf(number + 1);
            }

            for (int i = 0; i < 8; i++) {
                if (i == 0 || i == 7) {
                    lines[i][0] = "+";
                    lines[i][14] = "+";

                    for (int j = 1; j < 14; j++) {
                        lines[i][j] = "-";
                    }
                } else {
                    lines[i][0] = "|";
                    lines[i][14] = "|";

                    for (int j = 1; j < 14; j++) {
                        if (i == 1 || i == 6) {
                            if (j == 11 && numberString.length() == 2) {
                                lines[i][j] = String.valueOf(numberString.charAt(0));
                                lines[i][j + 1] = String.valueOf(numberString.charAt(1));

                                j++;
                            } else if (j == 2 || j == 12) {
                                if (numberString.length() == 2) {
                                    lines[i][j] = String.valueOf(numberString.charAt(0));
                                    lines[i][j + 1] = String.valueOf(numberString.charAt(1));

                                    j++;
                                } else {
                                    lines[i][j] = numberString;
                                }
                            } else {
                                lines[i][j] = " ";
                            }
                        } else if (i == 2 || i == 5) {
                            if (j == 2 || j == 12) {
                                lines[i][j] = symbolIcon[symbol];
                            } else {
                                lines[i][j] = " ";
                            }
                        } else {
                            lines[i][j] = " ";
                        }
                    }
                }

//            System.out.println(Arrays.toString(lines[i]));
            }
        }

        else{
            lines = new String[8][15];

            for (int i = 0; i < 8; i++) {
                if (i == 0 || i == 7){
                    lines[i][0] = "+";
                    lines[i][14] = "+";

                    for (int j = 1; j < 14; j++) {
                        lines[i][j] = "-";
                    }
                }

                else{
                    lines[i][0] = "|";
                    lines[i][14] = "|";

                    for (int j = 1; j < 14; j++) {
                        lines[i][j] = "/";
                    }
                }
            }
        }
    }

    public String[][] getLines(){
        return lines;
    }

    public String getLine(int index){
        String line = "";

        for (String s : lines[index]){
            line = line.concat(s);
        }

        return line;
    }

    public void displayCard(){
        for (String[] s : lines){
            for (String ss : s){
                System.out.print(ss);
            }

            System.out.println();
        }
    }

    public int getDisplayHeight(){
        return lines.length;
    }

    @Override
    public String toString() {
        return "Card{" +
                "symbol=" + symbol +
                ", number=" + number +
                ", known=" + known +
                '}';
    }
}
