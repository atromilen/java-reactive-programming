package cl.atromilen.reactiveprogramming.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "director")
public class Director {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer directorId;
    private @NonNull String name;
}
