package net.muzichko.moviecatalog.test;


import java.util.Random;

public class Utils {

    public static String randomEnglishSymbolString(int size){

        Random random = new Random();
        String result = "";
        char[] arr = "abcdefjhigklmnopqrstuvwxyz".toCharArray();

        for(int i = 0; i < size; i++){
            int r = random.nextInt(arr.length);
            result += arr[r];
        }

        return result;
    }

    public static String randomEnglishSymbolNumbersString(int size){

        Random random = new Random();
        String result = "";
        char[] arr = "abcdefjhigklmnopqrstuvwxyz0123456789".toCharArray();

        for(int i = 0; i < size; i++){
            int r = random.nextInt(arr.length);
            result += arr[r];
        }

        return result;
    }

    public static String randomEmail(){

        return randomEnglishSymbolNumbersString(15) + "@mail.ru";

    }

    public static String randomCyrillicSymbolString(int size){

        Random random = new Random();
        String result = "";
        char[] arr = "абвгдежзийклмнопрстуфхцчшщюяьъ".toCharArray();

        for(int i = 0; i < size; i++){
            int r = random.nextInt(arr.length);
            result += arr[r];
        }

        return result;
    }

    public static String randomIncorrectSymbolString(int size){

        Random random = new Random();
        String result = "";
        char[] arr = "_=+-&%#@^*{}[]".toCharArray();

        for(int i = 0; i < size; i++){
            int r = random.nextInt(arr.length);
            result += arr[r];
        }

        return result;
    }

}
