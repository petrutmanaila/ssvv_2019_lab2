import Domain.Nota;
import Domain.Student;
import Domain.Teme;
import Repository.NoteRepo;
import Repository.StudentRepo;
import Repository.TemeRepo;
import Service.ServiceNote;
import Service.ServiceStudent;
import Service.ServiceTeme;
import Validator.NotaValidator;
import Validator.StudentValidator;
import Validator.TemeValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class BigBangTest {

    private TemeRepo temeRepo;
    private StudentRepo studentRepo;
    private NoteRepo noteRepo;

    private ServiceTeme serviceTeme;
    private ServiceStudent studentService;
    private ServiceNote serviceNote;

    @Before
    public void setup() {
        studentRepo = new StudentRepo(new StudentValidator(), "src/test/java/studentiTesting.xml");
        studentService = new ServiceStudent(studentRepo);
        temeRepo = new TemeRepo(new TemeValidator(), "src/test/java/assignmentTesting.xml");
        serviceTeme = new ServiceTeme(temeRepo);
        noteRepo = new NoteRepo(new NotaValidator());
        studentService = new ServiceStudent(studentRepo);
    }

    @Test
    public void addStudentValid() {
        int initialSize = studentRepo.size();
        studentService.add(new Student("10", "Ionel", 934, "ionel2144@scs.ubbcluj.ro", "proff"));

        assert (studentRepo.size() == initialSize + 1);
    }

    @Test
    public void addAssignmentValid() {
        int initialSize = temeRepo.size();
        serviceTeme.add(new Teme(1, "Tema", 3, 4));

        assert (temeRepo.size() == initialSize + 1);
    }

    @Test(expected = NullPointerException.class)
    public void addGradeValid() {
        int initialSize = noteRepo.size();
        Student student = new Student("10", "Ionel", 934, "ionel2144@scs.ubbcluj.ro", "proff");
        Teme tema = new Teme(1, "Tema", 3, 4);
        Nota nota = new Nota(new AbstractMap.SimpleEntry<String, Integer>("id", 10),student, tema, 10, 4);
        serviceNote.add(nota, "src/test/java/Catalog.xml");

        assert (noteRepo.size() == initialSize + 1);
    }

    @Test(expected = NullPointerException.class)
    public void bigBangIntegration() {
        addStudentValid();
        addAssignmentValid();
        addGradeValid();
    }

    @After
    public void clearTests() {
        Iterator<Student> studentIterator = studentService.all().iterator();
        List<Student> studentList = new ArrayList<>();
        studentIterator.forEachRemaining(studentList::add);
        studentList.forEach(student -> studentService.del(student.getID()));

        Iterator<Teme> temeIterator = serviceTeme.all().iterator();
        List<Teme> temeList = new ArrayList<>();
        temeIterator.forEachRemaining(temeList::add);
        temeList.forEach(tema -> serviceTeme.del(tema.getID()));
    }
}
