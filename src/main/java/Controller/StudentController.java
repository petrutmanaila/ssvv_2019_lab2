package Controller;

import Domain.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "students")
public class StudentController {

    @PostMapping
    public ResponseEntity addStudent(@RequestBody Student student) {
        String m = "";
        if (student.getID() == null || student.getID().equals("") || !student.getID().matches("[0-9]+"))
            m = m + "\nID invalid";
        if (student.getGrupa() < 111 || student.getGrupa() > 937 || student.getGrupa() % 100 / 10 < 1 || student.getGrupa() % 100 / 10 > 3 || student.getGrupa() % 10 < 1 || student.getGrupa() % 10 > 7)
            m = m + "\nGrupa invalida";
        if (!student.getEmail().contains("@") || !student.getEmail().contains("."))
            m = m + "\nEmail invalid";
        if(!student.getNume().matches("[A-Za-z ,.'-]+"))
            m=m+"\nNume invalid";
        if(!student.getProfesor().matches("[A-Za-z ,.'-]+"))
            m=m+"\nNume profesor invalid";

        if (m.equals("")) {
            return ResponseEntity.status(HttpStatus.OK).body(student);
        }
        return ResponseEntity.badRequest().body(m);
    }

}
