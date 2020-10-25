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
import pl.coderslab.charity.rest.InvalidDataException;

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

    @Test
    public void shouldDeleteById() {
//        given
        Optional<Institution> mockInstitution = Optional.of(new Institution());
        Mockito.when(institutionRepositoryMock.findById(Mockito.anyLong())).thenReturn(mockInstitution);

//        when
        testObject.deleteById(Mockito.anyLong());

//        then
        Mockito.verify(institutionRepositoryMock, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(institutionRepositoryMock, Mockito.times(1)).delete(mockInstitution.get());

    }

    @Test(expected = InvalidDataException.class)
    public void shouldThrowExceptionWhenDeleteById() {
//        given
        Mockito.when(institutionRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

//        when
        testObject.deleteById(Mockito.anyLong());

//        than
//        trow InvalidDataException

    }

    @Test
    public void shouldUpdate() {
//        given
        ArgumentCaptor<Institution> argumentCaptor = ArgumentCaptor.forClass(Institution.class);
        Long id = 5L;
        InstitutionDTO institutionDTO = InstitutionDTO.builder()
                .name(INSTITUTION_NAME)
                .description(INSTITUTION_DESCRIPTION).build();
        Optional<Institution> institution = Optional.of(
                Institution.builder()
                        .id(id)
                        .name(INSTITUTION_NAME)
                        .description(INSTITUTION_DESCRIPTION)
                        .build()
        );

        Mockito.when(institutionRepositoryMock.findById(id)).thenReturn(institution);
        Mockito.when(institutionRepositoryMock.save(Mockito.any(Institution.class))).thenReturn(institution.get());

//        when
        Institution updateObject = testObject.update(id, institutionDTO);

//        than
        assertNotNull(updateObject);
        Mockito.verify(institutionRepositoryMock, Mockito.times(1)).save(argumentCaptor.capture());
        assertEquals(institutionDTO.getName(), argumentCaptor.getValue().getName());
        assertEquals(institutionDTO.getDescription(), argumentCaptor.getValue().getDescription());
        assertEquals(id, argumentCaptor.getValue().getId());
    }

    @Test(expected = InvalidDataException.class)
    public void shouldThrowExceptionWhenUpdate() {
//        when
        Mockito.when(institutionRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());

//        given
        testObject.update(Mockito.anyLong(), new InstitutionDTO());

//        then

    }
}