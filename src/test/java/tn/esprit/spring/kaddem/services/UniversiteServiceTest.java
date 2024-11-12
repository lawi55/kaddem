package tn.esprit.spring.kaddem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Universite;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.UniversiteRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UniversiteServiceTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllUniversites() {
        Universite universite = new Universite();
        when(universiteRepository.findAll()).thenReturn(List.of(universite));

        List<Universite> universites = universiteService.retrieveAllUniversites();

        assertNotNull(universites);
        assertEquals(1, universites.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);

        assertNotNull(savedUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testUpdateUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite updatedUniversite = universiteService.updateUniversite(universite);

        assertNotNull(updatedUniversite);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        Universite retrievedUniversite = universiteService.retrieveUniversite(1);

        assertNotNull(retrievedUniversite);
        verify(universiteRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        universiteService.deleteUniversite(1);

        verify(universiteRepository, times(1)).delete(universite);
    }

    @Test
    void testAssignUniversiteToDepartement() {
        Universite universite = new Universite();
        universite.setDepartements(new HashSet<>());
        Departement departement = new Departement();

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));
        when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        when(universiteRepository.save(universite)).thenReturn(universite);

        universiteService.assignUniversiteToDepartement(1, 1);

        assertTrue(universite.getDepartements().contains(departement));
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveDepartementsByUniversite() {
        Universite universite = new Universite();
        Set<Departement> departements = new HashSet<>();
        departements.add(new Departement());
        universite.setDepartements(departements);

        when(universiteRepository.findById(1)).thenReturn(Optional.of(universite));

        Set<Departement> retrievedDepartements = universiteService.retrieveDepartementsByUniversite(1);

        assertEquals(1, retrievedDepartements.size());
        verify(universiteRepository, times(1)).findById(1);
    }
}
