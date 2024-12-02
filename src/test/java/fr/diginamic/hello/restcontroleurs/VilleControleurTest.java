package fr.diginamic.hello.restcontroleurs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.repositories.VilleRepository;
import fr.diginamic.hello.services.VilleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class VilleControleurTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VilleService villeService;

    @Test
    void shouldCreateNewVille() throws Exception {
        // Arrange
        VilleDto villeDto = new VilleDto("Test", 2148000, "99", "machin");

        Mockito.when(villeService.insertVille(Mockito.any(VilleDto.class)))
                .thenReturn(villeDto);

        // Act & Assert
        mockMvc.perform(post("/api/villes/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(villeDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Ville insérée avec succès : Test"));

        Mockito.verify(villeService).insertVille(Mockito.any(VilleDto.class));
    }


}
