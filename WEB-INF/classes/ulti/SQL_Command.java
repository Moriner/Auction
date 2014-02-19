package ulti;

public interface SQL_Command {
	public static final String Create_User1="select * from sys.systables where tablename = 'USER_AUC'";
	public static final String Create_User2="create table USER_AUC(username varchar(30), password varchar(30))";
	public static final String Create_User3="select * from USER_AUC where username = ?";
	public static final String Create_User4="insert into USER_AUC values(?, ?)";
	
	public static final String Verify_User1="select * from sys.systables where tablename = 'USER_AUC'";
	public static final String Verify_User2="create table USER_AUC(username varchar(30), password varchar(30))";
	public static final String Verify_User3="select * from USER_AUC where username = ? and password = ?";
	
	public static final String Create_Auction1="select * from sys.systables where tablename = 'AUCTION'";
	public static final String Create_Auction2="create table AUCTION(owner varchar(30), name varchar(30), description varchar(500)," +
					"reserve_price int, buyout_price int, start_price int, " +
					"status boolean, end_date Timestamp NOT NULL, current_bid_price int)";
	public static final String Create_Auction3="select * from AUCTION where owner = ?  and name =?";
	public static final String Create_Auction4="insert into AUCTION values(?,?,?,?,?,?,?,?,?)";
	

	
	public static final String Bid_for_Auction="insert into BID values(?,?,?,?)";
	public static final String Create_Bid = "select * from sys.systables where tablename = 'BID'";
	
	public static final String update1="update auction set status = false where {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} < 0";
	public static final String update2="update auction set current_bid_price = 0 where status = false and current_bid_price < reserve_price and current_bid_price <> buyout_price";
}
