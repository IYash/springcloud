package com.clnn.hsqldb.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Pkonly implements Serializable {

    private int id;

    private int seqNum;
}
