<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.* ,ulti.Ulti_auction,ulti.Ulti_bid,model.Model"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%


boolean result;
Ulti_auction ptr=null;
//found out which one is current
List<Ulti_auction> c_auctions=(List<Ulti_auction>)session.getAttribute("auctionlist");
String current_user = (String)session.getAttribute("current_user");

for(Ulti_auction c:c_auctions)
{
  if ( (c.getName().equals(request.getParameter("auctionName"))) &&
          (c.getOwner().equals(request.getParameter("Owner"))) &&
          c.getStart_price()==Double.parseDouble((request.getParameter("Start_price"))) &&
          (c.getDescription().equals(request.getParameter("Description"))) )
  {
      ptr=c;
      break;
  }
}
//set pointer to the one gonna bid
if (ptr == null)
{
    int idx = Integer.parseInt(request.getParameter("table_idx"));
    ptr = c_auctions.get(idx);
}
session.setAttribute("CurrentBiddingAuction", ptr);
/*
if (ptr !=null)
  session.setAttribute("CurrentBiddingAuction", ptr);
*/
Model t = new Model();
Model.loadDriver();


//==========================
//no bid table error, so add a 0 dollar bid to make bid table exist
	    Ulti_bid bid = new Ulti_bid();
	    
	    bid.setBid_auction(ptr.getName());
	    bid.setBid_description(ptr.getDescription());
	    bid.setBid_price(0);
	    bid.setBider(current_user);
	    bid.setStatus(true);
	    try{
	    t.Bid_for_Auction(bid);
	    }catch(Exception e){}
	    
//==========================
result=t.setPrice(ptr.getBuyout_price(), ptr.getName(), current_user);
if (result==true)
    session.setAttribute("my_choice", "12");
else
    session.setAttribute("my_choice", "11");

response.sendRedirect("index.jsp");%>
</body>
</html>