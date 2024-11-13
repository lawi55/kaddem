package tn.esprit.spring.kaddem.Service;



import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContratServiceImplTest {

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private ContratServiceImpl contratService;

    @Test
    void testRetrieveAllContrats() {
        Contrat c1 = new Contrat();
        Contrat c2 = new Contrat();
        List<Contrat> contrats = Arrays.asList(c1, c2);

        when(contratRepository.findAll()).thenReturn(contrats);

        List<Contrat> result = contratService.retrieveAllContrats();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    void testAddContrat() {
        Contrat contrat = new Contrat();

        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat savedContrat = contratService.addContrat(contrat);

        assertNotNull(savedContrat);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testUpdateContrat() {
        Contrat contrat = new Contrat();

        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat updatedContrat = contratService.updateContrat(contrat);

        assertNotNull(updatedContrat);
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testRetrieveContrat() {
        Integer id = 1;
        Contrat contrat = new Contrat();

        when(contratRepository.findById(id)).thenReturn(Optional.of(contrat));

        Contrat result = contratService.retrieveContrat(id);

        assertNotNull(result);
        assertEquals(contrat, result);
        verify(contratRepository, times(1)).findById(id);
    }

    @Test
    void testRemoveContrat() {
        Integer id = 1;
        Contrat contrat = new Contrat();

        when(contratRepository.findById(id)).thenReturn(Optional.of(contrat));

        contratService.removeContrat(id);

        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void testAffectContratToEtudiant() {
        Integer idContrat = 1;
        String nomE = "John";
        String prenomE = "Doe";

        Etudiant etudiant = new Etudiant();
        etudiant.setContrats(new HashSet<>());

        Contrat contrat = new Contrat();
        contrat.setArchive(false);

        when(etudiantRepository.findByNomEAndPrenomE(nomE, prenomE)).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(idContrat)).thenReturn(contrat);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.affectContratToEtudiant(idContrat, nomE, prenomE);

        assertNotNull(result);
        assertEquals(etudiant, contrat.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testGetChiffreAffaireEntreDeuxDates() {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 2592000000L); // 30 days later

        Contrat contratIA = new Contrat();
        contratIA.setSpecialite(Specialite.IA);
        Contrat contratCloud = new Contrat();
        contratCloud.setSpecialite(Specialite.CLOUD);

        List<Contrat> contrats = Arrays.asList(contratIA, contratCloud);

        when(contratRepository.findAll()).thenReturn(contrats);

        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        assertEquals(700, result); // IA: 300 + CLOUD: 400 for 1 month
        verify(contratRepository, times(1)).findAll();
    }

    @Test
    public void retrieveAndUpdateStatusContrat() {
        List<Contrat> contrats = contratRepository.findAll();

        // Initialize the lists to avoid NullPointerException
        List<Contrat> contrats15j = new ArrayList<>();
        List<Contrat> contratsAarchiver = new ArrayList<>();

        Date dateSysteme = new Date();

        for (Contrat contrat : contrats) {
            if (Boolean.FALSE.equals(contrat.getArchive())) {  // Ensure contrat is not archived
                long differenceInTime = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();
                long differenceInDays = (differenceInTime / (1000 * 60 * 60 * 24)) % 365;

                if (differenceInDays == 15) {
                    contrats15j.add(contrat);
                } else if (differenceInDays == 0) {
                    contratsAarchiver.add(contrat);
                    contrat.setArchive(true);
                    contratRepository.save(contrat);
                }
            }
        }
    }

    @Test
    void testNbContratsValides() {
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000); // 30 days later

        when(contratRepository.getnbContratsValides(startDate, endDate)).thenReturn(3);

        Integer result = contratService.nbContratsValides(startDate, endDate);

        assertEquals(3, result);
        verify(contratRepository, times(1)).getnbContratsValides(startDate, endDate);
    }}