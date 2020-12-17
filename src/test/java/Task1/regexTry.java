package Task1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regexTry {
    public static void main(String[] args) {
        //NOT working
        String reg = "(.*)(,)";
        String lister = "2,3,4,5,";
        Matcher matcher = Pattern.compile(reg).matcher(lister);
        String s1 = lister.replaceAll(matcher.group(1), "");
        System.out.println("s1 = " + s1);
    }
}
