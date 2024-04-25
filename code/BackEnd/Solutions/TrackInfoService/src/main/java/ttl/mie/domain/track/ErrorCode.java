package ttl.mie.domain.track;

public enum ErrorCode
{
   ERROR(10),
   WARNING(14),
   INFO(22);

   private int codeValue;

   ErrorCode(int codeValue) {
      this.codeValue = codeValue;
   }

   public int getCodeValue() {
      return codeValue;
   }
}

class TryErrorCode
{
   public static void main(String[] args) {
      ErrorCode ec = ErrorCode.INFO;

      int codeValue = ec.getCodeValue();


   }
}
