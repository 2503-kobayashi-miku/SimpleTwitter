package chapter6.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/setting", "/edit"})
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpSession session = httpRequest.getSession();

		if (session.getAttribute("loginUser") != null) {
			chain.doFilter(request, response); // サーブレットを実行
		} else {
			session.setAttribute("errorMessages", "ログインしてください");
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.sendRedirect("./login");
		}
	}


	@Override
	public void init(FilterConfig config) {
	}


	@Override
	public void destroy() {
	}
}
