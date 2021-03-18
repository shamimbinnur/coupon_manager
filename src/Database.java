import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    public synchronized boolean writeToDatabase(String couponID, double discount, String expire) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            String sql = "INSERT INTO couponStock (Discount, Status, CouponID, Expire) " +
                    "VALUES ('" + discount + "', '" + "Unclaimed" + "',  '" + couponID + "', '" + expire +"' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }


    public synchronized boolean book(String customerName, String couponID, String exDate){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock where CouponID = '"+couponID+"'" );
            System.out.println(resultSet.isClosed());
            if (!resultSet.isClosed()){
                String sql = "UPDATE couponStock SET CustomerName = '"+customerName+"',"  +"Expire = '"+ exDate +"',"  +"Status ='Booked'  Where CouponID = '"+couponID+"' " ;
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
                return true;
            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean bookWithouEx(String customerName, String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock where CouponID = '"+couponID+"'" );
            System.out.println(resultSet.isClosed());
            if (!resultSet.isClosed()){
                String sql = "UPDATE couponStock SET CustomerName = '"+customerName+"',"  +"Status ='Booked'  Where CouponID = '"+couponID+"' " ;
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
                return true;
            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized int is_Unique(String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock" );
            if (!resultSet.isClosed()){
                while (resultSet.next()){
                    if (resultSet.getString("CouponID").equals(couponID)){
                        stmt.close();
                        c.close();
                        return 0;
                    }
                }
            }
            stmt.close();
            c.close();
            return 1;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return -1;
        }
    }

    public synchronized boolean checkValidity(String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock" );
            if (!resultSet.isClosed()){
                while (resultSet.next()){
                    if (resultSet.getString("CouponID").equals(couponID)){
                        stmt.close();
                        c.close();
                        return true;
                    }
                }
            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean isBooked(String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock where CouponID = '"+couponID+"'; " );
            if (!resultSet.isClosed()){
                if (resultSet.getString("Status").equals("Booked")){
                    stmt.close();
                    c.close();
                    return true;
                }

            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean isClaimed(String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock where CouponID = '"+couponID+"'; " );
            if (!resultSet.isClosed()){
                if (resultSet.getString("Status").equals("Claimed")){
                    stmt.close();
                    c.close();
                    return true;
                }
            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean isUnclaimed(String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock where CouponID = '"+couponID+"'; " );
            if (!resultSet.isClosed()){
                if (resultSet.getString("Status").equals("Unclaimed")){
                    stmt.close();
                    c.close();
                    return true;
                }
            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean isDateNA(String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock where CouponID = '"+couponID+"'; " );
            if (!resultSet.isClosed()){
                if (resultSet.getString("Expire").equals("NA")){
                    stmt.close();
                    c.close();
                    return true;
                }
            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized String[] getInfo(String couponID){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock Where CouponID = '"+couponID+"'" );
            if (!resultSet.isClosed()){
                String[] info = new String[5];
                info[0] = resultSet.getString("CustomerName");
                info[1] = resultSet.getString("Discount");
                info[2] =  resultSet.getString("ClaimingDate");
                info[3] =  resultSet.getString("Status");
                info[4] =  resultSet.getString("Expire");
                stmt.close();
                c.close();
                return info;
            }
            stmt.close();
            c.close();
            return null;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return null;
        }
    }

    public synchronized boolean claim( String couponID, String claimingDate){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock where CouponID = '"+couponID+"';" );
            System.out.println(resultSet.isClosed());
            if (!resultSet.isClosed()){
                String sql = "UPDATE couponStock SET ClaimingDate = '"+ claimingDate +"',"  +"Status ='Claimed' where CouponID = '"+couponID+"'; ";
                stmt.executeUpdate(sql);
                stmt.close();
                c.close();
                return true;
            }
            stmt.close();
            c.close();
            return false;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean del(String pm) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            String sql = "DELETE FROM couponStock WHERE No = '"+pm+"'";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public synchronized int getStockQ(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock" );
            int qnt = 0;
            if (!resultSet.isClosed()){
                while (resultSet.next()){
                    qnt ++;
                }
                stmt.close();
                c.close();
                return qnt;
            }
            stmt.close();
            c.close();
            return 0;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }

    public synchronized int getBookedQ(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock Where Status = 'Booked'" );
            int qnt = 0;
            if (!resultSet.isClosed()){
                while (resultSet.next()){
                    qnt ++;
                }
                stmt.close();
                c.close();
                return qnt;
            }
            stmt.close();
            c.close();
            return 0;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }

    public synchronized int getClaimedQ(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database/database.db");
            c.setAutoCommit(true);
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery( "SELECT * FROM couponStock Where Status = 'Claimed'" );
            int qnt = 0;
            if (!resultSet.isClosed()){
                while (resultSet.next()){
                    qnt ++;
                }
                stmt.close();
                c.close();
                return qnt;
            }
            stmt.close();
            c.close();
            return 0;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }
}
