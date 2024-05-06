package ttl.larku.db;

import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ttl.larku.domain.Course;
import ttl.larku.jconfig.LarkUTestDataConfig;

import static java.lang.System.out;

public class JDBCTemplateDemo {

   LarkUTestDataConfig testDataConfig = new LarkUTestDataConfig();

   public static void main(String[] args) {
      JDBCTemplateDemo jtd = new JDBCTemplateDemo();
      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = "larku";
      DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);

      JdbcTemplate template = new JdbcTemplate(dataSource);

//      jtd.addScheduledClasses(template);

      jtd.dumpAllCourses(template);
   }


   public void addScheduledClasses(JdbcTemplate template) {
      String insertClassql = "insert into scheduledclass (startdate, enddate, course_id) " +
            "values(?, ?, ?)";

      int numRows = 0;
      numRows += template.update(insertClassql, LocalDate.parse("2024-10-10"),
            LocalDate.parse("2025-10-10"), 1);


//      numRows += template.update(insertClassql,  "2025-10-10", "2026-10-10", 3);

      out.println("rows: " + numRows);
   }

   public void dumpAllCourses(JdbcTemplate template) {
      String sql = "select * from course";

      RowMapper<Course> rowMapper = (rs, rowNum) -> {
         int id = rs.getInt("id");
         String title = rs.getString("title");
         String code = rs.getString("code");
         float credits = rs.getFloat("credits");

         Course course = new Course(code, title, credits);
         course.setId(id);
         return course;
      };

      List<Course> courses = template.query(sql, rowMapper);
      courses.forEach(out::println);
   }
}
