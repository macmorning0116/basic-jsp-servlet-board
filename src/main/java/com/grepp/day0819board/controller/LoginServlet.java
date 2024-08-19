package com.grepp.day0819board.controller;

import com.grepp.day0819board.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.awt.image.Kernel;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
    private LoginService loginService = LoginService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("login".equals(action)){
            HttpSession session = req.getSession();
            String loginId = (String) session.getAttribute("loginId");
            if (loginId == null) {
                req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req,resp);
            }else{
                req.setAttribute("msg", "이미 로그인 내역이 있습니다.");
                req.setAttribute("path", req.getContextPath());
                req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
            }
        } else if ("logout".equals(action)) {
            HttpSession session = req.getSession();
            session.invalidate();
            req.setAttribute("msg", "로그아웃 되었습니다");
            req.setAttribute("path", req.getContextPath());
            req.getRequestDispatcher("WEB-INF/views/alert.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        String userPw = req.getParameter("userPw");
        String rememberMe = req.getParameter("rememberMe");

        try {
            int result = loginService.login(userId, userPw);
            if (result == 1) { // 로그인 성공
                System.out.println("로그인 성공 응답");

                // 아이디 기억하기 체크박스 누르면 쿠키 추가
                if ("on".equals(rememberMe)) {
                    Cookie cookie = new Cookie("loginCheck", userId);
                    resp.addCookie(cookie);
                }else{ // 아이디 기억하기 체크박스 안누르면 쿠키 삭제
                    Cookie loginCheck = new Cookie("loginCheck", null);
                    loginCheck.setMaxAge(0);
                    resp.addCookie(loginCheck);
                }

                HttpSession session = req.getSession();
                session.setAttribute("loginId", userId);


                req.setAttribute("msg", "로그인이 완료되었습니다.");
                req.setAttribute("path", req.getContextPath());
                req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
            } else if (result == 2) {
                System.out.println("비밀번호 틀림 응답");
                req.setAttribute("msg", "비밀번호가 틀렸습니다 다시 시도해주세요");
                req.setAttribute("path", req.getContextPath()+"/login.do");
                req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
            } else if (result == 0) {
                System.out.println("존재하지 않는 아이디 응답");
                req.setAttribute("msg", "존재하지 않는 아이디 입니다. 다시 시도해주세요");
                req.setAttribute("path", req.getContextPath()+"/login.do?action=login");
                req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
