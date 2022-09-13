package com.nguyentiennam.dto;

import com.nguyentiennam.model.Driver;
import com.nguyentiennam.model.Route;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverBusManagementTempDto {
    Driver driver;
    Route route;
    Integer round;
}
