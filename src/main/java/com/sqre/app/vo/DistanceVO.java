package com.sqre.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DistanceVO extends BaseVO {
    int distanceInKilometers;
}
