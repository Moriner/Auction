<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.* ,ulti.Ulti_auction,model.Model"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	    String search_choice = (String) session.getAttribute("search_choice");

	    boolean type, order;
	    boolean blank;

	    String tt, to;
	    Model t = new Model();
	    Model.loadDriver();
	    //t.dropTables();  

	    String filter = (String) session.getAttribute("filter");
	    String keyword = (String) session.getAttribute("keyword");

	    tt = (String) session.getAttribute("type");
	    to = (String) session.getAttribute("order");

	    if (tt != null && to != null) {
	        type = ((String) session.getAttribute("type")).equals("true") ? true : false;
	        order = ((String) session.getAttribute("order")).equals("true") ? true : false;
	    } else {
	        type = false;
	        order = false;
	    }

	    blank = (filter == null || tt == null || to == null) ? true : false;


	    
	    String myTool =(String) session.getAttribute("myTool");
	    if (myTool ==null)
	        session.setAttribute("myTool", "0");
	    
	    String cu = (String) session.getAttribute("current_user");

	    try {
	        if (blank) {
	            List<Ulti_auction> auction = t.List_by_User("*", true, true);
	        } else if (myTool.equals("1")) {
	            List<Ulti_auction> auction = t.List_by_User_MyBid(cu, type, order);
	            session.setAttribute("auctionlist", auction);
	        } else if (myTool.equals("2")) {
	            List<Ulti_auction> auction = t.List_by_User_MY(cu, type, order, "false");
	            session.setAttribute("auctionlist", auction);
	        } else if (myTool.equals("3")) {
	            List<Ulti_auction> auction = t.List_by_User_MY(cu, type, order, "true");
	            session.setAttribute("auctionlist", auction);
	        } else if (myTool.equals("4")) {
	            List<Ulti_auction> auction = t.List_Winning_Auction(cu);
	            session.setAttribute("auctionlist", auction);
	        } else {
	            if (filter.equals("1")) {
	                if (keyword == null)
	                    keyword = "*";
	                List<Ulti_auction> auction = t.List_by_Auction(keyword, type, order);
	                session.setAttribute("auctionlist", auction);
	            } else {
	                if (keyword == null)
	                    keyword = "*";
	                List<Ulti_auction> auction = t.List_by_User(keyword, type, order);
	                session.setAttribute("auctionlist", auction);
	            }
	        }

	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }


	%>

</body>
</html>