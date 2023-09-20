package com.example.spring_project.generics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponseDto<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private int totalPage;
    private Long totalContent;
}
