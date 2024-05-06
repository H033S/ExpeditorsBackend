package ttl.larku.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ttl.larku.domain.PhoneNumber;
import ttl.larku.domain.Student;
import ttl.larku.jconfig.LarkUTestDataConfig;

import static java.lang.System.out;

public class JDBCClientDemo {

   LarkUTestDataConfig testDataConfig = new LarkUTestDataConfig();

   public static void main(String[] args) {
      JDBCClientDemo jtd = new JDBCClientDemo();
      String url = "jdbc:postgresql://localhost:5433/larku";
      String user = "larku";
      String pw = "larku";
      DriverManagerDataSource dataSource = new DriverManagerDataSource(url, user, pw);

      JdbcClient template = JdbcClient.create(dataSource);

//      jtd.getSimpleWithOneColumn(template);
//      jtd.getWholeObjectButOnlyStudent(template);
//      jtd.getWholeObjectWithRelationShipWrongWay(template);
//      jtd.getWholeObjectWithRelationShipWithResultSetExtractor(template);

      jtd.addStudentsWithPhoneNumbers(template);
   }

   public void getSimpleWithOneColumn(JdbcClient template) {
      String sql = "select name from Student where id = ?";

      String name = template.sql(sql)
            .param(1)
            .query(String.class).single();

      out.println("name: " + name);
   }

   public void getWholeObjectButOnlyStudent(JdbcClient template) {
      String sql = "select * from Student where id = ?";

      RowMapper<Student> rowMapper = (resultSet, rowNum) -> {
         int id = resultSet.getInt("id");
         String name = resultSet.getString("name");
         LocalDate dob = resultSet.getObject("dob", LocalDate.class);

         Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

         Student student = new Student(name, "", dob, status);
         return student;
      };

      Student student = template.sql(sql)
            .param(1)
            .query(rowMapper)
            .single();

      Student student2 = template.sql(sql)
            .param(1)
            .query(Student.class)
            .single();

      out.println("student: " + student);
      out.println("student2: " + student2);
   }

   public void getWholeObjectWithRelationShipWrongWay(JdbcClient template) {
      String sql = "select * from student s left join phonenumber p on s.id = p.student_id order by s.id";

      RowMapper<Student> rowMapper = (resultSet, rowNum) -> {
         int id = resultSet.getInt("id");
         String name = resultSet.getString("name");
         LocalDate dob = resultSet.getObject("dob", LocalDate.class);


         Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

         Student student = new Student(name, "", dob, status);
         String type = resultSet.getString("type");
         if (type != null) {
            String number = resultSet.getString("number");


            PhoneNumber pn = new PhoneNumber(PhoneNumber.Type.valueOf(type), number);
            student.addPhoneNumber(pn);
         }
         return student;
      };

      List<Student> students = template.sql(sql)
            .query(rowMapper)
            .list();

      students.forEach(out::println);
   }

   public void getWholeObjectWithRelationShipWithResultSetExtractor(JdbcClient template) {
      String sql = "select * from student s left join phonenumber p on s.id = p.student_id order by s.id";

      List<Student> result = new ArrayList<>();
      ResultSetExtractor<List<Student>> rowMapper = (resultSet) -> {
         int lastStudentId = -1;
         Student currStudent = null;

         while (resultSet.next()) {
            int id = resultSet.getInt("id");
            if (id != lastStudentId) {
               lastStudentId = id;

               String name = resultSet.getString("name");
               LocalDate dob = resultSet.getObject("dob", LocalDate.class);


               Student.Status status = Student.Status.valueOf(resultSet.getString("status"));

               currStudent = new Student(name, "", dob, status);
               currStudent.setId(id);
               result.add(currStudent);
            }
            String type = resultSet.getString("type");
            if (type != null) {
               String number = resultSet.getString("number");


               PhoneNumber pn = new PhoneNumber(PhoneNumber.Type.valueOf(type), number);
               currStudent.addPhoneNumber(pn);
            }
         }
         return result;
      };

      List<Student> students = template.sql(sql)
            .query(rowMapper);


      students.forEach(out::println);
   }


   public void addStudentsWithPhoneNumbers(JdbcClient template) {
      String insertStudentSql = "insert into student (name, dob, status) values(?, ?, ?)";

      String insertPhoneNumbersSql = "insert into phonenumber (type, number, student_id) values(?, ?, ?)";
      List<Integer> newStudentKeys = new ArrayList<>();

      List<Student> students = List.of(
            testDataConfig.student1(),
            testDataConfig.student2(),
            testDataConfig.student3(),
            testDataConfig.student4()
      );

      List<Object[]> studentParams = students.stream()
            .map(s -> new Object[]{s.getName() + "-JDBC", s.getDob(), s.getStatus().toString()})
            .toList();

      List<Object[]> phoneParams = students.stream()
            .flatMap(s -> s.getPhoneNumbers().stream()
                  .map(pn -> new Object[]{pn.getType().toString(), pn.getNumber(), s.getId()}))
            .toList();

      out.println("Running sql: " + insertStudentSql);
      var keyHolder = new GeneratedKeyHolder();
      for (Object[] params : studentParams) {
         template.sql(insertStudentSql)
               .params(params)
               .update(keyHolder);

//         out.println("Keys for new student: " + keyHolder.getKeys());
         newStudentKeys.add((int)keyHolder.getKeys().get("id"));
      }


      out.println("Running sql: " + insertPhoneNumbersSql);
      for (Object[] params : phoneParams) {
         template.sql(insertPhoneNumbersSql)
               .params(params)
               .update(keyHolder);
      }

      out.println("students added : " + newStudentKeys.size());
      newStudentKeys.forEach(out::println);
   }
}
