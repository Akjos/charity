package pl.coderslab.charity.institution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.coderslab.charity.domain.model.Institution;
import pl.coderslab.charity.domain.repositories.InstitutionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class InstitutionServiceTest {
    private final String INSTITUTION_NAME = "Institution";
    private final String INSTITUTION_DESCRIPTION = "Description";

    @Mock
    private InstitutionRepository institutionRepositoryMock;

    @InjectMocks
    private InstitutionService testObject;


    @Test
    public void shouldReturnInstitutionList() {
//        given
        List<Institution> list = new ArrayList<>();
        Mockito.when(institutionRepositoryMock.findAll()).thenReturn(list);

//        when
        List<Institution> institutionList = testObject.getInstitutionList();

//        then
        assertNotNull(institutionList);

    }

    @Test
    public void shouldSaveInstitutionsAndReturnId() {
//        given
        ArgumentCaptor<Institution> argumentCaptor = ArgumentCaptor.forClass(Institution.class);
        Institution institution = new Institution();
        institution.setId(1L);

        InstitutionDTO institutionToSave = new InstitutionDTO();
        institutionToSave.setName(INSTITUTION_NAME);
        institutionToSave.setDescription(INSTITUTION_DESCRIPTION);
        Mockito.when(institutionRepositoryMock.save(Mockito.any(Institution.class))).thenReturn(institution);

//        when
        Long id = testObject.save(institutionToSave);

//        than
        Mockito.verify(institutionRepositoryMock, Mockito.times(1)).save(argumentCaptor.capture());
        assertEquals(INSTITUTION_NAME, argumentCaptor.getValue().getName());
        assertEquals(INSTITUTION_DESCRIPTION, argumentCaptor.getValue().getDescription());
        assertThat(id, is(1L));

    }

    @Test
    public void shouldReturnOptionalInstitutionWhenGetById() {
//      given
        Institution institution = new Institution();
        institution.setId(1L);
        Mockito.when(institutionRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(institution));

//        when
        Optional<Institution> returnOptional = testObject.getById(Mockito.anyLong());

//        then
        assertTrue(returnOptional.isPresent());
        assertTrue(returnOptional.get().getId() == 1L);
    }

}