package ttl.mie.domain.track.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import ttl.mie.domain.track.Format;

public record TrackDTO(int trackId, int length, Format format, String title,
                       String album, String year,
                       String genre, BigDecimal price, List<ArtistDTO> artists) implements Comparable<TrackDTO> {

   public TrackDTO(int trackId, int length, Format format, String title, String album,
                   String year, String genre, BigDecimal price, List<ArtistDTO> artists) {
      this.trackId = trackId;
      this.length = length;
      this.format = format;
      this.title = title;
      this.album = album;
      this.year = year;
      this.genre = genre;
      this.price = price;
      this.artists = new ArrayList<>();
      if (artists != null) {
         this.artists.addAll(artists);
      }
   }

   public TrackDTO(int trackId, int length, String format, String title, String album,
                   String year, String genre, BigDecimal price, List<ArtistDTO> artists) {
      this(trackId, length, Format.valueOf(format.toUpperCase()), title, album, year, genre, price, artists);
   }

   public TrackDTO(int id, int length, String format, String title, String album,
                   String year, String genre, String price, List<ArtistDTO> artists) {
      this(id, length, Format.valueOf(format.toUpperCase()), title, album, year, genre, new BigDecimal("0"), artists);
   }

   public TrackDTO(int id, int length, String format, String title, String album,
                   String year, String genre, List<ArtistDTO> artists) {
      this(id, length, Format.valueOf(format.toUpperCase()), title, album, year, genre, new BigDecimal("0"), artists);
   }

   public void addArtist(ArtistDTO artist) {
      //Check to see if you have an artist with this name
      //already, if so, replace it.
      boolean found = false;
      for (int i = 0; i < artists.size(); i++) {
         if (artists.get(i) != null && artist.name() != null) {
            if (artists.get(i).name().equalsIgnoreCase(artist.name())) {
               artists.set(i, artist);
               found = true;
               break;
            }
         }
      }
      if (!found) {
         artists.add(artist);
      }
   }

   @Override
   public int compareTo(TrackDTO other) {
      return Integer.compare(this.length, other.length);
   }

   public static TrackDTO copyWithId(int id, TrackDTO ati) {
      var newAti = new TrackDTO(id,
            ati.length(),
            ati.format(),
            ati.title(),
            ati.album(),
            ati.year(),
            ati.genre(),
            ati.price(),
            ati.artists());

      return newAti;
   }

   public static TrackDTO copyWithPrice(String price, TrackDTO ati) {
      return copyWithPrice(Format.valueOf(price.toUpperCase()), ati);
   }

   public static TrackDTO copyWithPrice(Format format, TrackDTO ati) {
      var newAti = new TrackDTO(ati.trackId(),
            ati.length(),
            format,
            ati.title(),
            ati.album(),
            ati.year(),
            ati.genre(),
            ati.price,
            ati.artists());

      return newAti;
   }

   public static AudioTrackInfoBuilder builder() {
      return new AudioTrackInfoBuilder();
   }

   public static class AudioTrackInfoBuilder {
      private int length;
      private Format format;
      private String title;
      private String album;
      private String year;
      private String genre;
      private BigDecimal price;
      private List<ArtistDTO> artists = new ArrayList<>();

      public AudioTrackInfoBuilder length(int length) {
         this.length = length;
         return this;
      }

      public AudioTrackInfoBuilder format(Format format) {
         this.format = format;
         return this;
      }

      public AudioTrackInfoBuilder format(String format) {
         return format(Format.valueOf(format.toUpperCase()));
      }

      public AudioTrackInfoBuilder title(String title) {
         this.title = title;
         return this;
      }

      public AudioTrackInfoBuilder album(String album) {
         this.album = album;
         return this;
      }

      public AudioTrackInfoBuilder artist(String artistName) {
         var artist = new ArtistDTO(artistName);
         artists.add(artist);
         return this;
      }

      public AudioTrackInfoBuilder artist(ArtistDTO artist) {
         artists.add(artist);
         return this;
      }

      public AudioTrackInfoBuilder year(String year) {
         this.year = year;
         return this;
      }

      public AudioTrackInfoBuilder genre(String genre) {
         this.genre = genre;
         return this;
      }

      public AudioTrackInfoBuilder price(BigDecimal price) {
         this.price = price;
         return this;
      }

      public AudioTrackInfoBuilder price(String price) {
         return this.price(new BigDecimal(price));
      }


      public AudioTrackInfoBuilder price(double price) {
         return this.price(new BigDecimal(String.valueOf(price)));
      }

      public TrackDTO build() {
         return new TrackDTO(0, length, format, title, album, year, genre, price, artists);
      }
   }
}