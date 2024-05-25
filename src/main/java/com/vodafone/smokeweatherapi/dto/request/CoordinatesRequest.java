package com.vodafone.smokeweatherapi.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoordinatesRequest {

    private double latitude;
    private double longitude;
}
