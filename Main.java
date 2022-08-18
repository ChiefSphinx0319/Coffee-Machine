import java.util.Arrays;
import java.util.Scanner;

enum MachineState {
    AWAITING_ACTION,
    AWAITING_COFFEE_SELECTION,
    AWAITING_FILLING_INGREDIENTS,
    EXIT,
}
class Coffee {
    int water;
    int milk;
    int coffeeBeans;
    int cost;

    public Coffee(int water, int milk, int coffeeBeans, int cost) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.cost = cost;
    }
}
class CoffeeMachine {

    static String ingredient = null;
    public static MachineState state = MachineState.AWAITING_ACTION;

    int waterStorage;
    int milkStorage;
    int coffeeBeanStorage;
    int noOfCupsAvailable;
    int moneyStorage;

    public CoffeeMachine() {
        waterStorage = 400;
        milkStorage = 540;
        coffeeBeanStorage = 120;
        noOfCupsAvailable = 9;
        moneyStorage = 550;
    }

    Coffee espresso = new Coffee(250,0,16,4);
    Coffee latte = new Coffee(350,75,20,7);
    Coffee cappuccino = new Coffee(200,100,12,6);

    static String getUserStringInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
    static int getUserIntInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    void makeCoffee (Coffee typeOfCoffee) {
        int waterReq;
        int milkReq;
        int coffeeBeansReq;
        int[] arrayList;
        int cupsCan;
        if (typeOfCoffee.milk == 0) {
            waterReq = waterStorage / typeOfCoffee.water;
            coffeeBeansReq = coffeeBeanStorage / typeOfCoffee.coffeeBeans;
            arrayList = new int[]{waterReq, coffeeBeansReq, noOfCupsAvailable};
            Arrays.sort(arrayList); //Sort the array to ascending order then get the index 0 then returns the least number therein.

            cupsCan = arrayList[0];// returns the number of cups can produce

            if (cupsCan >= 1) {
                System.out.println("I have enough resources, making you a coffee!\n");
                waterStorage -= 250;
                coffeeBeanStorage -=  16;
                moneyStorage += 4;
                noOfCupsAvailable -= 1;
            } else {
                ingredient = waterReq == 0 ? "water" :
                        coffeeBeansReq == 0 ? "coffee beans" :
                                noOfCupsAvailable == 0 ? "disposable cups" : "error";
                System.out.printf("Sorry, not enough %s!", ingredient);
            }
        } else {
            waterReq = waterStorage / typeOfCoffee.water;
            milkReq = milkStorage / typeOfCoffee.milk;
            coffeeBeansReq = coffeeBeanStorage / typeOfCoffee.coffeeBeans;
            int cupsLeft = noOfCupsAvailable;
            arrayList = new int[]{waterReq, milkReq, coffeeBeansReq, cupsLeft};
            Arrays.sort(arrayList); //Sort the array to ascending order then get the index 0 then returns the least number therein.

            cupsCan = arrayList[0];// returns the number of cups can produce

            if (cupsCan >= 1) {
                System.out.println("I have enough resources, making you a coffee!");
                waterStorage -= typeOfCoffee.water;
                milkStorage -= typeOfCoffee.milk;
                coffeeBeanStorage -=  typeOfCoffee.coffeeBeans;
                moneyStorage += typeOfCoffee.cost;
                noOfCupsAvailable -= 1;
            } else {
                ingredient = waterReq == 0 ? "water" :
                        milkReq == 0 ? "milk" :
                                coffeeBeansReq == 0 ? "coffee beans" :
                                        noOfCupsAvailable == 0 ? "disposable cups" : "error";
                System.out.printf("Sorry, not enough %s!", ingredient);
            }
        }
        state = MachineState.AWAITING_ACTION;
    }

    void selectCoffee() {
        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String userSelectedCoffee = getUserStringInput();
        switch (userSelectedCoffee) {
            case "1" -> //espresso
                    makeCoffee(espresso);
            case "2" ->//latte
                    makeCoffee(latte);
            case "3" ->//cappuccino
                    makeCoffee(cappuccino);
            case "back" ->//back to main menu
                    state = MachineState.AWAITING_ACTION;
            default -> {
                state = MachineState.AWAITING_ACTION;
                System.out.println("Invalid user selection!");
            }
        }// end of inside switch block
    }
    void fillIngredients() {
        System.out.println("Write how many ml of water you want to add: ");
        int addWater = getUserIntInput();
        System.out.println("Write how many ml of milk you want to add: ");
        int addMilk = getUserIntInput();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        int addCoffeeBeans = getUserIntInput();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        int addCups = getUserIntInput();

        waterStorage += addWater;
        milkStorage += addMilk;
        coffeeBeanStorage += addCoffeeBeans;
        noOfCupsAvailable += addCups;

        state = MachineState.AWAITING_ACTION;
    }
    void displayRemaining () {
        System.out.println("\nThe coffee machine has:");
        System.out.printf("%d ml of water\n", waterStorage);
        System.out.printf("%d ml of milk\n", milkStorage);
        System.out.printf("%d g of coffee beans\n", coffeeBeanStorage);
        System.out.printf("%d disposable cups\n", noOfCupsAvailable);
        System.out.printf("$%d of money\n", moneyStorage);
    }
    void startCoffeeMachine () {
        while (state.equals(MachineState.AWAITING_ACTION)) {

            System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
            String userAction = getUserStringInput();

            switch (userAction) {
                case "buy" -> {
                    state = MachineState.AWAITING_COFFEE_SELECTION;
                    selectCoffee();
                }
                case "fill" -> {
                    state = MachineState.AWAITING_FILLING_INGREDIENTS;
                    fillIngredients();
                }
                case "take" -> {
                    System.out.printf("I gave you $%d", moneyStorage);
                    moneyStorage -= moneyStorage;
                }
                case "remaining" -> displayRemaining();
                case "exit" -> state = MachineState.EXIT;
                default -> System.out.println("Invalid user action!");
            }//end of switch statement
        }//end of while
    }
}
public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.startCoffeeMachine();
    }
}
