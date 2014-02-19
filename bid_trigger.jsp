<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.* ,ulti.Ulti_auction"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
session.setAttribute("bid", "true");
session.setAttribute("bid_fail", "false");
session.setAttribute("my_choice", "7");


Ulti_auction ptr=null;
//found out which one is current
List<Ulti_auction> c_auctions=(List<Ulti_auction>)session.getAttribute("auctionlist");

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
response.sendRedirect("index.jsp");%>
</body>
</html>