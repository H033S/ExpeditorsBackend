package expeditors.backend.domain;

import java.time.LocalDate;

public class Student {
   private int id;
   private String name;
   private LocalDate dob;
   private String email;

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
