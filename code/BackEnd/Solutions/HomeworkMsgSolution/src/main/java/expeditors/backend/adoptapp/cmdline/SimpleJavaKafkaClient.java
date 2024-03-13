package expeditors.backend.adoptapp.cmdline;

import com.fasterxml.jackson.databind.ObjectMapper;
import expeditors.backend.adoptapp.domain.Adopter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class SimpleJavaKafkaClient {

   public static void main(String[] args) throws IOException {
      args = new String[]{"kafka_javaclient.properties"};
      if (args.length != 1) {
         System.out.println("Please provide the configuration file path as a command line argument");
         System.exit(1);
      }

      final Properties props = loadConfig(args[0]);
      final String topic = props.getProperty("ttl.kafka.adopter.topic");
      String key = "adopter";

      var frame = new JFrame("Simple Kafka Message Sender");
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);

      String line = "";

      ObjectMapper mapper = new ObjectMapper();

      try (final Producer<String, Object> producer = new KafkaProducer<>(props);
           var reader = new BufferedReader(new InputStreamReader(System.in));
      ) {
         while (true) {
            line = JOptionPane.showInputDialog(frame, "Enter Json Msg: ", line);

            if (line.equalsIgnoreCase("Quit")) {
               break;
            }

            String newLine = line;
            Adopter adopter = mapper.readValue(newLine, Adopter.class);
            producer.send(
                  new ProducerRecord<>(topic, key, adopter),
//                  new ProducerRecord<>(topic, key, newLine),
                  (event, ex) -> {
                     if (ex != null)
                        ex.printStackTrace();
                     else
                        System.out.printf("Produced event to topic %s, key %s, value = %s%n", topic, key, newLine);
                  });
         }
      }

   }

   public static Properties loadConfig(final String configFile) throws IOException {
      final Properties props = new Properties();
      props.load(SimpleJavaKafkaClient.class.getClassLoader().getResourceAsStream("kafka_javaclient.properties"));
      return props;
   }
}
