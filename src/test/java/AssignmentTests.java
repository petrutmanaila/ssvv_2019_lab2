import Domain.Teme;
import Repository.TemeRepo;
import Service.ServiceTeme;
import Validator.TemeValidator;
import Validator.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AssignmentTests {

    private TemeRepo temeRepo;
    private ServiceTeme serviceTeme;

    @Before
    public void setup() {
        temeRepo = new TemeRepo(new TemeValidator(), "src/test/java/assignmentTesting.xml");
        serviceTeme = new ServiceTeme(temeRepo);
    }

    @Test
    public void addAssignmentValid() {
        int initialSize = temeRepo.size();
        serviceTeme.add(new Teme(1, "Tema", 3, 4));

        assert (temeRepo.size() == initialSize + 1);
    }

    @Test
    public void addAssignmentDuplicate() {
        int initialSize = temeRepo.size();
        serviceTeme.add(new Teme(1, "Tema", 3, 4));
        serviceTeme.add(new Teme(1, "Tema Lab 6", 6, 8));

        assert (temeRepo.size() == initialSize + 1);
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentIdNull() {
        serviceTeme.add(new Teme(null, "Tema", 3, 4));
    }

    @Test(expected = ValidationException.class)
    public void addAssignmentIdNegative() {
        serviceTeme.add(new Teme(-1, "Tema", 3, 4));
    }

    @Test(expected = ValidationException.class)
    public void addDeadlineInvalid() {
        serviceTeme.add(new Teme(1, "Tema", 3, 15));
    }

    @Test(expected = ValidationException.class)
    public void addSaptPrimireInvalid() {
        serviceTeme.add(new Teme(1, "Tema", -1, 4));
    }

    @Test(expected = ValidationException.class)
    public void addIdAndDeadlineInvalid() {
        serviceTeme.add(new Teme(-1, "Tema", 3, 15));
    }

    @Test(expected = ValidationException.class)
    public void addDeadlineAndSaptPrimireInvalid() {
        serviceTeme.add(new Teme(1, "Tema", -1, -1));
    }

    @Test(expected = ValidationException.class)
    public void addAllInvalid() {
        serviceTeme.add(new Teme(-1, "Tema", -1, -1));
    }

    @After
    public void clearTests() {
        Iterator<Teme> temeIterator = serviceTeme.all().iterator();
        List<Teme> temeList = new ArrayList<>();

        temeIterator.forEachRemaining(temeList::add);

        temeList.forEach(tema -> serviceTeme.del(tema.getID()));
    }
}
