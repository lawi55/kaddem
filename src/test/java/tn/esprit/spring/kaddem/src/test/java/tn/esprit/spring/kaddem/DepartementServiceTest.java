package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.IDepartementService;

import java.util.List;

@SpringBootTest
public class DepartementServiceTest {

    @Autowired
    private IDepartementService departementService;

    @Autowired
    private DepartementRepository departementRepository;

    @BeforeEach
    public void setUp() {
        // Clean the database or setup test data before each test
        departementRepository.deleteAll();

        // Populate test data
        Departement dep1 = new Departement("Informatique");
        Departement dep2 = new Departement("Mécanique");
        departementRepository.save(dep1);
        departementRepository.save(dep2);
    }

    @Test
    public void testRetrieveAllDepartements() {
        List<Departement> departements = departementService.retrieveAllDepartements();
        assertNotNull(departements);
        assertFalse(departements.isEmpty());
        assertEquals(2, departements.size());
    }

    @Test
    public void testAddDepartement() {
        Departement newDepartement = new Departement("Biologie");
        Departement savedDepartement = departementService.addDepartement(newDepartement);
        assertNotNull(savedDepartement);
        assertNotNull(savedDepartement.getIdDepart());
        assertEquals("Biologie", savedDepartement.getNomDepart());
    }
}
