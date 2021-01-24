package humphreys;

import java.util.ArrayList;
import java.util.Scanner;

import static humphreys.PrintWeatherDetails.*;

public class Main {

    //Setting up some constants to be used in the methods here
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        String city;
        String choice;
        ArrayList<String> cities = new ArrayList<>();


        System.out.println("What would you like to do?");
        System.out.print("Enter 1 to check the Weather for one City or 2 to compare multiple cities: ");
        choice = sc.nextLine();
        boolean askCity = true;

        if (choice.equals("1")) {
            while (askCity) {
                System.out.print("Enter the name of the city to check the weather('quit' to quit): ");
                city = sc.nextLine();
                if (city.equals("quit")) {
                    askCity = false;
                } else {
                    printCityWeather(city);
                }
            }
        } else if (choice.equals("2")) {
            while (askCity) {
                System.out.print("Enter the name of one of the cities('quit' to quit): ");
                city = sc.nextLine();
                if (city.equals("quit")){
                    askCity = false;
                } else {
                    cities.add(city);
                }
            }
            printCityComparison(cities);
        }
    }
}

