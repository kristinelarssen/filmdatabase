import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Velkommen til vårt superprogram! Hva kan vi hjelpe deg med? ");
        System.out.println("Vi tilbyr følgende tjenester: ");
        System.out.println("1 - Finne navnet på alle rollene Heidi har");
        System.out.println("2 - Finne hvilke filmer som Svein har bidratt ii");
        System.out.println("3 - Finne hvilket filmselskap som lager flest Action filmer");
        System.out.println("4 - Sette inn en ny film");
        System.out.println("5 - Sette inn ny anmeldelse av en episode av en serie");

        Scanner myObj = new Scanner(System.in);
        int i = 10;
        while(i > 0) {
            System.out.println("\tVelg et alternativ: ");
            int alternativ = Integer.parseInt(myObj.nextLine());
            while (!isValidInputOption(alternativ)) {
                System.out.println("Det var feil. Prøv igjen");
                alternativ = Integer.parseInt(myObj.nextLine());
            }
            System.out.println("\tDu valgte alternativ: " + alternativ);
            direct(alternativ);
        }

        myObj.close();
    }

    private static boolean isValidInputOption(int i) {
        List<Integer> validInputs = Arrays.asList(1, 2, 3, 4, 5);
        return validInputs.contains(i);
    }

    private static void direct(int alternativ) {
        PrinterCtrl printer = new PrinterCtrl();
        printer.connect();
        // når man lager et objekt sånn som er tomt, så kan man bruke metodene uten å
        // gjøre de static

        switch (alternativ) {
            case 1:
                printer.printRoller("1");
                break;
            case 2:
                printer.printFilmer("2");
                break;
            case 3:
                printer.printFilmselskap("Action");
                break;
            case 4:
                // ---------- HUSK Å LEGGE TIL NYE NAVN OG TING HER FØR KJØRING -------------
                FilmCtrl filmCtrl = new FilmCtrl();
                filmCtrl.connect();

                filmCtrl.regSelskap("EksildsSelskap", "Os 78", "Norge");
                filmCtrl.regProdukt(13, "Testing 1", "01:46:15", "2014-09-06", true, "DVD",
                        "Dette er en test lol");

                Sjanger sjanger1 = new Sjanger();
                sjanger1.connect();
                sjanger1.regSjanger("test1");

                Sjanger sjanger2 = new Sjanger();
                sjanger2.connect();
                sjanger2.regSjanger("test2");

                filmCtrl.regSjanger(sjanger1);
                filmCtrl.regSjanger(sjanger2);

                Aktor aktor1 = new Aktor();
                aktor1.connect();
                aktor1.regAktor(18, "Svanilden", "Norge", 1987);

                Aktor aktor2 = new Aktor();
                aktor2.connect();
                aktor2.regAktor(19, "Borris", "Sverige", 1915);

                Aktor aktor3 = new Aktor();
                aktor3.connect();
                aktor3.regAktor(20, "Torjus", "Norge", 1996);

                filmCtrl.regBidrarifilm("Skuespiller", "Aktor mor", aktor1);
                filmCtrl.regBidrarifilm("Regissør", null, aktor1);
                filmCtrl.regBidrarifilm("Skuespiller", "Storebror", aktor2);
                filmCtrl.regBidrarifilm("Manusforfatter", null, aktor3);
                break;
            case 5:
                // ---------- HUSK Å LEGGE TIL NYE NAVN OG TING HER FØR KJØRING -------------
                AnmeldelseCtrl anmCtrl = new AnmeldelseCtrl();
                anmCtrl.connect();
                anmCtrl.regEpisode(1,1,7);  // episodenummer må være nytt

                Bruker bruker1 = new Bruker();
                bruker1.connect();
                bruker1.regBruker(10);

                anmCtrl.regAnmeldelse("Bra jobba", 7, bruker1);
                break;
        }
    }
}
