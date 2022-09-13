package com.nguyentiennam.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "BUS_ROUTE")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Route implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BUS_ROUTE_SEQ")
    @SequenceGenerator(name = "BUS_ROUTE_SEQ", sequenceName = "BUS_ROUTE_SEQ", allocationSize = 1, initialValue = 1)
    Long id;

    @Column(nullable = false)
    Float distance;

    @Column(name = "NUMBER_BUS_STOP", nullable = false)
    Float numberBusStop;

}
