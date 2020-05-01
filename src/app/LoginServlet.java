package app;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession(true);
		String status = (String) session.getAttribute("login");
		String loginRequest = request.getParameter("loginRequest");
		PrintWriter pw = response.getWriter();
		if (status == null) {
			if (loginRequest != null && loginRequest.equals("manager")) {
				session.setAttribute("login", "manager");
				pw.append(new ObjectMapper().writeValueAsString("ログイン完了。"));
			} else if (loginRequest != null && loginRequest.equals("member")) {
				session.setAttribute("login", "member");
				pw.append(new ObjectMapper().writeValueAsString("ログイン完了。"));
			} else {
				pw.append(new ObjectMapper().writeValueAsString("ログインして下さい。"));
			}
		} else {
			if (loginRequest != null && loginRequest.equals("logout")) {
				session.removeAttribute("login");
				pw.append(new ObjectMapper().writeValueAsString("ログアウト完了。"));
			} else {
				pw.append(new ObjectMapper().writeValueAsString("ログイン済み"));
			}
		}
		System.out.println("attributes");
		for (Enumeration<String> attr = session.getAttributeNames(); attr.hasMoreElements();)
			System.out.println(attr.nextElement());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
