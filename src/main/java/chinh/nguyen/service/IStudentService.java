package chinh.nguyen.service;

import chinh.nguyen.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    void deleteById(Long id);
    void save(Student student);
    Boolean existsByName(String name);
    Page<Student> findAll(Pageable pageable);
    List<Student> findAllByNameContaining(String name);
    Page<Student> findAllByNameContaining(String name, Pageable pageable);
}
