package ttl.mie.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.extractor.AudioInfoExtractor;
import ttl.mie.extractor.DiscogRestHandler;

/**
 * Application to extract metadata from audio files.
 * <p>
 * It first uses the AudioInfoExtractor to do read the metadata,
 * and then uses the DiscogRestHandler to embellish the artist
 * information with data from Discogs.
 * <p>
 * The result is then put into an output json file.
 */
public class SimpleExtractorApp {
   //   private static String discoggsSearchUrl = "https://api.discogs.com/database/search";
//   private static String discoggsArtisUrl = "https://api.discogs.com/artists/";
//   private static String discoggsMastersUrl = "https://api.discogs.com/masters/";
//   private static String authStr = "Discogs token=NvPVPQhSjNXovODgtIWHvCozAYJPsuDAqfcUtesf";
//   private static RestClient restClient = RestClient.create();
   private static ObjectMapper mapper = new ObjectMapper();

   public void main(String[] args) throws IOException {
      File input = new File("/sidecar/Music/Music");
      File output = new File("/tmp/tracks.json");
      SimpleExtractorApp sae = new SimpleExtractorApp();
      AudioInfoExtractor atst = new AudioInfoExtractor();
      List<TrackDTO> result =
            getAudioTracks(input, 100, atst);

      String authStr = System.getenv("DT");

      DiscogRestHandler drh = new DiscogRestHandler(authStr);
      drh.getArtistInfoFromDiscogs(result);

      mapper.writerWithDefaultPrettyPrinter().writeValue(output, result);

//      result.forEach(ati -> System.out.println("processed Track: " + ati));
   }

   public List<TrackDTO> getAudioTracks(File root, AudioInfoExtractor atst) throws IOException {
      return getAudioTracks(root, Integer.MAX_VALUE, atst);
   }

   public List<TrackDTO> getAudioTracks(File root, int limit, AudioInfoExtractor atst) throws IOException {

      long start = System.currentTimeMillis();

      List<TrackDTO> r1 = atst.searchTrackStream(root, limit, (af) -> {
         Tag tag = af.getTag();
         int length = af.getAudioHeader().getTrackLength();
         var vetoSet = Set.of("Ike Turner");
         var artist = tag.getFirst(FieldKey.ARTIST);
         var artists = tag.getFirst(FieldKey.ARTISTS);
         var result = Strings.isNotBlank(tag.getFirst(FieldKey.TITLE))
               && !vetoSet.contains(artist)
               && length > 6;
         return result;
      });


      long end = System.currentTimeMillis();

      System.out.println(STR."Single Threaded Search took \{end - start} ms");
      System.out.println(STR."Total Matches: \{r1.size()}, Matching tracks: ");

      return r1;
   }
}


//   public static void getArtistsForTracks(List<AudioTrackInfo> tracks) {
//
//      //'https://api.discogs.com//database/search?q=Miles%20Davis&type=artist'
//      // -H "Authorization: Discogs token=NvPVPQhSjNXovODgtIWHvCozAYJPsuDAqfcUtesf"
//      // --user-agent "FooBarApp/3.0"
//      // -H "Authorization: Discogs token=NvPVPQhSjNXovODgtIWHvCozAYJPsuDAqfcUtesf"
//      // --user-agent "FooBarApp/3.0"
//      var limit = 100;
//      var alreadySeenArtists = new HashMap<String, ArtistDTO>();
//      ResponseEntity<?> lastResponse = null;
//      for (int i = 0; i < limit; i++) {
//         var t = tracks.get(i);
//         if (!t.artists().isEmpty() &&
//               !t.artists().getFirst().name().isBlank()) {
//            var artistName = t.artists().getFirst().name();
//            if (alreadySeenArtists.containsKey(artistName)) {
//               var adto = alreadySeenArtists.get(artistName);
//               t.artists().set(0, adto);
//               System.err.println(STR."Already seen for: \{artistName}: \{adto}");
//               continue;
//            }
//            var uri = discoggsSearchUrl + "?q=" + t.artists().getFirst().name() + "&release_title=" + t.album();
//            System.out.println("Calling: " + uri);
//            var response = restClient
//                  .get()
//                  .uri(uri)
//                  .header("Authorization", authStr)
//                  .header("User-Agent", "TheThirdLane/1.0 +https://thethirdlane.com")
//                  .retrieve()
//                  .toEntity(String.class);
//            lastResponse = response;
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//               try {
//                  var jsonResult = response.getBody();
//                  JsonNode nameNode = mapper.readTree(jsonResult);
//                  var arr = nameNode.get("results");
//                  if (arr.size() > 0) {
//                     var releaseId = nameNode.get("results").get(0).get("id").asText();
//                     uri = discoggsMastersUrl + releaseId;
//                     System.out.println("Calling For Release: " + uri);
//                     var releaseResponse = restClient
//                           .get()
//                           .uri(uri)
//                           .header("Authorization", authStr)
//                           .header("User-Agent", "TheThirdLane/1.0 +https://thethirdlane.com")
//                           .retrieve()
//                           .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
//                              //Do Nothing because we handle it below
//                           })
//                           .toEntity(String.class);
//
//                     if (releaseResponse.getStatusCode() == HttpStatus.OK) {
//                        var releaseJson = releaseResponse.getBody();
//                        var releaseNode = mapper.readTree(releaseJson);
//                        var artistId = releaseNode.get("artists").get(0).get("id").asText();
//                        uri = discoggsArtisUrl + artistId;
//                        System.out.println("Calling For Artist: " + uri);
//                        var artistResponse = restClient
//                              .get()
//                              .uri(uri)
//                              .header("Authorization", authStr)
//                              .header("User-Agent", "TheThirdLane/1.0 +https://thethirdlane.com")
//                              .retrieve()
//                              .toEntity(ArtistDTO.class);
//
//                        lastResponse = artistResponse;
//                        if (artistResponse.getStatusCode() == HttpStatus.OK) {
//                           var artistDTO = artistResponse.getBody();
//                           //Add the artist to our artists
//                           var oldName = t.artists().getFirst().name();
//                           t.artists().set(0, artistDTO);
//                           alreadySeenArtists.put(oldName, artistDTO);
//                           System.out.println("Artist response: " + artistDTO);
//                           //return Optional.of(artistDTO);
//                        }
//                     }
//                  }
//               } catch (JsonProcessingException e) {
//                  e.printStackTrace();
//               }
//            } else {
//               System.err.println("Status NOT OK: " + lastResponse.getStatusCode());
//            }
////               return Optional.<ArtistDTO>empty();
//         }
//
//      }
//   }
