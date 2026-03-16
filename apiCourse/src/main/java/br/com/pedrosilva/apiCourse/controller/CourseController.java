package br.com.pedrosilva.apiCourse.controller;

import br.com.pedrosilva.apiCourse.model.CourseModel;
import br.com.pedrosilva.apiCourse.repository.CourseRepository;
import br.com.pedrosilva.apiCourse.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody CourseModel courseModel) {
        if(courseRepository.findByName(courseModel.getName()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já há um curso com este nome");
        }


        var courseCreated = this.courseRepository.save(courseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseCreated);
    }

    @GetMapping("/")
    public ResponseEntity getAllCourses() {
        return ResponseEntity.ok().body(courseRepository.findAll());
    }
    @GetMapping
    public ResponseEntity getCoursesByCategory(@RequestParam String category) {
        return ResponseEntity.ok().body(courseRepository.findByCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody CourseModel courseModel, @PathVariable UUID id) {
        var course = this.courseRepository.findById(id).orElse(null);

        if(course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado");
        }
         Utils.copyNonNullPropertys(courseModel, course);
         return ResponseEntity.ok().body(courseRepository.save(course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        var course = this.courseRepository.findById(id).orElse(null);

        if (course == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado");
        }
        courseRepository.delete(course);
        return ResponseEntity.ok().body("Curso deletado");
    }
}
