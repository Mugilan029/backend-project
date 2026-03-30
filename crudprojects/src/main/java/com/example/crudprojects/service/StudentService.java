package com.example.crudprojects.service;

import com.example.crudprojects.entity.student;
import com.example.crudprojects.repository.StudentRepo;
import com.example.crudprojects.response.paginatedresponse;
import com.example.crudprojects.response.paginatedresponsefilter;
import com.example.crudprojects.spec.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import utlis.ts.FilterQueryUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.internal.util.config.ConfigurationHelper.extractValue;

@Service
@RequiredArgsConstructor
public class
 StudentService {

    @Autowired
    final  StudentRepo studentRepo;

    public List<student> getAllStudents() {

        return studentRepo.findAll();
    }


    public student addStudent(student student) {

        student stud = studentRepo.save(student);
        return stud;
    }


/*    public student getbyid( int id)

    {

        return studentRepo.findById(id).orElse(new student());
    }*/

    public void update(student student) {
        studentRepo.save(student);
    }


    public void deletebyid(int id) {

        studentRepo.deleteById(id);
    }


    public void deletalldata() {

        studentRepo.deleteAll();
    }


    public student getById(int id) {

        return studentRepo.findById(id).orElse(new student());
    }


    public List<student> getbytech(String technology) {

        return studentRepo.findBytechnology(technology);
    }


    public List<student> getfilterdata(String gender, String technology) {

        return studentRepo.findByGenderAndTechnology(gender, technology);
    }


    public student getbyid(int id) {

        return studentRepo.findById(id).orElse(null);
    }


    // paginatimwithnormalsearchfilter
    public paginatedresponse<student> getalldata(int page, int size, String search) {

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name").ascending());
        Page<student> data;

        if (search != null && !search.trim().isEmpty()) {
            data = studentRepo.findByNameContainingIgnoreCase(search.trim(), pageable);
        } else {
            data = studentRepo.findAll(pageable);
        }

        // Store in variable
        paginatedresponse<student> response = new paginatedresponse<>(data.getContent(), (int) data.getTotalElements(), page, size);

        // Return the stored response
        return response;

    }


    //[paginationme2
    public ResponseEntity<?> getalldatame2(int page, int size) {

        //Pageable pages=PageRequest(page,size,Sort.by("id"));
        // page <student> stud=studentRepo.findAll(pages) ;


        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<student> productPage = studentRepo.findAll(pageable);

        List<student> products = productPage.getContent();
        long totalItems = productPage.getTotalElements();
        int totalPages = productPage.getTotalPages();
        int currentPage = productPage.getNumber();
        int pageSize = productPage.getSize();

        // Optional: return custom response
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("totalItems", totalItems);
        response.put("totalPages", totalPages);
        response.put("currentPage", currentPage);
        response.put("pageSize", pageSize);

        return ResponseEntity.ok(response);
    }


    //pagination with filterqueryforSinglemain in comp(with single data example=name[mugila])
    public paginatedresponsefilter getUsers(Integer page, int pageSize, String filterQuery) {


        Map<String, List<String>> filters = FilterQueryUtil.parseFilterQuery(filterQuery);
        String  Name = filters.getOrDefault("name", List.of("")).get(0); // Get name filter if present

        int safePage = (page != null && page > 0) ? page - 1 : 0;

        Pageable pageable = PageRequest.of(safePage, pageSize);
        Page<student> allPart;

        if ( Name != null && ! Name.isBlank()) {
            allPart = studentRepo.findByNameContainingIgnoreCase( Name, pageable);
        } else {
            allPart = studentRepo.findAll(pageable);
        }




        return new paginatedresponsefilter(
                allPart.getContent(),
                allPart.getTotalElements(),
                allPart.getNumber(),
                allPart.getSize());


    }






}

