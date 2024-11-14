package tn.esprit.spring.kaddem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.IEtudiantService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class EtudiantServiceTest {
    @Autowired
    private IEtudiantService etudiantService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    public void setUp() {
        // Clean the database or setup test data before each test
        etudiantRepository.deleteAll();

        // Populate test data
        Etudiant et1 = new Etudiant("Nom1", "Prenom1");
        Etudiant et2 = new Etudiant("Nom2", "Prenom2");
        etudiantRepository.save(et1);
        etudiantRepository.save(et2);
    }

    @Test
    public void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();
        assertNotNull(etudiants);
        assertFalse(etudiants.isEmpty());
        assertEquals(2, etudiants.size());
    }

    @Test
    public void testAddEtudiant() {
        Etudiant newEtudiant = new Etudiant("Nom3", "Prenom3");
        Etudiant savedEtudiant = etudiantService.addEtudiant(newEtudiant);
        assertNotNull(savedEtudiant);
        assertNotNull(savedEtudiant.getIdEtudiant());
        assertEquals("Nom3", savedEtudiant.getNomE());
    }
}
