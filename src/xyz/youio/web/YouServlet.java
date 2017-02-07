package xyz.youio.web;

import xyz.youio.dao.YouDao;
import xyz.youio.dao.YouPool;
import xyz.youio.util.YouJson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin@earthvip.xyz on 2017/1/24.
 * 服务层，与浏览器交互
 */
@WebServlet("/you.php")
public class YouServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //中文乱码
        response.setContentType("text/html;charset=UTF-8");
        //表单数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("sql")) {
            List<Object> list = new YouDao().execute(parameterMap.get("sql")[0]);
            String s = YouJson.toJson(list);
            response.getWriter().write(s);
        } else {
            System.out.println("sql:null");
            response.getWriter().write("false");
        }
    }
}
