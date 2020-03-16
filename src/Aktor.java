import java.sql.*;

public class Aktor extends DBConn{
    protected int aktorID;
    private PreparedStatement prs;

    private final static int INGEN_AKTORID = -1;

    // ----------------- KONSTRUKTØR ------------------

    public Aktor() {
        this.aktorID = INGEN_AKTORID;
    }

    // ----------------- REGISTRERER FILMAKTOR I DB ------------------

    public void regAktor(int aktorID, String navn, String land, int aar) {
        this.aktorID = aktorID;
        try {
            prs = conn.prepareStatement("INSERT INTO filmaktor VALUES ((?), (?), (?), (?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into filmaktor: " + e);
        }

        if (aktorID != INGEN_AKTORID) {
            try {
                prs.setInt(1, aktorID);
                prs.setString(2, navn);
                prs.setString(3, land);
                prs.setInt(4, aar);
                prs.execute();
                System.out.println("Aktoren " + this.aktorID + " er lagt til i databasen");
            } catch (Exception e) {
                System.out.println("db during insert of Filmaktor, aktorID= " + aktorID + " : " + e);
            }
        }
    }
}
