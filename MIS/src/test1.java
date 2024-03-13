import java.time.LocalDate;
import java.util.Calendar;
import java.util.Scanner;

public class test1 {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        String reg = "[0-9]{2}-[0-9]{2}-[0-9]{4}";
        if (str.matches(reg)){
            LocalDate date = LocalDate.parse(str);
            System.out.println(date);
        }
    }
}
