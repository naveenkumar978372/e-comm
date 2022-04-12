package com.sqre.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BaseVO {
    String status;
    String message;

    public BaseVO() {
    }
}
