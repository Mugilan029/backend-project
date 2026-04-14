package com.example.crudprojects.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crudprojects.entity.student;
import com.example.crudprojects.response.paginatedresponse;
import com.example.crudprojects.response.paginatedresponsefilter;
import com.example.crudprojects.service.StudentService;

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


    @GetMapping("/getalldata")
    public List<student> getdata() {

        List<student> list = new ArrayList<>();

        list.add(new student(1, "Arun", "Male", "Java"));
        list.add(new student(2, "Priya", "Female", "Angular"));
        list.add(new student(3, "Karthik", "Male", "Spring Boot"));

        return list;
    }





}








