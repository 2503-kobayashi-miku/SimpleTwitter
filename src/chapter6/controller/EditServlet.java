package chapter6.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import chapter6.beans.Message;
import chapter6.exception.NoRowsUpdatedRuntimeException;
import chapter6.logging.InitApplication;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/edit" })
public class EditServlet extends HttpServlet {

	/**
	 * ロガーインスタンスの生成
	 */
	Logger log = Logger.getLogger("twitter");

	/**
	 * デフォルトコンストラクタ
	 * アプリケーションの初期化を実施する。
	 */
	public EditServlet() {
		InitApplication application = InitApplication.getInstance();
		application.init();
	}


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		HttpSession session = request.getSession();

		String id = request.getParameter("id");
		Message message = null;

		if (!StringUtils.isBlank(id) && id.matches("^[0-9]*$")) {
			int intId = Integer.parseInt(id);
			message = new MessageService().select(intId);
		}

		if (message == null) {
			session.setAttribute("errorMessages", "不正なパラメータが入力されました");
			response.sendRedirect("./");
			return;
		}

		request.setAttribute("message",message);
		request.getRequestDispatcher("edit.jsp").forward(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		List<String> errorMessages = new ArrayList<String>();

		Message message = getMessage(request);

		if (isValid(message.getText(), errorMessages)) {
			try {
				new MessageService().update(message);
			} catch (NoRowsUpdatedRuntimeException e) {
				log.warning("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
				errorMessages.add("他の人によって更新されています。最新のデータを表示しました。データを確認してください。");
			}
		}

		if (errorMessages.size() != 0) {
			request.setAttribute("errorMessages", errorMessages);
			request.setAttribute("message", message);
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			return;
		}

		response.sendRedirect("./");
	}


	private Message getMessage(HttpServletRequest request) throws IOException, ServletException {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		Message message = new Message();
		message.setId(Integer.parseInt(request.getParameter("id")));
		message.setText(request.getParameter("text"));

		return message;
	}


	private boolean isValid(String text, List<String> errorMessages) {

		log.info(new Object(){}.getClass().getEnclosingClass().getName() +
		" : " + new Object(){}.getClass().getEnclosingMethod().getName());

		if (StringUtils.isBlank(text)) {
			errorMessages.add("メッセージを入力してください");
		} else if (140 < text.length()) {
			errorMessages.add("140文字以下で入力してください");
		}

		if (errorMessages.size() != 0) {
			return false;
		}
		return true;
	}
}