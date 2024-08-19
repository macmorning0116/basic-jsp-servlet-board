package com.grepp.day0819board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardRepositoryMysql implements BoardRepository{
    private BoardRepositoryMysql(){}

    private static BoardRepository instance = new BoardRepositoryMysql();

    public static BoardRepository getInstance() {
        return instance;
    }

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;



    @Override
    public int insert(BoardDTO board) throws SQLException {
        int result = 0;
        try {
            String sql = " INSERT INTO " +
                    " BOARD(TITLE,WRITER,CONTENT,REG_DATE) VALUES(?,?,?,NOW()) ";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, board.getTitle());
            ps.setString(2, board.getWriter());
            ps.setString(3, board.getContent());
            result = ps.executeUpdate();
        }catch (SQLException ex){
            System.out.println("insert error");
            throw ex;
        }finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public int update(BoardDTO board) throws SQLException {
        int result = 0;

        try {
            String sql = "UPDATE BOARD SET ";

            if(board.getTitle()!=null && board.getTitle().length()>0){ // 제목에 변경하고자 값이 확실히 있는지 체크해서
                sql += " TITLE='"+board.getTitle()+"', ";
            }
            if(board.getWriter()!=null && board.getWriter().length()>0){
                sql += " WRITER='"+board.getWriter()+"', ";
            }
            if(board.getContent()!=null && board.getContent().length()>0){
                sql += " CONTENT='"+board.getContent()+"', ";
            }
            sql = sql.substring(0, sql.length()-2);

            sql += " WHERE NO = "+board.getNo();
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            result = ps.executeUpdate();
        }catch (SQLException ex){
            System.out.println("update error");
            throw ex;
        }finally {
            DBUtil.close(ps, conn);
        }

        return result;
    }

    @Override
    public int delete(BoardDTO board) throws SQLException {
        return 0;
    }

    @Override
    public List<BoardDTO> selectAll() throws SQLException {
        List<BoardDTO> list = new ArrayList<BoardDTO>();
        try {
            String sql = " SELECT NO,TITLE,WRITER,CONTENT,READ_COUNT,REG_DATE FROM BOARD ";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(makeBoardDTO(rs));
            }
        }catch (SQLException ex){
            System.out.println("update error");
            throw ex;
        }finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    @Override
    public BoardDTO selectOne(int id) throws SQLException {
        BoardDTO board = null;

        try {
            String sql = " SELECT NO,TITLE,WRITER,CONTENT,READ_COUNT,REG_DATE FROM BOARD WHERE NO="+id;
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){ // 글번호 이상하면 없을수는 있음.
                board = makeBoardDTO(rs);
            }
        }catch (SQLException ex){
            System.out.println("update error");
            throw ex;
        }finally {
            DBUtil.close(rs, ps, conn);
        }

        return board;
    }

    @Override
    public MemberDTO findMember(String id) throws SQLException {
        try {
            MemberDTO memberDTO = new MemberDTO();
            String sql = "select user_id, user_pw from members where user_id= '" + id +"';";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                memberDTO.setUser_id(rs.getString("user_id"));
                memberDTO.setUser_pw(rs.getString("user_pw"));
                return memberDTO;
            }else{
                return null;
            }
        } catch (SQLException e) {
            System.out.println("findError");
            throw e;
        }finally {
            DBUtil.close();
        }
    }

    private BoardDTO makeBoardDTO(ResultSet rs) throws SQLException {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setNo(rs.getInt("NO"));
        boardDTO.setTitle(rs.getString("TITLE"));
        boardDTO.setWriter(rs.getString("WRITER"));
        boardDTO.setContent(rs.getString("CONTENT"));
        boardDTO.setReadCount(rs.getInt("READ_COUNT"));
        boardDTO.setRegDate(rs.getString("REG_DATE"));
        return boardDTO;
    }




}
