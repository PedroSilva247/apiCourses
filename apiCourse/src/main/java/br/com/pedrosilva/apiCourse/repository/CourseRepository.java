package br.com.pedrosilva.apiCourse.repository;


import br.com.pedrosilva.apiCourse.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> {
    CourseModel findByName(String name);
    List<CourseModel> findByCategory(String category);
}
