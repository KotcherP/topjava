package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("userId");

        log.debug("select user id {}",id);

        if(id != null){
            SecurityUtil.setAuthUserId(Integer.parseInt(id));
        }
        //request.getRequestDispatcher("/index.html").forward(request, response);
        response.sendRedirect("meals");
    }
}
