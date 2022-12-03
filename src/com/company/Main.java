package com.company;

import java.util.*;

public class Main {
    private static Board board = new Board();
    private static Scanner scanner = new Scanner(System.in);
    private static boolean correctMove = true;
    private static boolean continuePlaying = true;
    private static int numberOfMoves = 0;
    private static long startTime;

    public static void main(String[] args) {
        board.drawAll();

        startTime = System.currentTimeMillis();

        String input;

        System.out.println("--------------------------------------------");
        System.out.println("Wykonanie ruchu: *x *y lub *n");
        System.out.println("*x - nr stosu, z którego chcemy przełożyć kartę");
        System.out.println("*y - nr stosu, na który chcemy przełożyć kartę");
        System.out.println("*n - wpisz 'n', aby wylosować następną kartę");
        System.out.println("Przykładowe ruchy:");
        System.out.println("3 1 -> przełożnie karty ze stosu nr 3 na stos nr 1");
        System.out.println("4 0 -> przełożnie Asa ze stosu nr 4 na stosy dla Asów");
        System.out.println("8 4 -> przełożenie karty ze stosu kart do dobrania na stos nr 4");
        System.out.println("n -> wylosowanie nowej karty");
        System.out.println("--------------------------------------------");

        while (continuePlaying){
            System.out.print("\nWykonaj ruch: ");

            input = scanner.next();

            try{
                int stack1 = Integer.valueOf(input) - 1;

                if (stack1 < -1 || stack1 > 7) System.out.println("Niepoprawne dane");

                else{
                    int stack2 = scanner.nextInt() - 1;

                    if (stack2 < -1 || stack2 > 6) System.out.println("Niepoprawne dane");

                    else{
                        if (stack2 == -1) moveToCollector(stack1);
                        else if (stack1 == -1) moveFromCollector(stack2);
                        else if (stack1 == 7) moveFormNextCards(stack2);
                        else normalMove(stack1, stack2);
                    }
                }

                numberOfMoves++;
            } catch (NumberFormatException | InputMismatchException e){
                String command = input;

                if (command.equals("n")){
                    showNextCard();

                    correctMove = true;
                    numberOfMoves++;
                }

                else if (command.equals("q")){
                    continuePlaying = false;
                    correctMove = false;

                    break;
                }

                else if (command.equals("r")){
                    board = new Board();

                    numberOfMoves = 0;
                    startTime = System.currentTimeMillis();

                    board.drawAll();
                }

                else{
                    System.out.println("Nieznana komenda");

                    correctMove = false;
                }
            } finally {
                if (board.areCollectorsFull()){
                    continuePlaying = false;
                    correctMove = false;

                    float gameTime = (System.currentTimeMillis() - startTime) / 1000;
                    int minutes = (int) (gameTime / 60);
                    int seconds = (int) (gameTime % 60);

                    String stringMinutes = String.valueOf(minutes);
                    String stringSeconds = String.valueOf(seconds);

                    if (stringSeconds.length() == 1) stringSeconds = "0".concat(stringSeconds);

                    board.drawAll();

                    System.out.println("\n\n--------------------------------------------------------------------\n");
                    System.out.println("Wygrałeś/aś!");
                    System.out.println("Ilość ruchów: " + numberOfMoves);
                    System.out.println("Czas: " + stringMinutes + "." + stringSeconds);

                    break;
                }
                else if (correctMove) board.drawAll();
            }
        }

        scanner.close();
    }

    private static void showNextCard(){
        Stack nc0 = board.getNextCards(0);
        Stack nc1 = board.getNextCards(1);

        if (nc0.getStack().size() > 0){
            Card card = nc0.getCard(nc0.getStack().size() - 1);

            nc1.addCard(card);
            nc0.removeCard(card);
        }

        else{
            nc0.getStack().addAll(nc1.getStack());
            Collections.reverse(nc0.getStack());
            nc1.getStack().clear();
        }
    }

    private static void moveToCollector(int stack1){
        if (stack1 > -1) {
            Stack firstStack;
            boolean ok = true;

            if (stack1 == 7) {
                firstStack = board.getNextCards(1);

                if (firstStack.getStack().size() == 0) ok = false;
            } else {
                firstStack = board.getStack(stack1);
            }

            if (ok) {
                if (firstStack.getStack().size() > 0) {
                    Card firstCard = firstStack.getCard(firstStack.getStack().size() - 1);
                    Stack secondStack = board.getCollectors(firstCard, false);

                    if (secondStack.isMoveCorrect(firstCard, 2)) {
                        secondStack.addCard(firstCard);
                        firstStack.removeCard(firstCard);

                        correctMove = true;
                    } else {
                        correctMove = false;
                    }
                } else {
                    System.out.println("Niepoprawny ruch");
                }
            } else {
                System.out.println("Niepoprawny ruch");
            }
        }
    }

    private static void moveFromCollector(int stack2){
        Stack secondStack = board.getStack(stack2);
        Card stackCard = secondStack.getCard(secondStack.getStack().size() - 1);
        Stack firstStack = board.getCollectors(stackCard, true);

        if (firstStack != null){
            Card firstCard = firstStack.getCard(firstStack.getStack().size() - 1);

            if (secondStack.isMoveCorrect(firstCard, 1)){
                secondStack.addCard(firstCard);
                firstStack.removeCard(firstCard);

                correctMove = true;
            }

            else{
                correctMove = false;
            }
        }

        else{
            correctMove = false;
        }
    }

    private static void moveFormNextCards(int stack2){
        Stack next = board.getNextCards(1);

        if (next.getStack().size() > 0){
            Stack firstStack = next;
            Stack secondStack= board.getStack(stack2);

            int index = firstStack.getStack().size() - 1;

            Card firstCard = firstStack.getCard(index);

            if (secondStack.isMoveCorrect(firstCard, 1)){
                secondStack.addCard(firstCard);
                firstStack.removeCard(firstCard);

                correctMove = true;
            }

            else{
                correctMove = false;
            }
        }

        else {
            if (board.getNextCards(0).getStack().size() == 0){
                System.out.println("Nie można wylosować większej ilości kart");
            }

            else{
                System.out.println("Wylosuj następną kartę ('n')");
            }

            correctMove = false;
        }
    }

    private static void normalMove(int stack1, int stack2){
        Stack firstStack = board.getStack(stack1);
        Stack secondStack = board.getStack(stack2);

        if (firstStack.getStack().size() > 0) {

            int index = firstStack.getStack().size() - 1;

            for (int i = 0; i < firstStack.getStack().size(); i++) {
                if (firstStack.getCard(i).isKnown()) {
                    if (secondStack.isMoveCorrect(firstStack.getCard(i), 1)){
                        index = i;

                        break;
                    }
                }
            }

            Card firstCard = firstStack.getCard(index);

            if (secondStack.isMoveCorrect(firstCard, 1)) {
                if (index != firstStack.getStack().size() - 1) {
                    List<Card> cardsToRemove = new ArrayList<>();

                    for (int i = index; i < firstStack.getStack().size(); i++) {
                        firstCard = firstStack.getCard(i);

                        secondStack.addCard(firstCard);

                        cardsToRemove.add(firstCard);
                    }

                    for (Card c : cardsToRemove) firstStack.removeCard(c);

                    correctMove = true;
                }

                else {
                    secondStack.addCard(firstCard);
                    firstStack.removeCard(firstCard);

                    correctMove = true;
                }
            }

            else {
                correctMove = false;
            }
        }
    }
}
