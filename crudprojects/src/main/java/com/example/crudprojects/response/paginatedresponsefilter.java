package com.example.crudprojects.response;

import com.example.crudprojects.entity.student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class paginatedresponsefilter {

    private List<student> content;
    private long totalcount;  // ✅ long to match Page.getTotalElements()
    private int page;
    private int pageSize;
}
