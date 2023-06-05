import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double nieuw = 0;
        double getal1 = 0;
        int i;
        for (i = 1; i <=10; i++) {
            double getal = scanner.nextDouble();

            getal1 += getal;

            if (getal > nieuw){
                nieuw = getal;
            }

        }
            System.out.println(nieuw);
            System.out.println(getal1 / i);
        }
    }

