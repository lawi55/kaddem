package tn.esprit.spring.kaddem.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContratServiceTest {

    @InjectMocks
    private ContratServiceImpl contratService;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllContrats() {
        // Arrange
        List<Contrat> mockContrats = Arrays.asList(new Contrat(), new Contrat());
        when(contratRepository.findAll()).thenReturn(mockContrats);

        // Act
        List<Contrat> result = contratService.retrieveAllContrats();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testAddContrat() {
        // Arrange
        Contrat newContrat = new Contrat();
        when(contratRepository.save(newContrat)).thenReturn(newContrat);

        // Act
        Contrat result = contratService.addContrat(newContrat);

        // Assert
        assertNotNull(result);
        verify(contratRepository, times(1)).save(newContrat);
    }

    @Test
    void testUpdateContrat() {
        // Arrange
        Contrat mockContrat = new Contrat();
        when(contratRepository.save(mockContrat)).thenReturn(mockContrat);

        // Act
        Contrat result = contratService.updateContrat(mockContrat);

        // Assert
        assertNotNull(result);
        verify(contratRepository, times(1)).save(mockContrat);
    }

    @Test
    void testRetrieveContrat() {
        // Arrange
        int id = 1;
        Contrat mockContrat = new Contrat();
        mockContrat.setIdContrat(id);
        when(contratRepository.findById(id)).thenReturn(Optional.of(mockContrat));

        // Act
        Contrat result = contratService.retrieveContrat(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getIdContrat());
        verify(contratRepository, times(1)).findById(id);
    }

    @Test
    void testRetrieveContratNotFound() {
        // Arrange
        int id = 1;
        when(contratRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Contrat result = contratService.retrieveContrat(id);

        // Assert
        assertNull(result);
        verify(contratRepository, times(1)).findById(id);
    }

    @Test
    void testRemoveContrat() {
        // Arrange
        int id = 1;
        Contrat mockContrat = new Contrat();
        when(contratRepository.findById(id)).thenReturn(Optional.of(mockContrat));

        // Act
        contratService.removeContrat(id);

        // Assert
        verify(contratRepository, times(1)).delete(mockContrat);
    }

    @Test
    void testNbContratsValides() {
        // Arrange
        Date startDate = new Date();
        Date endDate = new Date();
        int expectedCount = 5;
        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(expectedCount);

        // Act
        int result = contratService.nbContratsValides(startDate, endDate);

        // Assert
        assertEquals(expectedCount, result);
        verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
    }

    @Test
    void testAffectContratToEtudiant() {
        // Arrange
        String nomE = "Doe";
        String prenomE = "John";
        Integer idContrat = 1;
        Etudiant mockEtudiant = new Etudiant();
        mockEtudiant.setNomE(nomE);
        mockEtudiant.setPrenomE(prenomE);
        mockEtudiant.setContrats(new HashSet<>()); // Ensure the student has fewer than 5 active contracts

        Contrat mockContrat = new Contrat();
        mockContrat.setIdContrat(idContrat);

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(mockEtudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(mockContrat);

        // Act
        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        // Assert
        assertNotNull(result);
        assertEquals(mockEtudiant, result.getEtudiant());
        verify(contratRepository, times(1)).save(mockContrat);
    }

    @Test
    void testRetrieveAndUpdateStatusContrat() {
        // Arrange
        List<Contrat> mockContrats = Arrays.asList(new Contrat(), new Contrat());
        mockContrats.get(0).setDateFinContrat(new Date(System.currentTimeMillis() - (15L * 24 * 60 * 60 * 1000))); // 15 days ago
        mockContrats.get(1).setDateFinContrat(new Date()); // today


        when(contratRepository.findAll()).thenReturn(mockContrats);

        // Act
        contratService.retrieveAndUpdateStatusContrat();

        // Assert
        verify(contratRepository, times(1)).save(mockContrats.get(1)); // Only the expiring contract should be saved as archived
    }


}
