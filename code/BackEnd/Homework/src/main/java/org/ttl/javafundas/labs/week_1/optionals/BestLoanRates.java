package org.ttl.javafundas.labs.week_1.optionals;

import java.util.Map;
import java.util.Scanner;

public class BestLoanRates {
    public static final Map<Integer, Float> bestRates = Map.of();

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = scanner.next();
        System.out.println("Hello " + name);
        System.out.println("Enter the loan term (in years)");
        int loanTermInYears = scanner.nextInt();
        float bestRate = getRates(loanTermInYears);

        if(bestRate == 0.0F){
            System.out.println("No available rates for term: " + loanTermInYears + " years");
        }else System.out.println("Best Available Rate: " + bestRate + "%");
    }

    public static Float getRates(Integer loanTermsInYears){
        return bestRates.getOrDefault(loanTermsInYears, 0.0F);
    }
}
