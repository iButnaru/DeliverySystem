package Entities;

public class BlockedCustomer {

    private int bid;
    private String bfirstname;
    private String blastname;
    private String bemail;
    private String bpassword;
    private String bcity;
    private String baddress;
    private long bphone;

    public BlockedCustomer() {
    }

    public BlockedCustomer(String bfirstname, String blastname, String bemail, String bpassword, String bcity, String baddress, long bphone) {
        this.bfirstname = bfirstname;
        this.blastname = blastname;
        this.bemail = bemail;
        this.bpassword = bpassword;
        this.bcity = bcity;
        this.baddress = baddress;
        this.bphone = bphone;
    }

    public BlockedCustomer(int bid, String bfirstname, String blastname, String bemail, String bpassword, String bcity, String baddress, long bphone) {
        this.bid = bid;
        this.bfirstname = bfirstname;
        this.blastname = blastname;
        this.bemail = bemail;
        this.bpassword = bpassword;
        this.bcity = bcity;
        this.baddress = baddress;
        this.bphone = bphone;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBfirstname() {
        return bfirstname;
    }

    public void setBfirstname(String bfirstname) {
        this.bfirstname = bfirstname;
    }

    public String getBlastname() {
        return blastname;
    }

    public void setBlastname(String blastname) {
        this.blastname = blastname;
    }

    public String getBemail() {
        return bemail;
    }

    public void setBemail(String bemail) {
        this.bemail = bemail;
    }

    public String getBpassword() {
        return bpassword;
    }

    public void setBpassword(String bpassword) {
        this.bpassword = bpassword;
    }

    public String getBcity() {
        return bcity;
    }

    public void setBcity(String bcity) {
        this.bcity = bcity;
    }

    public String getBaddress() {
        return baddress;
    }

    public void setBaddress(String baddress) {
        this.baddress = baddress;
    }

    public long getBphone() {
        return bphone;
    }

    public void setBphone(long bphone) {
        this.bphone = bphone;
    }
}
