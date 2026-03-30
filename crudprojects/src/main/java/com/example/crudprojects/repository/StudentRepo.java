package com.example.crudprojects.repository;

import com.example.crudprojects.entity.student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<student, Integer>, JpaSpecificationExecutor<student> {


    List<student> findBytechnology(String technology);


    @Query(value = "SELECT * FROM studentsstable WHERE gender = :gender AND technology = :technology", nativeQuery = true)
    List<student> findByGenderAndTechnology(@Param("gender") String gender, @Param("technology") String technology);

   // List<student> findByfilter(@Param("gender") String gender, @Param("technology") String technology);






    //paginationwithnnormalfilteraaaand

    Page<student> findByNameContainingIgnoreCase(String name, Pageable pageable);



}
