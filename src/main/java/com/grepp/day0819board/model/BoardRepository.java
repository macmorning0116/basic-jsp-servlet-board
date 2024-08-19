package com.grepp.day0819board.model;

import java.sql.SQLException;
import java.util.List;

public interface BoardRepository {
    int insert(BoardDTO board) throws SQLException;
    int update(BoardDTO board) throws SQLException;
    int delete(int bno) throws SQLException;
    List<BoardDTO> selectAll() throws SQLException;
    BoardDTO selectOne(int id) throws SQLException;
    MemberDTO findMember(String id) throws SQLException;
}
