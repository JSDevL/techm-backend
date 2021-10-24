package com.hackerearth.fullstack.backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentRequest {
    String name;
    String roll;
}
