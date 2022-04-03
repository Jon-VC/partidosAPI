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
import uol.compass.partidosAPI.model.Party;
import uol.compass.partidosAPI.model.constants.Ideology;
import uol.compass.partidosAPI.repository.PartyRepository;
import uol.compass.partidosAPI.service.PartyServiceImpl;

import java.util.*;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartyControllerTest.class)
class PartyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    PartyRepository partyRepository;
    @MockBean
    PartyServiceImpl partyService;

    Party RECORD_1 = new Party("Progressistas", "PP", Ideology.CENTRO, new Date(1995, 11, 16));
    Party RECORD_2 = new Party("Movimento Democrático Brasileiro", "MDB", Ideology.DIREITA, new Date(1981, 06, 30));
    Party RECORD_3 = new Party("Partido da Social Democracia Brasileira", "PSDB", Ideology.CENTRO, new Date(1988, 06, 25));

    @Test
    public void getAllParties_success() throws Exception {
        List<Party> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2, RECORD_3));

        Mockito.when(partyRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/partidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(3)))
                .andExpect((ResultMatcher) jsonPath("$[2].name", is("Movimento Democrático Brasileiro")));
    }

    @Test
    public void getPatientById_success() throws Exception {
        Mockito.when(partyRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/partidos/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.name", is("Progressistas")));
    }

    @Test
    public void createPartyRecord_success() throws Exception {
        Party record = new Party("Partido dos Trabalhadores", "PT", Ideology.ESQUERDA, new Date(1981, 06, 30));

        Mockito.when(partyRepository.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/partidos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.name", is("John Doe")));
    }

    @Test
    public void updatePartyRecord_nullId() throws Exception {
        Party updatedRecord = new Party("Partido dos Trabalhadores", "PT", Ideology.ESQUERDA, new Date(1981, 06, 30));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/partidos")
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
                        Assertions.assertEquals("PatientRecord or ID must not be null!", result.getResolvedException().getMessage()));
    }

    @Test
    public void updatePartyRecord_recordNotFound() throws Exception {
        Party updatedRecord = new Party("Partido dos Trabalhadores", "PT", Ideology.ESQUERDA, new Date(1981, 06, 30));

        Mockito.when(partyRepository.findById(updatedRecord.getId())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/partidos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        Assertions.assertTrue(result.getResolvedException() instanceof BusinessException))
                .andExpect(result ->
                        Assertions.assertEquals("Patient with ID 5 does not exist.", result.getResolvedException().getMessage()));
    }
}