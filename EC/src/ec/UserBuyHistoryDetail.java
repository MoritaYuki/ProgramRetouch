package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ItemDataBeans;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッション開始
		HttpSession session = request.getSession();

		/*文字化け対策*/
		request.setCharacterEncoding("UTF-8");

		// t_buyテーブルのidを受け取る
		int id = Integer.parseInt(request.getParameter("id"));

		// t_buyテーブルのIDと等しい購入IDの、購入詳細情報のデータを持つJavaBeansのリストを取得
		try {
			ArrayList<ItemDataBeans> buyDetailItemList = new BuyDetailDAO().getItemDataBeansListByid(id);
			request.setAttribute("buyDetailItemList", buyDetailItemList);

			// buyHistoryListの情報を引き継ぐ
			request.setAttribute("buyDate", request.getParameter("buyDate"));
			request.setAttribute("deliveryMethodName", request.getParameter("deliveryMethodName"));
			request.setAttribute("deliveryMethodPrice", request.getParameter("deliveryMethodPrice"));
			request.setAttribute("totalPrice", request.getParameter("totalPrice"));

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
		request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);
	}
}
