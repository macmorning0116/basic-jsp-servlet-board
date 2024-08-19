package com.grepp.day0819board.service;

import com.grepp.day0819board.model.BoardDTO;
import com.grepp.day0819board.model.BoardRepository;
import com.grepp.day0819board.model.BoardRepositoryMysql;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.sql.SQLException;
import java.util.List;

public class BoardService {
    private BoardRepository boardRepository = BoardRepositoryMysql.getInstance();
    private BoardService(){}

    private static BoardService instance = new BoardService();

    public static BoardService getInstance() {
        return instance;
    }

    // 게시글 목록 조회
    public List<BoardDTO> getList() throws SQLException {
        return boardRepository.selectAll();
    }

    // 글 쓰기 가능여부
    public int checkWrite(HttpSession session) {
        System.out.println("BoardService.checkWrite");
        if (session.getAttribute("loginId") == null) {
            return 0;
        }else{
            return 1;
        }
    }


    // 글 작성
    public int write(HttpServletRequest request) throws SQLException {
        try {
            BoardDTO boardDTO = new BoardDTO();
            HttpSession session = request.getSession();
            boardDTO.setContent(request.getParameter("cccc"));
            boardDTO.setTitle(request.getParameter("tttt"));
            boardDTO.setWriter((String) session.getAttribute("loginId"));

            boardRepository.insert(boardDTO);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("글 작성 중 오류 발생");
            return 0;
        }

    }

    // 글 하나 조회
    public BoardDTO findByBno(int no) throws SQLException {
        BoardDTO boardDTO = boardRepository.selectOne(no);
        return boardDTO;
    }

    // 게시글이 로그인한 사용자가 쓴 것인지 확인
    public int writerCheck(HttpServletRequest request) throws SQLException {
        BoardDTO boardDTO = findByBno(Integer.parseInt(request.getParameter("no")));
        // 본인의 게시글이면 1, 아니면 0
        if (boardDTO.getWriter().equals(request.getSession().getAttribute("loginId"))) {
            System.out.println("BoardService.writerCheck 본인이 쓸 글 맞아서 1반환");
            return 1;
        }else{
            System.out.println("BoardService.writerCheck 본인이 쓸 글 아니여서 0반환");
            return 0;
        }
    }

    // 게시글 수정(본인것만 가능)
    public int update(HttpServletRequest req) {
        try {
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setNo(Integer.parseInt(req.getParameter("no")));
            boardDTO.setContent(req.getParameter("cccc"));
            boardDTO.setTitle(req.getParameter("tttt"));

            boardRepository.update(boardDTO);
            return 1;
        } catch (SQLException e) {
            System.out.println("게시글 수정중 오류 발생");
            throw new RuntimeException(e);
        }

    }

    // 게시글 삭제
    public int deleteBoard(HttpServletRequest request) throws SQLException {
        int bno = Integer.parseInt(request.getParameter("no"));
        int result = boardRepository.delete(bno);
        return result;
    }
}
