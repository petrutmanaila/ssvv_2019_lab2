import Domain.Student;
import Repository.StudentRepo;
import Service.ServiceStudent;
import Validator.StudentValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StudentTests {

    private StudentRepo studentRepo;
    private ServiceStudent studentService;

    @Before
    public void setup() {
        studentRepo = new StudentRepo(new StudentValidator(), "src/test/java/studentiTesting.xml");
        studentService = new ServiceStudent(studentRepo);
    }

    @Test
    public void addStudentValid() {
        int initialSize = studentRepo.size();
        studentService.add(new Student("10", "Ionel", 934, "ionel2144@scs.ubbcluj.ro", "proff"));

        assert (studentRepo.size() == initialSize + 1);
    }

    @Test
    public void addStudentNull() {
        int initialSize = studentRepo.size();
        studentService.add(null);

        assert (studentRepo.size() == initialSize);
    }

    @After
    public void clearTests() {
        Iterator<Student> studentIterator = studentService.all().iterator();
        List<Student> studentList = new ArrayList<>();

        studentIterator.forEachRemaining(studentList::add);

        studentList.forEach(student -> studentService.del(student.getID()));
    }
}
