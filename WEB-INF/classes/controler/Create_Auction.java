package controler;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import ulti.Ulti_auction;

/**
 * Servlet implementation class Create_Auction
 */
@WebServlet("/Create_Auction")
public class Create_Auction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Create_Auction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Calendar   calendar   =   Calendar.getInstance(); 
			HttpSession session = request.getSession();
			Date current = new Date();
			String end_day = request.getParameter("end_day");
			String end_time = request.getParameter("end_time");
			String end = end_day + " " + end_time;
			SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date end1=formater.parse(end);
			calendar.setTime(current);
			long timethis = calendar.getTimeInMillis();
			calendar.setTime(end1);
			long timeend = calendar.getTimeInMillis();
			long themin = (timeend   -   timethis)   /   (1000   *   60); 
			System.out.println(themin);
			if(themin>=10){
		
		
			boolean result = false;
			
			Model t = new Model();
			Model.loadDriver();
			Ulti_auction auction = new Ulti_auction();
			String owner = (String)session.getAttribute("current_user");
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			String reserve_price = request.getParameter("reserve_price");
			String buyout_price = request.getParameter("buyout_price");
			String start_price = request.getParameter("start_price");
			Date date = new Date();
			Timestamp start_date;
			//SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			start_date=Timestamp.valueOf(formater.format(date));
			boolean status = true;
			
			Timestamp end_date = Timestamp.valueOf(formater.format(end1));
			auction.setOwner(owner);
			auction.setName(name);
			auction.setDescription(description);
			auction.setReserve_Price(Integer.parseInt(reserve_price));
			auction.setBuyout_Price(Integer.parseInt(buyout_price));
			auction.setStart_Price(Integer.parseInt(start_price));
			auction.setStart_date(start_date);
			auction.setEnd_date(end_date);
			auction.setStatus(status);
			auction.setCurrent_bid_price(0);
			
			result = t.Create_Auction(auction);
		
		if(result==true){
		    List<Ulti_auction> cauction = t.List_by_User("*", true, true);
		    session.setAttribute("auctionlist", cauction);
		    
			session.setAttribute("my_choice", "4");
		}else {
			session.setAttribute("my_choice", "5");
		}
		 response.sendRedirect("index.jsp");
		
			}else if(themin<10){
				session.setAttribute("my_choice", "6");
				response.sendRedirect("index.jsp");
			}
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
