package com.school.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.school.entities.Students;

@RepositoryRestResource
public interface StudentsRepository extends JpaRepository<Students, Long>, JpaSpecificationExecutor<Students> {
	
	@Query(value="SELECT * FROM students ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
	Students findOne();

}
