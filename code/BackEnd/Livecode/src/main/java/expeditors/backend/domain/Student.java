package expeditors.backend.domain;

import java.time.LocalDate;

public class Student {

   public enum Status {
      FULL_TIME,
      PART_TIME,
      HIBERNATING
   }

   private int id;
   private String name;
   private LocalDate dob;
   private String email;
   
   private String otherNewValue;

   private Status status = Status.FULL_TIME;

   public Student(int id, String name, LocalDate dob, String email, Status status) {
//      init(id, name, dob, email, status);
      this.id = id;
      this.name = name;
      this.dob = dob;
      this.email = email;
      this.status = status;
   }

   public Student(int id, String name, LocalDate dob, String email) {
      this(id, name, dob, email, Status.FULL_TIME);

//      init(id, name, dob, email, Status.FULL_TIME);
//      this.id = id;
//      this.name = name;
//      this.dob = dob;
//      this.email = email;
   }

   public Student(int id, String name, LocalDate dob) {
      this(id, name, dob, null, Status.FULL_TIME);

//      init(id, name, dob, null, Status.FULL_TIME);
//      this.id = id;
//      this.name = name;
//      this.dob = dob;
   }

   private void init(int id, String name, LocalDate dob, String email, Status status) {
      this.id = id;
      this.name = name;
      this.dob = dob;
      this.email = email;
      this.status = status;
   }

   public Student() {

   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public LocalDate getDob() {
      return dob;
   }

   public void setDob(LocalDate dob) {
      this.dob = dob;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      if(!email.matches(".*@.*")) {
         throw new RuntimeException("Bad email value: " + email);
      }
      this.email = email;
   }
}
