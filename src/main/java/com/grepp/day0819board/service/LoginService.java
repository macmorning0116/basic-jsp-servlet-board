package com.grepp.day0819board.service;


import com.grepp.day0819board.model.BoardRepository;
import com.grepp.day0819board.model.BoardRepositoryMysql;
import com.grepp.day0819board.model.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;

public class LoginService {
    private BoardRepository boardRepository = BoardRepositoryMysql.getInstance();

    private LoginService() {
    }

    private static LoginService instance = new LoginService();

    public static LoginService getInstance() {
        return instance;
    }

    // 로그인
    public int login(String id, String pw) throws SQLException {
        MemberDTO memberDTO = boardRepository.findMember(id);
        System.out.println("로그인 서비스 호출");
        if (memberDTO != null) {
            if (!pw.equals(memberDTO.getUser_pw())) {
                return 2; // 비밀번호 틀림
            }else{
                return 1; // 로그인 성공
            }
        }else{
            return 0; // 아이디조차 틀림
        }
    }


    /***
     * @param request
     * @return 로그인 되어 있으면 1, 아니면 0
     */
    // 로그인이 되어 있는지 확인
    public int loginCheck(HttpServletRequest request) {
        if (request.getSession().getAttribute("loginId") != null) {
            return 1;
        }else{
            return 0;
        }
    }
}
