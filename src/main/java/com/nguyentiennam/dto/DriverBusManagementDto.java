package com.nguyentiennam.dto;

import com.nguyentiennam.model.Driver;
import com.nguyentiennam.model.Route;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;


import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverBusManagementDto {

    Driver driver;

    Map<Route, Integer> assignedBuses;

    float totalDistance;

    public DriverBusManagementDto(Driver driver, Map<Route, Integer> assignedBuses) {
        this.driver = driver;
        this.assignedBuses = assignedBuses;
    }

    public void setTotalDistance() {
        if (assignedBuses == null || assignedBuses.isEmpty()) {
            this.setTotalDistance(0);
        }
        AtomicReference<Float> totalDistance = new AtomicReference<>((float) 0);
        this.assignedBuses.forEach((route, round) -> totalDistance.updateAndGet(v -> v + route.getDistance() * round));
        this.totalDistance = totalDistance.get();

    }

}
