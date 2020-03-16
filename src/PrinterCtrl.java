import java.sql.*;
public class PrinterCtrl extends DBConn{

    // ----------------- SKRIVER UT FILMENE EN GITT SKUESPILLER HAR SPILT I ------------------
    public void printFilmer(String filmaktor) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT produkt.tittel FROM bidrarifilm INNER JOIN produkt ON bidrarifilm.produktid = produkt.produktid WHERE bidrarifilm.tittel='skuespiller' AND aktorid='"
                    + filmaktor + "' ";
            ResultSet rs = stmt.executeQuery(query);

            // skriver ut alle filmene en skuespiller er med i
            System.out.println("Detter er filmene som Svein har spilt i: ");
            while (rs.next()) {
                System.out.println(rs.getString("produkt.tittel"));
            }
        } catch (Exception e) {
            System.out.println("db error during select of skuespiller = " + e);
        }
    }

    // ----------------- SKRIVER UT FILMSELSKAPET MED FLEST FILMER I EN GITT SJANGER ------------------
    public void printFilmselskap(String sjanger) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT SelskapURL, COUNT(tabell.ProduktID) AS antall FROM (SELECT ProduktID FROM sjangerogfilm WHERE Sjanger='" + sjanger + "') AS tabell NATURAL JOIN Produkt GROUP BY SelskapURL ORDER BY COUNT(ProduktID) DESC LIMIT 0, 1000";
            ResultSet rs = stmt.executeQuery(query);

            // skriver ut filmselskapet med flest filmer i gitt sjanger
            System.out.println("Detter er filmselskapet som har flest filmer i sjangeren " + sjanger + ": ");
            if (rs.next()) {
                System.out.println(rs.getString("SelskapURL") + " som har " + rs.getInt(2) + " " + sjanger + " film(er)");
            }
        } catch (Exception e) {
            System.out.println("db error during select of filmselskap = " + e);
        }
    }

    // ----------------- SKRIVER UT ROLLENE EN SKUESPILLER HAR HATT ------------------
    public void printRoller(String filmaktor) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT rolle FROM bidrarifilm WHERE aktorid='" + filmaktor + "' AND tittel='skuespiller'";
            ResultSet rs = stmt.executeQuery(query);

            // skriver ut alle rollene en skuespiller har
            System.out.println("Detter er rollene som Heidi har hatt: ");
            while (rs.next()) {
                System.out.println(rs.getString("rolle"));
            }
        } catch (Exception e) {
            System.out.println("db error during select of skuespillers roller = " + e);
        }
    }
}
