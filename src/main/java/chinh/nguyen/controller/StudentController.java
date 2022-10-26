package chinh.nguyen.controller;

import chinh.nguyen.dto.response.ResponseMessage;
import chinh.nguyen.model.Student;
import chinh.nguyen.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getName().trim().equals("") || student.getName().length() < 3 || student.getName().length() > 10) {
            return new ResponseEntity<>(new ResponseMessage("The name student invalid"), HttpStatus.OK);
        }
        if (studentService.existsByName(student.getName())) {
            return new ResponseEntity<>(new ResponseMessage("The name student exited"), HttpStatus.OK);
        }
        studentService.save(student);
        return new ResponseEntity<>(new ResponseMessage("create success!"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> showListStudent() {
        return new ResponseEntity<>(studentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> pageStudent(@PageableDefault(sort = "name", size = 2) Pageable pageable) {
        return new ResponseEntity<>(studentService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailStudent(@PathVariable Long id) {
        if (!studentService.findById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentService.findById(id).get(), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id,@RequestBody Student student){
        Optional<Student> student1 = studentService.findById(id);
        if(!student1.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (student.getName().trim().equals("") || student.getName().length() < 3 || student.getName().length() > 10) {
            return new ResponseEntity<>(new ResponseMessage("The name student invalid"), HttpStatus.OK);
        }
        if (studentService.existsByName(student.getName())) {
            return new ResponseEntity<>(new ResponseMessage("The name student exited"), HttpStatus.OK);
        }
        student1.get().setName(student.getName());
        studentService.save(student1.get());
        return new ResponseEntity<>(new ResponseMessage("update success!"), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id){
        Optional<Student> student = studentService.findById(id);
        if(!student.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        studentService.deleteById(id);
        return new ResponseEntity<>(new ResponseMessage("Delete success!"), HttpStatus.OK);
    }
    //Cach 1: Search dung voi @PathVariable
    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name){
        if(name.trim().equals("")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentService.findAllByNameContaining(name), HttpStatus.OK);
    }
    //Cach 2: Search su dung @RequestParam
    @GetMapping("/searchs")
    public ResponseEntity<?> searchByNamePage(@RequestParam String name, Pageable pageable){
        if(name.trim().equals("")){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(studentService.findAllByNameContaining(name,pageable), HttpStatus.OK);
    }
}
