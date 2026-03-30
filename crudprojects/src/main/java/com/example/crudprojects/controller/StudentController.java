package com.example.crudprojects.controller;

import com.example.crudprojects.entity.student;
import com.example.crudprojects.response.paginatedresponse;
import com.example.crudprojects.response.paginatedresponsefilter;
import com.example.crudprojects.service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
     StudentService studentService;

    //get all data-fr

    @GetMapping("/Students")
    public  ResponseEntity<List<student> >getStudents(){

        List<student> stud = studentService.getAllStudents();
        return ResponseEntity.ok(stud);
    }

    @GetMapping("/Students/{id}")
    public student getStudent(@PathVariable int id){
        return studentService.getbyid(id);

    }

    //add data -fr

    @PostMapping("/addstud")
    public ResponseEntity<student> addStudent(@RequestBody student student){
        student students= studentService.addStudent(student);
        return ResponseEntity.ok(students);
    }
    @PutMapping("/update")
    public String updateStudent(@RequestBody student student){
        studentService.update(student);

        return "successfully  updated student    ";
    }
    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable int id){
        studentService.deletebyid(id);
        return "successfully  deleted student    ";
    }

    @DeleteMapping("/deleteall")
    public String deleteAllStudents(){
        studentService.deletalldata();
        return "successfully  deleted all student    ";
    }


    @GetMapping("/param")
    public student getStudents(@RequestParam("id") int id) {
        return studentService.getById(id);
    }

    @GetMapping("/students/{technology}")
    public List<student> getStudentsByTechnology(@PathVariable String technology){
        return studentService.getbytech(technology);
    }

    @GetMapping("/students/filter")
    public List<student> getStudentsByFilter(@RequestParam("gender") String gender, @RequestParam("technology") String technology){
        return studentService.getfilterdata(gender,technology);
    }

    @GetMapping("/STUD/{id}")
    public ResponseEntity <student> getStudentById(@PathVariable int id){
        student student = studentService.getbyid(id);

        if(student == null){
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(student);
        }
        //return ResponseEntity.ok(student);
    }



    // custom_paginationwith  normal search
    @GetMapping("/custompagination")
    public ResponseEntity<paginatedresponse<student>> getPaginatedProducts(
            @RequestParam(name="page",defaultValue = "1") int page,
            @RequestParam(name ="pageSize",defaultValue = "5") int pagesize,
             @RequestParam(required = false) String search
    ) {

        paginatedresponse<student> user=studentService.getalldata(page,pagesize,search);

        return ResponseEntity.ok(user);
    }




///pagination with filter mpst use in comp

@GetMapping("/paginationwithfilter")
public ResponseEntity<paginatedresponsefilter> getUsers(
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "5") int pageSize,
        @RequestParam(required = false) String filterQuery

) {

    paginatedresponsefilter response = studentService.getUsers(page, pageSize, filterQuery);
    return ResponseEntity.ok(response); // HTTP 200 with body
}





}








