package DataBaseCURDOperationServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DataBaseAccessingDAO.UserDAO;
import DataBaseAccessingModel.User;

/**
 *  implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        this.userDAO = new UserDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		
		switch(action)
		{
		case "/new":
			showNewForm(request, response);
			break;
		case "/delete":
			deleteUser(request, response);
			break;
		case "/insert":
			try {
				insertUser(request, response);
			} catch (IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/edit":
			showEditForm(request, response);
			break;
		case "/update":
			updateuser(request, response);
			break;
		default:
			listUser(request, response);
			break;
		}
		
	}
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<User> listUser = userDAO.selectAllUser();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}
	private void updateuser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String roll = request.getParameter("roll");
		String address = request.getParameter("address");
		
		User newUser = new User(id,name,roll,address);
		userDAO.updateUser(newUser);
		response.sendRedirect("list");
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
	{
	
		String name = request.getParameter("name");
		String roll = request.getParameter("roll");
		String address = request.getParameter("address");
		
		User newUser = new User(name,roll,address);
		userDAO.insertUser(newUser);
		response.sendRedirect("list");
		
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String roll = request.getParameter("roll");
		User existingUser = userDAO.selectUser(roll);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user",existingUser);
		dispatcher.forward(request, response);
		
	}
	
	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int id = Integer.parseInt(request.getParameter("id"));
		userDAO.deleteUser(id);
		response.sendRedirect("list");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.doGet(request, response);
	}

}
