package Task1;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PrimeFactors {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number");
        int n = sc.nextInt();
        calculate(n);

    }

    public static void calculate(int n){
        Instant start = Instant.now();
        if(ConfigurationReader.get(String.valueOf(n))!=null){
            System.out.println("Prime Factors List = " +ConfigurationReader.get(String.valueOf(n)));
            System.out.println("I used library");
        }else{
            ConfigurationReader.setProperties(String.valueOf(n),primeFactorsList2(n));
            System.out.println("Prime Factors List  = " + primeFactorsList2(n));
            System.out.println(" I used method");
        }
        // time passes
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("elapsedTime = " + timeElapsed);
    }

    public static String primeFactorsList2(int n) {
        StringBuilder list = new StringBuilder();
        // Print the number of 2s that divide n
        while (n % 2 == 0) {
            list.append(2).append(",");
            n /= 2;
        }
        // n must be odd at this point.

        for (int i = 3; i <= n; i += 2) {
            while (n % i == 0) {
                list.append(i).append(",");
                n /= i;
            }
        }
        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2){
            list.append(2).append(",");
        }

        String reg = "(.*)(,)";
        String lister = list.toString();
        Matcher matcher = Pattern.compile(reg).matcher(lister);
        String group ="";
        while(matcher.find()){
            group = ""+matcher.group(1);
        }
        String s = Arrays.stream(group.split(",")).distinct().collect(Collectors.toList()).toString();
        return s.substring(1,s.length()-1);
    }
}
