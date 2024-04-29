package org.ttl.liburicreator;

import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class LibUriCreator {

   public URI getURI(int id) {
      URI newResource = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();

      return newResource;
   }

}
