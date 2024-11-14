package tn.esprit.spring.kaddem;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class EtudiantServiceTestMock {
    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllEtudiants() {
        when(etudiantRepository.findAll()).thenReturn(Arrays.asList(new Etudiant("Nom1", "Prenom1"), new Etudiant("Nom2", "Prenom2")));
        List<Etudiant> etudiants = etudiantService.retrieveAllEtudiants();
        assertNotNull(etudiants);
        assertEquals(2, etudiants.size());
    }

    @Test
    public void testAddEtudiant() {
        Etudiant newEtudiant = new Etudiant("Nom3", "Prenom3");
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(new Etudiant("Nom3", "Prenom3"));
        Etudiant savedEtudiant = etudiantService.addEtudiant(newEtudiant);
        assertNotNull(savedEtudiant);
        assertEquals("Nom3", savedEtudiant.getNomE());
    }
}
