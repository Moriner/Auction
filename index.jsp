<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.* ,ulti.Ulti_auction,model.Model"%>
<%@ include file="daemon.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Auction</title>
<title>Project1</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>


<script>
	function onlyNonNegative(obj) {
		var inputChar = event.keyCode;
		if (inputChar == 190) {
			var index1 = obj.value.indexOf(".") + 1;
			var index2 = obj.value.indexOf(".", index1);
			while (index2 != -1) {

				obj.value = obj.value.substring(0, index2);
				index2 = obj.value.indexOf(".", index1);
			}
		}
		obj.value = obj.value.replace(/[^(\d|.)]/g, "");
	}
	
	function check_fields(){
        
        if((document.register_form1.user.value.length==0)||(document.register_form1.password.value.length==0)){
            alert("username/password CAN'T be NULL");
            return false;
        }
	}
	function check_fields1(){
		if((document.register_form2.user.value.length==0)||(document.register_form2.password.value.length==0)){
            alert("username/password CAN'T be NULL");
            return false;
        }
	}
	function check_fields2(){
		
        if((document.create_auction.name.value.length==0)||(document.create_auction.reserve_price.value.length==0)||(document.create_auction.buyout_price.value.length==0)||(document.create_auction.start_price.value.length==0)||(document.create_auction.end_day.value.length==0)||(document.create_auction.end_time.value.length==0)){
        	alert("Your input CAN'T be NULL, check again!");
            return false;
        }
		}
	function check_fields3(){
        if(document.bid_price.bid_price.value.length==0){
        	alert("Your bid price CAN'T be NULL, check again!");
            return false;
        }
        }
	
	function strDateTime(str) 
	{ 
	if(str=="") return false; 
	var r = str.match(/^(\d{4})(-)(\d{2})\2(\d{2})$/); 
	if(r==null)return true; 
	var d= new Date(r[1], r[3]-1, r[4]); 
	return !(d.getFullYear()==r[1] && (d.getMonth()+1)==r[3] && d.getDate()==r[4]); 
	} 
	
	function isTime(str)
	{
	var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/);
	if (a == null) {alert('Your format of time is incorrect!'); return false;}
	if (a[1]>24 || a[3]>60 || a[4]>60)
	{
	alert("Your format of time is incorrect!");
	return false
	}
	return true;
	}
	
</script>
<body>

	<%
		String current_user = (String) session.getAttribute("current_user");
		String register = (String) session.getAttribute("register");
		String register_fail = (String) session
				.getAttribute("register_fail");
        int item_table_idx=0;
		String reload = (String) session.getAttribute("reload");

		if (register == null) {
			session.setAttribute("register", "false");
			session.setAttribute("register_fail", "false");
		}
		register = (String) session.getAttribute("register");
		register_fail = (String) session.getAttribute("register_fail");

		//Model t = new Model();
		//Model.loadDriver();
		//t.dropTables();

		List<Ulti_auction> c_auctions = (List<Ulti_auction>) session
				.getAttribute("auctionlist");

		if (c_auctions == null) {
			c_auctions = new ArrayList();
			session.setAttribute("auctionlist", c_auctions);
		}

		c_auctions = (List<Ulti_auction>) session
				.getAttribute("auctionlist");
	%>



	<div id="container">

		<div id="header">
			<div id="picture">


				<h1>In The Clouds</h1>
			</div>
		</div>


		<div>
			<div id="main">
				<div id="searchcol_container">
					<div class="searchcol">
						<br>

						<form action="List_Auction" method="POST">

							<select name="filter" id="filter">
								<option value="1">Keyword</option>
								<option value="2">User</option>
							</select> <input id="filter-search-submit" name="filter-search-submit" />
							<select name="type" id="type">
								<option value="0">by Time</option>
								<option value="1">by Current Highest bid</option>
							</select> <select name="order" id="order">
								<option value="0">Ascending order</option>
								<option value="1">Descending order</option>
							</select> <input type="submit" value="refresh" />
						</form>

					</div>
					<div class="searchcol_bottom"></div>
				</div>
			</div>


			<div id="main">
				<div id="leftcol_container">
					<div class="leftcol">

						<%
							if ((current_user == null) && register.equals("false")
									&& register_fail.equals("false")) {
						%>
						<h2>Member Login</h2>
						<form action="Login" method="post">
							<p>&nbsp;</p>
							<strong>User Login</strong><input type="text" name="user" /> <strong>Password</strong><input
								type="password" name="password" />
							<p>&nbsp;</p>
							<font size="2">No Account Yet? <a href="reg_trigger.jsp">Register</a></font>
							<p>&nbsp;</p>
							<input type="submit" value="submit" />
						</form>

						<%
							} else if ((current_user != null)) {
						%>
						<h2>
							Hello
							<%=current_user%>
						</h2>
						<p>&nbsp;</p>
						<form action="Logout" method="post">
							<input name="logout" type="submit" value="logout" />
						</form>
						<%
							} else if ((current_user == null) && register.equals("true")
									&& register_fail.equals("false")) {
						%>
						<h2>Register</h2>
						<p>&nbsp;</p>
						<form action="Register" method="post" name="register_form1">
							<strong>User Login </strong><input type="text" name="user" /> <strong>Password
							</strong><input type="password" name="password" />
							<p>&nbsp;</p>
							<input onclick="return check_fields();" type="submit" value="submit" />
						</form>
						<%
							} else if ((current_user == null) && register.equals("true")
									&& register_fail.equals("true")) {
						%>
						<h2>Register</h2>
						<p>&nbsp;</p>
						<form action="Register" method="post" name="register_form2">
							<strong>User Login </strong><input type="text" name="user" /> <strong>Password
							</strong><input type="password" name="password" />
							<p>&nbsp;</p>
							<input onclick="return check_fields1();" type="submit" value="submit" /> <font color=red><b>User
									existed!</b></font>
						</form>

						<%
							session.removeAttribute("register");
								session.removeAttribute("register_fail");
							} else if ((current_user == null) && register.equals("false")
									&& register_fail.equals("true")) {
						%>
						<h2>Member Login</h2>
						<form action="Login" method="post">
							<p>&nbsp;</p>
							<strong>User Login</strong><input type="text" name="user" /> <strong>Password</strong><input
								type="password" name="password" />
							<p>&nbsp;</p>
							<font size="2">No Account Yet? <a href="reg_trigger.jsp">Register</a></font>
							<p>&nbsp;</p>
							<input type="submit" value="submit" />
						</form>
						<font color=red><b>Username or Password is wrong!</b></font>
						<%
							}
						%>

					</div>
					<div class="leftcol_bottom"></div>
					<%
						if ((current_user != null)) {
					%>
					<div class="leftcol">
						<h2>Auction Tools</h2>
						<ul>
							<li><a href="myPage_trigger.jsp">Create new auction</a></li>
							<li><a href="myPage_trigger_bid.jsp">My bids</a></li>
							<li><a href="myPage_trigger_fin.jsp">My auction list
									(finished)</a></li>
							<li><a href="myPage_trigger_unfin.jsp">My auction list
									(unfinished)</a></li>
							<li><a href="myPage_trigger_win.jsp">My winning auction</a></li>

						</ul>
						<p>&nbsp;</p>
						<a href="#">Contact Us</a>
					</div>
					<div class="leftcol_bottom"></div>

					<%
						}
					%>
				</div>


				<%
					String enable = (String) session.getAttribute("enable");
					String my_choice = (String) session.getAttribute("my_choice");
					if (enable == null) {
						session.setAttribute("enable", "false");
					}
					if (my_choice == null) {
						session.setAttribute("my_choice", "0");
					}
					enable = (String) session.getAttribute("enable");
					my_choice = (String) session.getAttribute("my_choice");
				%>


				<div id="maincol_container">
					<div class="maincol">

						<%
							if (enable.equals("false") && my_choice.equals("0")) {
						%>
						<h2>Auction List</h2>


						<%
							
									c_auctions = (List<Ulti_auction>) session
											.getAttribute("auctionlist");
								for (Ulti_auction ca : c_auctions) { item_table_idx++;
						%>
						<div class="maincol">
							<table>
								<tr>
									<td class="list_block_0">Name:</td>
									<td class="list_block"><%=ca.getName()%></td>
								</tr>
								<tr>
									<td class="list_block_0">Owner:</td>
									<td class="list_block"><%=ca.getOwner()%></td>
								</tr>
								<tr>
									<td class="list_block_0">Buyout_price:</td>
									<td class="list_block"><%=ca.getBuyout_price()%></td>
									<%
										if (!ca.getOwner().equals(current_user)
														&& (current_user != null)) {
									%>
									<td><form action="buyout_handler.jsp" method="post">
											<input type="hidden" name="auctionName"
												value=<%=ca.getName()%>> <input type="hidden"
												name="Owner" value=<%=ca.getOwner()%>> <input
												type="hidden" name="Start_price"
												value=<%=ca.getStart_price()%>> <input type="hidden"
												name="Description" value=<%=ca.getDescription()%>> 
												<input type="hidden"
												name="table_idx" value=<%=item_table_idx%>>
												<input class="input_small" type="submit" value="buy out" />
										</form></td>
									<%
										}
									%>
								</tr>
								<tr>
									<td class="list_block_0">Current_bid_price:</td>
									<td class="list_block"><%=ca.getCurrent_bid_price()%></td>
									<td class="list_block_0">End_date:</td>
									<td class="list_block"><%=ca.getEnd_date()%></td>
								</tr>


								<tr>
									<td class="list_block_0">Description:</td>
									<td class="list_block"><%=ca.getDescription()%></td>
									<%
										if (!ca.getOwner().equals(current_user)
														&& (current_user != null)) {
									%>
								
								<tr>

									<td><form action="bid_trigger.jsp" method="post">
											<input type="hidden" name="auctionName"
												value=<%=ca.getName()%>> <input type="hidden"
												name="Owner" value=<%=ca.getOwner()%>> <input
												type="hidden" name="Start_price"
												value=<%=ca.getStart_price()%>> <input type="hidden"
												name="Description" value=<%=ca.getDescription()%>> 
												<input type="hidden"
												name="table_idx" value=<%=item_table_idx%>>
												<input
												class="input_small" type="submit" value="bid" />
										</form></td>
								</tr>
								<%
									}
								%>



							</table>


						</div>
						<div class="maincol_bottom"></div>
						<%
							}
						%>

						<%
							} else if (enable.equals("true") && my_choice.equals("1")) {
								session.setAttribute("enable", "false");
								session.setAttribute("my_choice", "0");
						%>
						<h2>Create Auction</h2>
						<form action="Create_Auction" method="post" name="create_auction">
							<p>&nbsp;</p>
							<strong>Name</strong>
							<p>&nbsp;</p>
							<input class="main_input" type="text" name="name" />
							<p>&nbsp;</p>
							<strong>Description</strong>
							<p>&nbsp;</p>
							<TEXTAREA class="main_textarea" NAME="description" COLS=200
								ROWS=6></TEXTAREA>
							<p>&nbsp;</p>
							<strong>Reserve_Price</strong>
							<p>&nbsp;</p>
							<input class="main_input" name="reserve_price"
								onkeyup="onlyNonNegative(this)" />
							<p>&nbsp;</p>
							<strong>Buyout_Price</strong>
							<p>&nbsp;</p>
							<input class="main_input" name="buyout_price"
								onkeyup="onlyNonNegative(this)" />
							<p>&nbsp;</p>
							<strong>Start_Price</strong>
							<p>&nbsp;</p>
							<input class="main_input" name="start_price"
								onkeyup="onlyNonNegative(this)" />
							<p>&nbsp;</p>
							<strong>End_Day &nbsp;</strong> <input width="100" type="text"
								name="end_day" value="yyyy-MM-dd"  onblur="if(strDateTime(value))alert('Your format of date is incorrect!')"/>
							<p>&nbsp;</p>
							<strong>End_Time</strong> <input width="100" type="text"
								name="end_time" value="HH:mm:ss" onblur="isTime(value)"/>
							<p>&nbsp;</p>
							<input onclick=" return check_fields2();" type="submit" value="submit" />
						</form>
						<%
							} else if (my_choice.equals("4")) {
						%><font color=red><b>Create Success!</b></font>
						<form action="ok.jsp" method="post">
							<input type="submit" value="ok" />
						</form>
						<%
							} else if (my_choice.equals("5")) {
						%>
						<font color=red><b>Auction existed!</b></font>
						<form action="ok.jsp" method="post">
							<input type="submit" value="ok" />
						</form>
						<%
							} else if (my_choice.equals("6")) {
						%>
						<font color=red><b>The duration should be longer than
								10 minutes!</b></font>
						<form action="ok.jsp" method="post">
							<input type="submit" value="ok" />
						</form>
						<%
							} else if (my_choice.equals("7")) { //bid
								Ulti_auction cb = (Ulti_auction) session
										.getAttribute("CurrentBiddingAuction");
						%>
						<div class="maincol">
							<h2>Bidding Auction</h2>
							<table>
								<tr>
									<td class="list_block_0">Name:</td>
									<td class="list_block"><%=cb.getName()%></td>
								</tr>

								<tr>
									<td class="list_block_0">Owner:</td>
									<td class="list_block"><%=cb.getOwner()%></td>
								</tr>
								<tr>
									<td class="list_block_0">Description:</td>
									<td class="list_block"><%=cb.getDescription()%></td>
								</tr>
								<tr>
									<td class="list_block_0">buyout_price:</td>
									<td class="list_block"><%=cb.getBuyout_price()%></td>
								</tr>
								<tr>
									<td class="list_block_0">Current_bid_price:</td>
									<td class="list_block"><%=cb.getCurrent_bid_price()%></td>

								</tr>
							</table>
							<p>&nbsp;</p>
							<form action="bid" method="post" name ="bid_price">
								<input type="number" name="bid_price" /> <input
									class="input_small" onclick=" return check_fields3();" type="submit" value="place bid" />
							</form>

						</div>
						<div class="maincol_bottom"></div>
						<%
							} else if (my_choice.equals("8")) {
								//c_auctions = (List<Ulti_auction>) session.getAttribute("auctionlist");//reload list
						%>
						<font color=red><b>Bid accepted!</b></font>
						<form action="ok.jsp" method="post">
							<input type="submit" value="ok" />
						</form>

						<%
							} else if (my_choice.equals("9")) {
						%>
						<font color=red><b>Bids must be bigger than the start
								price and at least 5% more than the current bid for the auction!</b></font>
						<form action="ok.jsp" method="post">
							<input type="submit" value="ok" />
						</form>
						<%
							} else if (my_choice.equals("10")) {
						%>

						<%
							if (myTool.equals("1")) {
						%>
						<h2>My Bids</h2>
						<%
							} else if (myTool.equals("2")) {
						%>
						<h2>My Auction List (Finished)</h2>
						<%
							} else if (myTool.equals("3")) {
						%>
						<h2>My Auction List (Unfinished)</h2>
						<%
							} else if (myTool.equals("4")) {
						%>
						<h2>My Winning Auction</h2>
						<%
							}
						%>

						<form action="exit.jsp" method="post">
							<input type="submit" value="exit" />
						</form>
						<%
							if (c_auctions != null)
									for (Ulti_auction ca : c_auctions) {
						%>
						<div class="maincol">
							<table>
								<tr>
									<td class="list_block_0">Name:</td>
									<td class="list_block"><%=ca.getName()%></td>
								</tr>
								<tr>
									<td class="list_block_0">Owner:</td>
									<td class="list_block"><%=ca.getOwner()%></td>
								</tr>
								<tr>
									<td class="list_block_0">Buyout_price:</td>
									<td class="list_block"><%=ca.getBuyout_price()%></td>

								</tr>
								<tr>
									<td class="list_block_0">Current_bid_price:</td>
									<td class="list_block"><%=ca.getCurrent_bid_price()%></td>
									<td class="list_block_0">End_date:</td>
									<td class="list_block"><%=ca.getEnd_date()%></td>
								</tr>


								<tr>
									<td class="list_block_0">Description:</td>
									<td class="list_block"><%=ca.getDescription()%></td>
								<tr>
							</table>


						</div>
						<div class="maincol_bottom"></div>
						<%
							}
						%>

						<%
							} else if (my_choice.equals("11")) {
						%>
						<font color=red><b>Buy_out success!</b></font>
						<form action="ok.jsp" method="post">
							<input type="submit" value="ok" />
						</form>
						<%
							} else if (my_choice.equals("12")) {
						%>
						<font color=red><b>Buy_out fail!</b></font>
						<form action="ok.jsp" method="post">
							<input type="submit" value="ok" />
						</form>
						<%
							}
						%>
					</div>



				</div>



				<div class="clear"></div>

				<div id="footer">
					<h3>
						<a href="http://www.bryantsmith.com">web design florida</a>
					</h3>
				</div>



			</div>
		</div>
	</div>
</body>
</html>