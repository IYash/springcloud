package com.clnn;

import lombok.Data;

@Data
public class CNumber {
    private int num;

    public CNumber() {
    }

    public CNumber(int num) {
        this.num = num;
    }
}
