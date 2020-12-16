package Task1;

import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PrimeFactors {

    private static final String SAMPLE_CSV_FILE_PATH = "MyFile.csv";
    public static void main(String[] args) throws IOException {
        Instant start = Instant.now();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number");
        int n = sc.nextInt();
        if(ConfigurationReader.get(String.valueOf(n))!=null){
            System.out.println("primeFactorsList2(n) = " +ConfigurationReader.get(String.valueOf(n)));
            System.out.println("I have it");
        }else{
            ConfigurationReader.setProperties(String.valueOf(n),primeFactorsList2(n));
            System.out.println("primeFactorsList2(n) = " + primeFactorsList2(n));
            System.out.println(" I used method");
        }
        // time passes
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("elapsedTime = " + timeElapsed);

    }
    //return list of primeFactors
    public static List<Integer> primeFactorsList(int n) {
        List<Integer> primeFactors = new ArrayList<>();
        // Print the number of 2s that divide n
        while (n % 2 == 0) {
            primeFactors.add(2);
            n /= 2;
        }
        // n must be odd at this point.

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            while (n % i == 0) {
                primeFactors.add(i);
                n /= i;
            }
        }
        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2){
            primeFactors.add(n);
        }

        //stream/distinct
        return primeFactors.stream().distinct().collect(Collectors.toList());
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
        return group;
    }
    //reader by header
    public static List<String> myReaderPrimes() throws IOException {
        List<String> myList = new ArrayList<>();
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String email = csvRecord.get("Prime Factors");
                myList.add(email);

            }
        }

        return myList;
    }
    //writing result to csv file
    public static void writeResult(int inputNumber, List<Integer> primeFactors){
        try (
                BufferedWriter writer = Files.newBufferedWriter(Paths.get(SAMPLE_CSV_FILE_PATH));

                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Input Number", "Prime Factors"));
        ) {
            writer.newLine();
            csvPrinter.printRecord(String.valueOf(inputNumber), String.valueOf(primeFactors));

            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
