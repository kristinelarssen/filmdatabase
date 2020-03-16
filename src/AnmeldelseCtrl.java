import java.sql.*;

public class AnmeldelseCtrl extends DBConn {

    protected int produktID;
    protected int snummer;
    protected int enummer;
    private PreparedStatement anm_rs;
    private PreparedStatement ep_rs;
    private static final int INGEN_PRODUKTID = -1;
    private static final int INGEN_SNUMMERID = -1;
    private static final int INGEN_ENUMMERID = -1;

    // ----------------- KONSTRUKTØR ------------------

    public AnmeldelseCtrl() {
        this.produktID = INGEN_PRODUKTID;
        this.enummer = INGEN_ENUMMERID;
        this.snummer = INGEN_SNUMMERID;
    }


    // ----------------- REGISTRERER EPISODE I DB------------------

    public void regEpisode(int produktID, int snummer, int enummer) {
        this.produktID = produktID;
        this.snummer = snummer;
        this.enummer = enummer;
        try {
            ep_rs = conn.prepareStatement("INSERT INTO episode VALUES ((?), (?), (?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into episode: " + e);
        }
        if ((this.produktID != INGEN_PRODUKTID) && (this.snummer != INGEN_SNUMMERID) && (this.enummer != INGEN_ENUMMERID)) {
            try {
                ep_rs.setInt(1, enummer);
                ep_rs.setInt(2, snummer);
                ep_rs.setInt(3, produktID);
                ep_rs.execute();
                System.out.println("Episoden med enr:" + enummer + " snr: " + snummer + " sesongid: " + produktID + " er satt inn i databasen");
            } catch (Exception e) {
                System.out.println("db during insert of episode: " + e);
            }
        }
    }


    // ----------------- REGISTRERER EPISODEANMELDELSE I DB ------------------

    public void regAnmeldelse(String tekst, int rating, Bruker bruker) {
        try {
            anm_rs = conn.prepareStatement("INSERT INTO episodeanmeldelse VALUES ((?), (?), (?), (?), (?), (?))");
        } catch (Exception e) {
            System.out.println("db error during prepare of insert into episodeanmeldelse: " + e);
        }
        if (bruker.brukerID != 0) {
            try {
                anm_rs.setInt(1, bruker.brukerID);
                anm_rs.setInt(2, this.produktID);
                anm_rs.setInt(3, this.snummer);
                anm_rs.setInt(4, this.enummer);
                anm_rs.setString(5, tekst);
                anm_rs.setInt(6, rating);
                anm_rs.execute();
                System.out.println("Anmeldelse skrevet av bruker" + bruker.brukerID + " er satt inn i databasen");
            } catch (Exception e) {
                System.out.println("db during insert of episodeanmeldelse: " + e);
            }
        }
    }

}

