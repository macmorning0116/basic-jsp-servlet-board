package com.grepp.day0819board.controller;

import com.grepp.day0819board.model.BoardDTO;
import com.grepp.day0819board.service.BoardService;
import com.grepp.day0819board.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/board.do")
public class BoardServlet extends HttpServlet {

    private BoardService boardService = BoardService.getInstance();
    private LoginService loginService = LoginService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        // 게시글 전체 조회
        if ("list".equals(action)) {
            try {
                List<BoardDTO> boardDTOList = boardService.getList();
                req.setAttribute("bList", boardDTOList);
                req.getRequestDispatcher("WEB-INF/views/list.jsp").forward(req, resp);
            } catch (SQLException e) {
                System.out.println("게시글 목록 불러오다 오류발생");
                throw new RuntimeException(e);
            }
            // 글 작성하기
        } else if ("writeForm".equals(action)) {
            HttpSession session = req.getSession();
            int result = boardService.checkWrite(session);
            if (result == 0) {
                req.setAttribute("msg", "로그인후 작성 가능합니다.");
                req.setAttribute("path", req.getContextPath());
                req.getRequestDispatcher("WEB-INF/views/alert.jsp").forward(req, resp);
            } else if (result == 1) {
                req.getRequestDispatcher("WEB-INF/views/writeForm.jsp").forward(req, resp);
            }
            // 게시글 상세조회
        } else if ("view".equals(action)) {
            int result = loginService.loginCheck(req);
            if (result == 1) {
                try {
                    BoardDTO boardDTO = boardService.findByBno(Integer.parseInt(req.getParameter("no")));
                    req.setAttribute("bbb", boardDTO);
                    req.getRequestDispatcher("/WEB-INF/views/view.jsp").forward(req, resp);
                } catch (SQLException e) {
                    System.out.println("게시글 상세조회 에러 발생");
                    throw new RuntimeException(e);
                }
            }else{
                req.setAttribute("msg", "로그인후 볼 수 있습니다.");
                req.setAttribute("path", req.getContextPath());
                req.getRequestDispatcher("WEB-INF/views/alert.jsp").forward(req, resp);
            }
            // 게시글 수정
        } else if ("update".equals(action)) {
            try {
                int result = boardService.writerCheck(req);
                if (result == 1) {
                    int no = Integer.parseInt(req.getParameter("no"));
                    req.setAttribute("no", no);
                    req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
                } else if (result == 0) {
                    req.setAttribute("msg", "본인이 작성한 글만 수정할 수 있습니다.");
                    req.setAttribute("path", req.getContextPath());
                    req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
                }
            } catch (SQLException e) {
                System.out.println("BoardServlet.doGet 에서 수정부분 오류 발생" );
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            // 게시글 수정
            if ("update".equals(action)) {
                int result = boardService.update(req);
                if (result == 1) {
                    req.setAttribute("msg", "게시글을 수정하였습니다 :)");
                    req.setAttribute("path", req.getContextPath() + "/board.do?action=list");
                    req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
                }else {
                    req.setAttribute("msg", "문제가 발생하였습니다. 관리자에게 문의 바랍니다.");
                    req.setAttribute("path", req.getContextPath());
                    req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
                }
            }
            // 게시글 작성
            else{
                int result = boardService.write(req);
                if (result == 1) {
                    req.setAttribute("msg", "글 작성이 완료되었습니다 :)");
                    req.setAttribute("path", req.getContextPath() + "/board.do?action=list");
                    req.getRequestDispatcher("/WEB-INF/views/alert.jsp").forward(req, resp);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
