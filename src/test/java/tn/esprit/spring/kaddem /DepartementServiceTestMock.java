package tn.esprit.spring.kaddem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class DepartementServiceTestMock {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllDepartements() {
        when(departementRepository.findAll()).thenReturn(Arrays.asList(
                new Departement("Informatique"),
                new Departement("Mécanique")
        ));
        List<Departement> departements = departementService.retrieveAllDepartements();
        assertNotNull(departements);
        assertEquals(2, departements.size());
    }

    @Test
    public void testAddDepartement() {
        Departement newDepartement = new Departement("Biologie");
        when(departementRepository.save(any(Departement.class))).thenReturn(new Departement("Biologie"));
        Departement savedDepartement = departementService.addDepartement(newDepartement);
        assertNotNull(savedDepartement);
        assertEquals("Biologie", savedDepartement.getNomDepart());
    }
}

