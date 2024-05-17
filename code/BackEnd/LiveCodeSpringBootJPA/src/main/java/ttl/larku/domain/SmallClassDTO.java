package ttl.larku.domain;

import java.time.LocalDate;

public record SmallClassDTO(LocalDate startDate, LocalDate endDate, String courseCode) {
}
