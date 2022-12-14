package org.manjunath.voterapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.manjunath.voterapi.codetype.GenderType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Entity
@Table(name = "voter")
public class Voter implements Serializable {

    private static final long serialVersionUID = -2064234665419580733L;

    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private GenderType gender;


    /**
     * OneToOne mapping for Voter and Address, and it mentions that the
     * mapping column is specified under Address Entity Object.
     * <p>
     * Also Exclude the toString() method, to avoid the circular dependency.
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "voter",
             fetch = FetchType.LAZY)
    @ToString.Exclude
    private Address address;
}
