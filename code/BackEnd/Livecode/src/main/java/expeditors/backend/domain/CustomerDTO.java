package expeditors.backend.domain;

public record CustomerDTO(int id, String name) {
}

class RecordApp
{
   public static void main(String[] args) {
      CustomerDTO cdto = new CustomerDTO(4, "Shelley");

      cdto = new CustomerDTO(cdto.id(), "New Name");
   }
}
