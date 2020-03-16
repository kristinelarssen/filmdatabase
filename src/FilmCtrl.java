import java.sql.*;
public class FilmCtrl extends DBConn {
    protected int produktID; // 10
    protected String url; //    "StoreNorskeSelskap.no"

    private PreparedStatement selskap_rs;
    private PreparedStatement film_rs;
    private PreparedStatement sjanger_rs;
    private PreparedStatement aktor_rs;

    private static final String INGEN_URL = "INGEN_URL";
    private static final int INGEN_PRODUKTID = -1;

    // ----------------- KONSTRUKTØR ------------------

    public FilmCtrl() {
        this.produktID = INGEN_PRODUKTID;
        this.url = INGEN_URL;
    }

    // ----------------- REGISTRERING AV FILMSELSKAP I DB ------------------

    public void regSelskap(String url, String adresse, String land) {
        this.url = url;

        try {
            selskap_rs = conn.prepareStatement("INSERT INTO selskap VALUES ((?), (?), (?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into selskap: " + e);
        }

        if (!this.url.equals(INGEN_URL)) {
            try {
                selskap_rs.setString(1, url);
                selskap_rs.setString(2, adresse);
                selskap_rs.setString(3, land);
                selskap_rs.execute();
                System.out.println("Selskapet" + this.url + " er satt inn i databasen");
            } catch (Exception e) {
                System.out.println("db during insert of url= " + url + " : " + e);
            }
        }
    }

    // ----------------- REGISTRERING AV FILMPRODUKT I DB ------------------

    public void regProdukt(int produktID, String tittel, String lengde, String dato, boolean utgittpaavideo,
                           String lagetfor, String storyline) {
        this.produktID = produktID;

        try {
            film_rs = conn.prepareStatement("INSERT INTO produkt VALUES ((?), (?), (?), (?), (?), (?), (?), (?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into produkt: " + e);
        }

        if ((this.produktID != INGEN_PRODUKTID) && (!this.url.equals(INGEN_URL))) {
            try {
                film_rs.setInt(1, produktID);
                film_rs.setString(2, tittel);
                film_rs.setString(3, lengde); // skal være TIME egentlig
                film_rs.setString(4, dato); // skal være DATE egentlig
                film_rs.setBoolean(5, utgittpaavideo);
                film_rs.setString(6, lagetfor);
                film_rs.setString(7, storyline);
                film_rs.setString(8, url);
                film_rs.execute();
                System.out.println("Filmen " + this.produktID + " er satt inn i databasen");
            } catch (Exception e) {
                System.out.println("db during insert of produkt= " + produktID + " : " + e);
            }
        }
    }
    // ----------------- KOBLER FILMEN TIL SJANGRE ------------------

    public void regSjanger(Sjanger sjanger) {
        try {
            sjanger_rs = conn.prepareStatement("INSERT INTO sjangerogfilm VALUES ((?), (?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into sjangerogfilm: " + e);
        }
        try {
            sjanger_rs.setString(1, sjanger.kategori);
            sjanger_rs.setInt(2, this.produktID);
            sjanger_rs.execute();
            System.out.println("Filmen " + this.produktID +  " er knyttet opp til sjangeren: " + sjanger.kategori);
        } catch (Exception e) {
            System.out
                    .println("db during insert of sjangerogfilm= " + sjanger.kategori + " og " + this.produktID + " : " + e);
        }
    }

    // ----------------- KOBLER FILMAKTORER TIL FILMEN  ------------------

    public void regBidrarifilm(String tittel, String rolle, Aktor aktor) {
        try {
            aktor_rs = conn.prepareStatement("INSERT INTO bidrarifilm VALUES ((?), (?), (?), (?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into bidrarifilm: " + e);
        }

        if ((aktor.aktorID != 0) && (this.produktID != INGEN_PRODUKTID)) {
            try {
                aktor_rs.setInt(1, aktor.aktorID);
                aktor_rs.setInt(2, this.produktID);
                aktor_rs.setString(3, tittel);
                aktor_rs.setString(4, rolle);
                aktor_rs.execute();
                System.out.println("Bidrarifilm er oppdatert: Aktor= " + aktor.aktorID + " og Produkt= " + this.produktID);
            } catch (Exception e) {
                System.out.println(
                        "db during insert og aktorID= " + aktor.aktorID + " and produktID= " + produktID + " : " + e);
            }
        }
    }

}
