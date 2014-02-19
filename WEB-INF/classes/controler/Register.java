package controler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Model;

import ulti.Ulti_user;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
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
			 boolean result = false;
		 
	    
	    String name = request.getParameter("user");
	    HttpSession session = request.getSession(); 
	    if (name != null) {
	        String user = name;
	        String password = request.getParameter("password");
	        Model m = new Model();
	        Model.loadDriver();
	        Ulti_user userlist = new Ulti_user(user, password);
				result = m.Create_User(userlist);
	        if(result==true){
	            session.setAttribute("register_fail", "false");
	            session.setAttribute("register", "false");
	            session.setAttribute("current_user", user);
	        }else{
	        	session.setAttribute("register_fail", "true");
                session.setAttribute("register", "true");
                
	        }
	    }
	    
	    response.sendRedirect("index.jsp");
		 } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
