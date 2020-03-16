import java.sql.*;
public class Sjanger extends DBConn{

    protected String kategori;
    private PreparedStatement prs;
    private final static String INGEN_SJANGER = "INGEN_SJANGER";

    // ----------------- KONSTRUKTØR  ------------------

    public Sjanger() {
        this.kategori = INGEN_SJANGER;
    }

    // ----------------- REGISTRERE KATEGORI I DB ------------------

    public void regSjanger(String kategori) {
        this.kategori = kategori;
        try {
            prs = conn.prepareStatement("INSERT INTO kategori VALUES ((?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into kategori: " + e);
        }

        if (this.kategori != INGEN_SJANGER) {
            try {
                prs.setString(1, kategori);
                prs.execute();
                System.out.println("Sjangeren " + this.kategori + " er nå satt inn i databasen");
            } catch (Exception e) {
                System.out.println("db during insert of kategori= " + this.kategori + " : " + e);
            }
        }
    }
}
