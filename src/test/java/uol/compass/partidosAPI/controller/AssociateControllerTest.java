package uol.compass.partidosAPI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uol.compass.partidosAPI.exceptions.BusinessException;
import uol.compass.partidosAPI.model.Associate;
import uol.compass.partidosAPI.model.constants.Gender;
import uol.compass.partidosAPI.model.constants.PoliticalOffice;
import uol.compass.partidosAPI.repository.AssociateRepository;
import uol.compass.partidosAPI.service.AssociateServiceImpl;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartyControllerTest.class)
class AssociateControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    AssociateRepository associateRepository;
    @MockBean
    AssociateServiceImpl associateService;

    Associate RECORD_1 = new Associate("Gilberto Nascimento JR", PoliticalOffice.VEREADOR, new Date(1967,10, 25), Gender.MASCULINO);
    Associate RECORD_2 = new Associate("Robert Willians", PoliticalOffice.PREFEITO, new Date(1983, 07, 12), Gender.MASCULINO);
    Associate RECORD_3 = new Associate("Maria Dolores da Cunha", PoliticalOffice.DEPUTADO_ESTADUAL, new Date(1999, 05, 19), Gender.FEMININO);

    @Test
    public void getAllAssociates_success() throws Exception {
        List<Associate> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(associateRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/associados")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(3)))
                .andExpect((ResultMatcher) jsonPath("$[2].name", is("Movimento Democrático Brasileiro")));
    }

    @Test
    public void getAssociateById_success() throws Exception {
        Mockito.when(associateRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/partidos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.name", is("Progressistas")));
    }

    @Test
    public void createAssociateRecord_success() throws Exception {
        Associate record = new Associate("Danilo Schudeller", PoliticalOffice.DEPUTADO_FEDERAL, new Date(1943,02, 28), Gender.MASCULINO);
        Mockito.when(associateRepository.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void updateAssociateRecord_nullId() throws Exception {
        Associate updatedRecord = new Associate("Danilo Schudeller", PoliticalOffice.DEPUTADO_FEDERAL, new Date(1943,02, 28), Gender.MASCULINO);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                {
                    result.getResolvedException();
                    Assertions.fail();
                })
                .andExpect(result ->
                        Assertions.assertEquals("Associate or ID must not be null!", result.getResolvedException().getMessage()));
    }

    @Test
    public void updateAssociateRecord_recordNotFound() throws Exception {
        Associate updatedRecord = new Associate("Danilo Schudeller", PoliticalOffice.DEPUTADO_FEDERAL, new Date(1943,02, 28), Gender.MASCULINO);

        Mockito.when(associateRepository.findById(updatedRecord.getId())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/associados")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        Assertions.assertTrue(result.getResolvedException() instanceof BusinessException))
                .andExpect(result ->
                        Assertions.assertEquals("Associate with ID 5 does not exist.", result.getResolvedException().getMessage()));
    }
}