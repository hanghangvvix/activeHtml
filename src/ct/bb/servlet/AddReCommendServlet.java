package ct.bb.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import ct.bb.model.BackModel;
import ct.bb.service.DbService;
import ct.bb.service.DbServiceImpl;

public class AddReCommendServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		DbService serviceImpl = new DbServiceImpl();
		BackModel backModel = new BackModel();

		String mapId = request.getParameter("mapId");
		String phone = request.getParameter("phone");

		if (checkString(mapId) && checkString(phone)) {
			backModel = serviceImpl.addRecommond(mapId, phone);
		} else {
			backModel.setResult("参数不能为空");
		}

		response.getWriter().write(backModel.toJsonString());
	}

	private boolean checkString(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		return true;
	}
}
