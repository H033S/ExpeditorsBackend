package ttl.larku.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ttl.larku.domain.Course;

import static java.lang.System.out;

public class JdbcDemo {

   public static void main(String[] args) {

      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = "larku";

      try(Connection connection = DriverManager.getConnection(url, user, pw);) {
//         addCourses(connection);
         dumpAllCourses(connection);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public static void addCourses(Connection connection) {

      String sql = "insert into course (title, code, credits) values (?, ?, ?)";

      try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );) {
         ps.setString(1, "Intro to Bioloty");
         ps.setString(2, "Bio-101");
         ps.setFloat(3, 3.5F);

         int numRows = ps.executeUpdate();
         int newId = -1;

         try (ResultSet keys = ps.getGeneratedKeys();) {
            keys.next();
            newId = keys.getInt(1);
         }

         out.println("numRows: " + numRows + ", newId: " + newId);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public static void dumpAllCourses(Connection connection) {

      String sql = "select * from Course";

      try(PreparedStatement ps = connection.prepareStatement(sql);) {

         ResultSet rs = ps.executeQuery();
         List<Course> courses = new ArrayList<>();

         while(rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String code = rs.getString("code");
            float credits = rs.getFloat("credits");

            Course course = new Course(code, title, credits);
            course.setId(id);

            courses.add(course);
         }

         courses.forEach(out::println);

      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

}
