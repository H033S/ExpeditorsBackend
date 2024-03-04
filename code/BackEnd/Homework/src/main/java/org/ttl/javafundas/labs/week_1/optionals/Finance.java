package org.ttl.javafundas.labs.week_1.optionals;

import java.util.Arrays;
import java.util.Map;

public class Finance {
    public final static String BEST_LOAN_RATES  = "bestLoanRates";
    public final static String SAVINGS_CALCULATOR = "savingsCalculator";
    public final static String MORTGAGE_CALCULATOR = "mortgageCalculator";

    public final static Map<String, String> commandsToUsage = Map.of(
            BEST_LOAN_RATES, "usage: bestLoanRates",
            SAVINGS_CALCULATOR, "usage: savingsCalculator <credits separated by ','> <debits separated by ','>",
            MORTGAGE_CALCULATOR, "usage: mortgageCalculator <loanAmount> <termInYears> <annualRate>"
    );

    private static boolean validateCommandArguments(String[] args){

        return switch (args[0]) {
            case BEST_LOAN_RATES -> args.length == 1;
            case SAVINGS_CALCULATOR -> args.length == 3;
            case MORTGAGE_CALCULATOR -> args.length == 4;
            default -> false;
        };
    }

    private static void executeCommand(String command, String[] arguments){
        switch (command) {
            case BEST_LOAN_RATES:
                System.out.println("Finding best loan rates ...");
                BestLoanRates.main(arguments);
                return;
            case SAVINGS_CALCULATOR:
                System.out.println("Finding your net savings ...");
                SavingsCalculator.main(arguments);
                return;
            case MORTGAGE_CALCULATOR:
                System.out.println("Finding your monthly payment ...");
                MortgageCalculator.main(arguments);
                return;
        }
    }

    public static void main(String[] args) {
        String command  = args[0];

        if(!commandsToUsage.containsKey(command)){
            System.out.println(command + ": command not found");
            return;
        }
        boolean isValidCommand = validateCommandArguments(args);
        if(!isValidCommand){
            System.out.println(commandsToUsage.get(command));
            return;
        }

        executeCommand(command, Arrays.copyOfRange(args, 1, args.length));
    }

}
