package ttl.mie.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ttl.mie.db.DBUtils;
import ttl.mie.domain.track.dto.TrackDTO;

@Component
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/application-postgres.properties")
@PropertySource("classpath:/trackinfoservice.properties")
public class JsonFileToDB {

   private final ObjectMapper objectMapper;
   private DBUtils dbUtils;

   public JsonFileToDB(DBUtils dbUtils, ObjectMapper objectMapper) {
      this.dbUtils = dbUtils;
      this.objectMapper = objectMapper;
   }

   public int addTracksToDB(File trackFile) throws Exception {

      List<TrackDTO> tracks = readTracksFromJsonFile(trackFile);

      dbUtils.addTracksToDB(tracks);

      return tracks.size();
   }
   public List<TrackDTO> readTracksFromJsonFile(File file) {
      try {
         TypeReference<List<TrackDTO>> tr = new TypeReference<>() {
         };
         var result = objectMapper.readValue(file, tr);
         return result;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public void main() throws Exception {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
      context.getEnvironment().setActiveProfiles("postgres");
      context.scan("ttl.mie");
      context.refresh();

      JsonFileToDB jftb = context.getBean("jsonFileToDB", JsonFileToDB.class);
      File trackFile = new File("/tmp/tracks.json");
      jftb.addTracksToDB(trackFile);
   }
}
