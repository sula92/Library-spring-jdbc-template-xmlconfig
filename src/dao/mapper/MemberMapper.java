package dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Member;
import org.springframework.jdbc.core.RowMapper;

public class MemberMapper implements RowMapper<Member> {
   public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
      Member member = new Member();
      member.setId(rs.getString(1));
      member.setName(rs.getString(2));
      member.setContact(rs.getString(3));
      member.setAddress(rs.getString(4));
      return member;
   }
}