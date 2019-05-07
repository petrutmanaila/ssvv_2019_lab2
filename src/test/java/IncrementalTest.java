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
import Validator.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IncrementalTest {

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
        serviceNote = new ServiceNote(noteRepo);
    }

    @Test
    public void addStudentValid() {
        int initialSize = studentRepo.size();

        try {
            studentService.add(new Student("10", "Ionel", 934, "ionel2144@scs.ubbcluj.ro", "proff"));
        } catch (ValidationException ve) {
            assert (studentRepo.size() == initialSize);
            return;
        }
        assert (studentRepo.size() == initialSize + 1);
    }

    @Test
    public void addAssignmentIntegration() {
        addStudentValid();

        int initialSize = temeRepo.size();
        try {
            serviceTeme.add(new Teme(1, "Tema", 3, 4));
        } catch (ValidationException ve) {
            assert (studentRepo.size() == initialSize);
            return;
        }
        assert (temeRepo.size() == initialSize + 1);
    }

    @Test
    public void addGradeIntegration() {
        addStudentValid();
        addAssignmentIntegration();

        int initialSize = noteRepo.size();
        Student student = new Student("10", "Ionel", 934, "ionel2144@scs.ubbcluj.ro", "proff");
        Teme tema = new Teme(1, "Tema", 3, 4);
        Nota nota = new Nota(new AbstractMap.SimpleEntry<String, Integer>("id", 10),student, tema, 10, 4);
        serviceNote.add(nota, "src/test/java/Catalog.xml");

        assert (noteRepo.size() == initialSize + 1);
    }

    @Test
    public void incrementalIntegration() {
        addStudentValid();
        addAssignmentIntegration();
        addGradeIntegration();
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
