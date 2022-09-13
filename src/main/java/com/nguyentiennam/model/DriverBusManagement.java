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
@Table(name = "DRIVER_BUS_MANAGEMENT")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(DriverBusManagementPrimaryKey.class)
public class DriverBusManagement implements Serializable {

    @Id
    @Column(name = "DRIVER_ID")
    Long driverId;

    @Id
    @Column(name = "BUS_ROUTE_ID")
    Long routeId;

    @Column(name = "ROUND_NUMBER", nullable = false)
    Integer roundNumber;

}
