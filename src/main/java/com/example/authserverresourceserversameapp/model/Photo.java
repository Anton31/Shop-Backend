package com.example.authserverresourceserversameapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Photo {
    @Id
    @SequenceGenerator(name = "imageGen", sequenceName = "imageSeq", initialValue = 20)
    @GeneratedValue(generator = "imageGen")
    private Long id;
    private String name;
    private String url;
    @ManyToOne
    @JsonIgnore
    private Product product;
}
