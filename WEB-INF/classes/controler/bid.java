package controler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;
import ulti.Ulti_bid;
import ulti.Ulti_auction;

/**
 * Servlet implementation class bid
 */
@WebServlet("/bid")
public class bid extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bid() {
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
		// TODO Auto-generated method stub
	    HttpSession session = request.getSession(false);
	    Ulti_auction cb =(Ulti_auction)session.getAttribute("CurrentBiddingAuction");
	    String Current_user =(String)session.getAttribute("current_user"); 
	    int price=0;
	    if (request.getParameter("bid_price") != null)
	    {
	        price =Integer.parseInt(request.getParameter("bid_price"));
	    }
	    /*int price =Integer.parseInt(request.getParameter("bid_price"));*/
	    
	    boolean result;
	    List<Ulti_auction> auction=null;
	    
	    Model t = new Model();
        Model.loadDriver();
	    
	    Ulti_bid bid = new Ulti_bid();
	    
	    bid.setBid_auction(cb.getName());
	    bid.setBid_description(cb.getDescription());
	    bid.setBid_price(price);
	    bid.setBider(Current_user);
	    bid.setStatus(true);
	    
	    try {
	        
	        result=t.Bid_for_Auction(bid);
	        if (result ==true){
	        session.setAttribute("my_choice", "8");
	            auction =t.List_by_Auction("*", false, false);
            
                session.setAttribute("auctionlist", auction);
	            
	        }else
	            session.setAttribute("my_choice", "9");
	        
	        session.setAttribute("bid", "false");
            response.sendRedirect("index.jsp");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    
	
	}

}
