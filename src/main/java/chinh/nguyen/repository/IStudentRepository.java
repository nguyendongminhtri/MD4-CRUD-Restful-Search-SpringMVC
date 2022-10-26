package chinh.nguyen.repository;

import chinh.nguyen.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IStudentRepository extends JpaRepository<Student, Long> {
    Boolean existsByName(String name);
    List<Student> findAllByNameContaining(String name);
    Page<Student> findAllByNameContaining(String name, Pageable pageable);
}
