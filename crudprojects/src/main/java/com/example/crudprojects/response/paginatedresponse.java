package com.example.crudprojects.response;

import com.example.crudprojects.entity.student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class paginatedresponse<T> {
    private List<T> content;

    private int totalcount;
    private int page;
    private int pageSize;

}
