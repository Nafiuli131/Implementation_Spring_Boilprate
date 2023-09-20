package com.example.spring_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookDto {
    private String bookName;
    private Integer bookPrice;
    private String userName;
    private String email;
}
