package model;

import java.sql.Connection;

import ulti.SQL_Command;
import ulti.Ulti_auction;
import ulti.Ulti_user;
import ulti.Ulti_bid;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Model {

	private static String driver = "org.apache.derby.jdbc.ClientDriver";
	private static String protocol = "jdbc:derby://localhost:1527/Auction;create=true";
	Connection conn = null;
	Statement s = null;
	ResultSet rs = null;
	List<Ulti_auction> list_auc1=new ArrayList<Ulti_auction>();
	List<Ulti_auction> list_auc2=new ArrayList<Ulti_auction>();
	List<Ulti_bid> list_bid1=new ArrayList<Ulti_bid>();
	List<Ulti_bid> list_bid2=new ArrayList<Ulti_bid>();
	
	
	public static void loadDriver() {
		try {
		Class.forName(driver).newInstance();
		System.out.println("Loaded the appropriate driver");
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		
	
	public void dropTables() throws Exception
	{
	    conn = DriverManager.getConnection(protocol);
        s=conn.createStatement();
	    s.execute("drop table USER_AUC");
	    s.execute("drop table AUCTION");
	    s.execute("drop table BID");
	}
	
	public boolean Create_User(Ulti_user user) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_User1);
		if(!a.next()) {
			s.execute(SQL_Command.Create_User2);
		}
		PreparedStatement ps1 = conn.prepareStatement(SQL_Command.Create_User3);
		ps1.setString(1, user.getUsername());
		rs=ps1.executeQuery();
		if(!rs.next()){
			PreparedStatement ps2 = conn.prepareStatement(SQL_Command.Create_User4);
			ps2.setString(1, user.getUsername());
			ps2.setString(2, user.getPassword());
			ps2.execute();
			return true;
		}else return false;
	} 
	
	public boolean Verify_User(Ulti_user user) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Verify_User1);
		if(!a.next()){
			s.execute(SQL_Command.Verify_User2);
		}
		PreparedStatement ps1 = conn.prepareStatement(SQL_Command.Verify_User3);
		ps1.setString(1, user.getUsername());
		ps1.setString(2, user.getPassword());
		rs=ps1.executeQuery();
		return rs.next();
	}
	
	public boolean Create_Auction(Ulti_auction auction) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
		if(!a.next()){
			s.execute(SQL_Command.Create_Auction2);
		}
		PreparedStatement ps1 = conn.prepareStatement(SQL_Command.Create_Auction3);
		ps1.setString(1, auction.getOwner());
		ps1.setString(2, auction.getName());
		rs=ps1.executeQuery();
		if(!rs.next()){
			PreparedStatement ps2 = conn.prepareStatement(SQL_Command.Create_Auction4);
			ps2.setString(1, auction.getOwner());
			ps2.setString(2, auction.getName());
			ps2.setString(3, auction.getDescription());
			ps2.setInt(4, auction.getReserve_price());
			ps2.setInt(5, auction.getBuyout_price());
			ps2.setInt(6, auction.getStart_price());
			ps2.setBoolean(7, auction.getStatus());
			System.out.println(auction.getEnd_date());
			ps2.setTimestamp(8, auction.getEnd_date());
			ps2.setInt(9, auction.getCurrent_bid_price());
			
			ps2.execute();
			rs = s.executeQuery("select end_date from auction");
			while(!rs.next()){
				System.out.println(rs.getTimestamp(1));
			}
			return true;
		}else return false;
	}
	
	public List<Ulti_auction> List_by_User(String user, boolean t_h,boolean order) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
		if(a.next()){
			s.execute(SQL_Command.update1);
			s.execute(SQL_Command.update2);
		
			if (user.equals("*"))
			{
			    if((t_h==true)&&(order==true)){
	                rs=s.executeQuery("select * from auction where  status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} ASC ");
	            }else if((t_h==true)&&(order==false)){
	                rs=s.executeQuery("select * from auction where  status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} DESC");
	            }else if((t_h==false)&&(order==true)){
	                rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and owner = '"+user+"') order by current_bid_price ASC");
	            }else if((t_h==false)&&(order==false)){
	                rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and owner = '"+user+"') order by current_bid_price DESC");;
	            }
			}
			else{
			if((t_h==true)&&(order==true)){
				rs=s.executeQuery("select * from auction where owner = '"+user+"' and status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} ASC ");
			}else if((t_h==true)&&(order==false)){
				rs=s.executeQuery("select * from auction where owner = '"+user+"' and status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} DESC");
			}else if((t_h==false)&&(order==true)){
				rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and owner = '"+user+"') order by current_bid_price ASC");
			}else if((t_h==false)&&(order==false)){
				rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and owner = '"+user+"') order by current_bid_price DESC");;
			}}
			
			
			
			while (rs.next()) {
				Ulti_auction auc=new Ulti_auction();
				auc.setOwner(rs.getString(1));
				auc.setName(rs.getString(2));
				auc.setDescription(rs.getString(3));
				auc.setReserve_Price(rs.getInt(4));
				auc.setBuyout_Price(rs.getInt(5));
				auc.setStart_Price(rs.getInt(6));
				auc.setStatus(rs.getBoolean(7));
				auc.setEnd_date(rs.getTimestamp(8));
				auc.setCurrent_bid_price(rs.getInt(9));
				list_auc1.add(auc);
			}
			return list_auc1;
		}else return null;
	}
	
	public List<Ulti_auction> List_by_Auction(String keywords, boolean t_h, boolean order) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
		if(a.next()){
			s.execute(SQL_Command.update1);
			s.execute(SQL_Command.update2);
		
			if (keywords.equals("*"))
			{
			    if((t_h==true)&&(order==true)){
	                rs=s.executeQuery("select * from auction where  status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} ASC ");
	            }else if((t_h==true)&&(order==false)){
	                rs=s.executeQuery("select * from auction where  status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} DESC");
	            }else if((t_h==false)&&(order==true)){
	                rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and (name like '%"+keywords+"%' or description like '%"+keywords+"%')) order by current_bid_price ASC");
	            }else if((t_h==false)&&(order==false)){
	                rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and (name like '%"+keywords+"%' or description like '%"+keywords+"%')) order by current_bid_price DESC");;
	            }
			}
			else{
			if((t_h==true)&&(order==true)){
				rs=s.executeQuery("select * from auction where (name like '%"+keywords+"%' or description like '%"+keywords+"%') and status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} ASC ");
			}else if((t_h==true)&&(order==false)){
				rs=s.executeQuery("select * from auction where (name like '%"+keywords+"%' or description like '%"+keywords+"%') and status = true order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} DESC");
			}else if((t_h==false)&&(order==true)){
				rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and (name like '%"+keywords+"%' or description like '%"+keywords+"%')) order by current_bid_price ASC");
			}else if((t_h==false)&&(order==false)){
				rs=s.executeQuery("select * from auction where name IN (select name from auction where status = true and (name like '%"+keywords+"%' or description like '%"+keywords+"%')) order by current_bid_price DESC");;
			}}
			
			
			
			while (rs.next()) {
				Ulti_auction auc=new Ulti_auction();
				auc.setOwner(rs.getString(1));
				auc.setName(rs.getString(2));
				auc.setDescription(rs.getString(3));
				auc.setReserve_Price(rs.getInt(4));
				auc.setBuyout_Price(rs.getInt(5));
				auc.setStart_Price(rs.getInt(6));
				auc.setStatus(rs.getBoolean(7));
				auc.setEnd_date(rs.getTimestamp(8));
				auc.setCurrent_bid_price(rs.getInt(9));
				list_auc2.add(auc);
			}
			return list_auc2;
		}else return null;
	}
	
	public boolean Bid_for_Auction(Ulti_bid bid) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Bid); 
		
		if(!a.next()){
			s.execute("create table BID(bider varchar(30), bid_auction varchar(30), bid_description varchar(500), bid_price int)");
		}
		ResultSet b = s.executeQuery(SQL_Command.Create_Auction1); 
		if(b.next()){
		s.execute("update auction set status = false where {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} < 0");
		s.execute("update auction set current_bid_price = 0 where status = false and current_bid_price < reserve_price and current_bid_price <> buyout_price");
		double max = 0;
		
		rs=s.executeQuery("select max(bid_price) from Bid where bid_auction = '"+bid.getBid_auction()+"'");
		while(rs.next()){
			max = rs.getInt(1);
		}
		max = max * 1.05;
		rs=s.executeQuery("select start_price from auction where name = '"+bid.getBid_auction()+"'");
		int start = 0;
		while(rs.next()){
			start = rs.getInt(1);
		}
		
			if((bid.getBid_price())>max&&(bid.getBid_price()>start)){
				PreparedStatement ps = conn.prepareStatement(SQL_Command.Bid_for_Auction);
				ps.setString(1, bid.getBider());
				ps.setString(2, bid.getBid_auction());
				ps.setString(3, bid.getBid_description());
				ps.setInt(4, bid.getBid_price());
				s.execute("update auction set end_date = {fn TIMESTAMPADD(SQL_TSI_MINUTE,10,end_date)} where name = '"+bid.getBid_auction()+"' and {fn TIMESTAMPDIFF(SQL_TSI_MINUTE, CURRENT_TIMESTAMP, end_date)} < 1");
				ps.execute();
				s.execute("update auction set current_bid_price = "+bid.getBid_price()+" where name = '"+bid.getBid_auction()+"'");
				return true;
			}else return false;
		}else return false;
	}
	
	public List<Ulti_bid> Search_Bid_owner(String owner) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Bid); 
		if(a.next()){
			rs=s.executeQuery("select * from Bid where bider = '"+owner+"'");
			while (rs.next()) {
				Ulti_bid  bid=new Ulti_bid();
				bid.setBider(rs.getString(1));
				bid.setBid_auction(rs.getString(2));
				bid.setBid_description(rs.getString(3));
				bid.setBid_price(rs.getInt(4));
				list_bid1.add(bid);
			}
			return list_bid1;
		}else return null;
	}
	
	public List<Ulti_bid> Search_Bid_Auction(String auc) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
		
		if(a.next()){
			s.execute(SQL_Command.update1);
			s.execute(SQL_Command.update2);
			ResultSet b = s.executeQuery(SQL_Command.Create_Bid);
		if(b.next()){
			rs=s.executeQuery("select * from Bid where bid_auction = '"+auc+"'");
			while (rs.next()) {
				Ulti_bid  bid=new Ulti_bid();
				bid.setBider(rs.getString(1));
				bid.setBid_auction(rs.getString(2));
				bid.setBid_description(rs.getString(3));
				bid.setBid_price(rs.getInt(4));	
				list_bid1.add(bid);
			}
			return list_bid1;
		}else return null;
		}else return null;
	}
	
	public List<Ulti_auction> List_Finish_Auction() throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
		if(a.next()){
			s.execute(SQL_Command.update1);
			s.execute(SQL_Command.update2);
		
		rs=s.executeQuery("select * from auction where status = false");
		while (rs.next()) {
			Ulti_auction auc=new Ulti_auction();
			auc.setOwner(rs.getString(1));
			auc.setName(rs.getString(2));
			auc.setDescription(rs.getString(3));
			auc.setReserve_Price(rs.getInt(4));
			auc.setBuyout_Price(rs.getInt(5));
			auc.setStart_Price(rs.getInt(6));
			auc.setStatus(rs.getBoolean(7));
			auc.setEnd_date(rs.getTimestamp(8));
			auc.setCurrent_bid_price(rs.getInt(9));
			list_auc2.add(auc);
		}
		return list_auc2;
		}else return null;
	}
	

	
	public List<Ulti_auction> List_Winning_Auction(String username) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
		if(a.next()){
			s.execute(SQL_Command.update1);
			s.execute(SQL_Command.update2);
			ResultSet b = s.executeQuery(SQL_Command.Create_Bid);
			if(b.next()){
		rs=s.executeQuery("select * from auction where name IN (select bid_auction from bid INNER JOIN auction ON bid_auction = name and current_bid_price = bid_price where bider = '"+username+"' and auction.status = false)");
		while (rs.next()) {
			Ulti_auction auc=new Ulti_auction();
			auc.setOwner(rs.getString(1));
			auc.setName(rs.getString(2));
			auc.setDescription(rs.getString(3));
			auc.setReserve_Price(rs.getInt(4));
			auc.setBuyout_Price(rs.getInt(5));
			auc.setStart_Price(rs.getInt(6));
			auc.setStatus(rs.getBoolean(7));
			auc.setEnd_date(rs.getTimestamp(8));
			auc.setCurrent_bid_price(rs.getInt(9));
			list_auc2.add(auc);
		}
		return list_auc2;
		}else return null;
		}else return null;
	}
	
	public boolean setPrice(int price, String auction_name, String winner) throws Exception{
		conn = DriverManager.getConnection(protocol);
		s=conn.createStatement();
		ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
		
		if(a.next()){
			ResultSet b = s.executeQuery(SQL_Command.Create_Bid);
			if(b.next()){
		s.execute("update auction set status = false where {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} < 0");
		s.execute("update auction set current_bid_price = 0 where status = false and current_bid_price < reserve_price and current_bid_price <> buyout_price");
		s.execute("update auction set current_bid_price = "+price+" where name = '"+auction_name+"'");
		s.execute("update auction set status = false where name = '"+auction_name+"'");
		boolean result = s.execute("insert into bid values('"+winner+"','"+auction_name+"', 'buyout', "+price+")");
		return result;
			}else return false;
			}else return false;
	}
	
	public List<Ulti_auction> List_by_User_MY(String owner, boolean t_h,boolean order, String fin) throws Exception{
        conn = DriverManager.getConnection(protocol);
        s=conn.createStatement();
        ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
        if(a.next()){
            s.execute(SQL_Command.update1);
            s.execute(SQL_Command.update2);
            
            if((t_h==true)&&(order==true)){
                rs=s.executeQuery("select * from auction where owner = '"+owner+"' and status = "+fin+" order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} ASC ");
            }else if((t_h==true)&&(order==false)){
                rs=s.executeQuery("select * from auction where owner = '"+owner+"' and status = "+fin+" order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} DESC");
            }else if((t_h==false)&&(order==true)){
                rs=s.executeQuery("select * from auction where name IN (select name from auction where status = "+fin+" and owner = '"+owner+"') order by current_bid_price ASC");
            }else if((t_h==false)&&(order==false)){
                rs=s.executeQuery("select * from auction where name IN (select name from auction where status = "+fin+" and owner = '"+owner+"') order by current_bid_price DESC");;
            }
            
            
            
            while (rs.next()) {
                Ulti_auction auc=new Ulti_auction();
                auc.setOwner(rs.getString(1));
                auc.setName(rs.getString(2));
                auc.setDescription(rs.getString(3));
                auc.setReserve_Price(rs.getInt(4));
                auc.setBuyout_Price(rs.getInt(5));
                auc.setStart_Price(rs.getInt(6));
                auc.setStatus(rs.getBoolean(7));
                auc.setEnd_date(rs.getTimestamp(8));
                auc.setCurrent_bid_price(rs.getInt(9));
                list_auc1.add(auc);
            }
            return list_auc1;
        }else return null;
       
    }
	
	public List<Ulti_auction> List_by_User_MyBid(String bider, boolean t_h,boolean order) throws Exception{
        conn = DriverManager.getConnection(protocol);
        s=conn.createStatement();
        ResultSet a = s.executeQuery(SQL_Command.Create_Auction1); 
        if(a.next()){
            s.execute(SQL_Command.update1);
            s.execute(SQL_Command.update2);
            ResultSet b = s.executeQuery(SQL_Command.Create_Bid);
            if(b.next()){
           System.out.println(bider);
                if((t_h==true)&&(order==true)){
                    rs=s.executeQuery("select * from auction where  status = true and name IN( select name from BID where bider= '"+bider+"' and name= bid_auction) order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} ASC ");
                }else if((t_h==true)&&(order==false)){
                    rs=s.executeQuery("select * from auction where  status = true and name IN( select name from BID where bider= '"+bider+"' and name= bid_auction) order by {fn TIMESTAMPDIFF(SQL_TSI_SECOND, CURRENT_TIMESTAMP, end_date)} DESC ");
                }else if((t_h==false)&&(order==true)){
                    rs=s.executeQuery("select * from auction where name IN (select name from BID where bider= '"+bider+"' and name= bid_auction) order by current_bid_price ASC");
                }else if((t_h==false)&&(order==false)){
                    rs=s.executeQuery("select * from auction where name IN (select name from BID where bider= '"+bider+"' and name= bid_auction) order by current_bid_price DESC");;
                }

            
            while (rs.next()) {
                Ulti_auction auc=new Ulti_auction();
                auc.setOwner(rs.getString(1));
                auc.setName(rs.getString(2));
                auc.setDescription(rs.getString(3));
                auc.setReserve_Price(rs.getInt(4));
                auc.setBuyout_Price(rs.getInt(5));
                auc.setStart_Price(rs.getInt(6));
                auc.setStatus(rs.getBoolean(7));
                auc.setEnd_date(rs.getTimestamp(8));
                auc.setCurrent_bid_price(rs.getInt(9));
                list_auc1.add(auc);
            }
            return list_auc1;
        }else return null;
        }else return null;
    }
}

