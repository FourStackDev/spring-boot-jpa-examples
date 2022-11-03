package org.manjunath.voterapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String doorNo;
    private String streetAddr1;
    private String streetAddr2;
    private String locality;
    private String talluk;
    private String district;
    private String state;
    private String country;
    private String zipcode;


    /**
     * Map the Address and Voter. Map them using the joinColumn 'voter_id'
     * and in Voter Entity mapped by must specify the variable of Voter i.e. voter.
     */
    @OneToOne
    @JoinColumn(name = "voter_id")
    private Voter voter;
}
