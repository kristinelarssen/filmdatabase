import java.sql.*;
public class Bruker extends DBConn{
    protected int brukerID;
    private PreparedStatement prs;
    private static final int INGEN_BRUKERID = -1;

    // ----------------- KONSTRUKTØR ------------------ /// mååååå deles i to

    public Bruker() {
        this.brukerID = INGEN_BRUKERID;
    }

    // ----------------- REGISTRERER BRUKER I DB ------------------
    public void regBruker(int brukerID) {
        this.brukerID = brukerID;
        try {
            prs = conn.prepareStatement("INSERT INTO bruker VALUES ((?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into bruker: " + e);
        }
        if (brukerID != INGEN_BRUKERID) {
            try {
                prs.setInt(1, brukerID);
                prs.execute();
                System.out.println("Bruker " + brukerID + " har blitt satt inn i databasen");
            } catch (Exception e) {
                System.out.println("db during insert of brukerID= " + brukerID + " : " + e);
            }
        }

    }
}
