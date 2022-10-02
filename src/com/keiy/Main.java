package com.keiy;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public enum RomanNumbers {
        O, I, II, III, IV, V, VI, VII, VIII, IX, X,
        XI, XII, XIII, XIV, XV, XVI, XVII, XVIII, XIX, XX,
        XXI, XXII, XXIII, XXIV, XXV, XXVI, XXVII, XXVIII, XXIX, XXX,
        XXXI, XXXII, XXXIII, XXXIV, XXXV, XXXVI, XXXVII, XXXVIII, XXXIX, XXXX,
        XXXXI, XXXXII, XXXXIII, XXXXIV, XXXXV, XXXXVI, XXXXVII, XXXXVIII, XXXXIX, L,
        LI, LII, LIII, LIV, LV, LVI, LVII, LVIII, LIX, LX,
        LXI, LXII, LXIII, LXIV, LXV, LXVI, LXVII, LXVIII, LXIX, LXX,
        LXXI, LXXII, LXXIII, LXXIV, LXXV, LXXVI, LXXVII, LXXVIII, LXXIX, LXXX,
        LXXXI, LXXXII, LXXXIII, LXXXIV, LXXXV, LXXXVI, LXXXVII, LXXXVIII, LXXXIX, LXXXX,
        LXXXXI, LXXXXII, LXXXXIII, LXXXXIV, LXXXXV, LXXXXVI, LXXXXVII, LXXXXVIII, LXXXXIX, C
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите выражение: ");

        String expression = scanner.nextLine().replaceAll(" ", "");

        System.out.println(calc(expression));

        scanner.close();
    }

    public static String calc(String input) throws RuntimeException {
        checkInput(input);
        boolean isArabic = isArabic(input);
        int answer = calculate(input, isArabic);

        return convertToStr(answer, isArabic);
    }

    public static void checkInput(String input) {
        // checks if an expression has 2 operands and 1 operator
        if (!input.matches("([0-9]+[+\\-*/][0-9]+)|([IVX]+[+\\-*/][IVX]+)"))
            throw new RuntimeException("Math expression is not correct");

        boolean isArabic = isArabic(input);
        Matcher matcher = Pattern.compile("[IVX0-9]+").matcher(input);

        // checks if operands are in given range
        while (matcher.find()) {
            checkRange(convertToInt(matcher.group(), isArabic));
        }
    }

    public static boolean isArabic(String input) {
        return input.matches("[0-9]+[+\\-*/][0-9]+");
    }

    public static int[] getOperands(String input, boolean isArabic) {
        return Stream.of(input.split("[+\\-*/]"))
                .mapToInt(operand -> convertToInt(operand, isArabic))
                .toArray();
    }

    public static void checkRange(int operand) {
        if (!Integer.toString(operand).matches("[1-9]|(10)"))
            throw new RuntimeException("One or both operands are less than 1 or greater than 10");
    }

    public static int calculate(String input, boolean isArabic) {
        int[] operands = getOperands(input, isArabic);
        int answer;
        switch (getOperator(input)) {
            case '+':
                answer = operands[0] + operands[1];
                break;
            case '-':
                answer = operands[0] - operands[1];
                break;
            case '*':
                answer = operands[0] * operands[1];
                break;
            default:
                answer = operands[0] / operands[1];
        }

        return isArabic ? answer : checkRomanNumber(answer);
    }

    public static char getOperator(String input) {
        return input.replaceAll("[IVX0-9]", "").charAt(0);
    }

    public static int convertToInt(String number, boolean isArabic) {
        return isArabic ? Integer.parseInt(number) : RomanNumbers.valueOf(number).ordinal();
    }

    public static String convertToStr(int number, boolean isArabic) {
        return isArabic ? String.valueOf(number) : RomanNumbers.values()[number].toString();
    }

    public static int checkRomanNumber(int number) {
        if (number < 1)
            throw new RuntimeException("Roman numeral system doesn't have numbers less than 1");

        return number;
    }
}