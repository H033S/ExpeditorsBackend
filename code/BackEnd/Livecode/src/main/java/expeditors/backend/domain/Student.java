package expeditors.backend.domain;

import java.time.LocalDate;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
   private Set<ScheduledClass> classes = ConcurrentHashMap.newKeySet();
   
   private Status status = Status.FULL_TIME;

   public Student(int id, String name, LocalDate dob,
                  String email, Status status, Set<ScheduledClass> classes) {
      this.id = id;
      this.name = name;
      this.dob = dob;
      this.email = email;
      this.status = status;
      if(classes != null) {
         classes.forEach(this::addClass);
      }
   }

   public Student(String name, LocalDate dob, String email, Status status) {
      this(0, name, dob, email, status, Set.of());
   }

   public Student(String name, LocalDate dob, String email) {
      this(0, name, dob, email, Status.FULL_TIME, Set.of());
   }



   public Student(int id, String name, LocalDate dob) {
      this(0, name, dob, null, Status.FULL_TIME, Set.of());
   }

   public Student(String name, LocalDate dob) {
      this(0, name, dob, null, Status.FULL_TIME, Set.of());
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

   public Set<ScheduledClass> getClasses() {
      return Set.copyOf(classes);
   }

   public void setPhoneNumbers(Set<ScheduledClass> classes) {
      this.classes.addAll(classes);
   }

   public void addClass(ScheduledClass scheduledClass) {
      this.classes.add(scheduledClass);
   }

   public void removeClass(ScheduledClass scheduledClass) {
      this.classes.remove(scheduledClass);
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   @Override
   public String toString() {
      return "Student{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", dob=" + dob +
            ", email='" + email + '\'' +
            ", status=" + status +
            '}';
   }
}
