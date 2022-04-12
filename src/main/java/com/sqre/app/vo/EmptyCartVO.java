package com.sqre.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmptyCartVO extends BaseVO {
    String action;

    public EmptyCartVO(String action) {
        this.action = action;
    }
}
