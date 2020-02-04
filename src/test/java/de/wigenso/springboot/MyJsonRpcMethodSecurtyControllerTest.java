package de.wigenso.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.wigenso.springboot.jsonrpc.JsonRpcResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MyJsonRpcMethodSecurityController.class)
class MyJsonRpcMethodSecurtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(value = "bob", roles = { "USER", "SPECIAL_BOB" })
    @Test
    void testOnlyForAlice_shouldFailWithForbidden() throws Exception {

        mockMvc.perform(post(MyJsonRpcMethodSecurityController.API)
                .content(getResourceAsString("/json/onlyForAlice.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(value = "bob", roles = { "USER", "SPECIAL_BOB" })
    @Test
    void testOnlyForBob() throws Exception {

        mockMvc.perform(post(MyJsonRpcMethodSecurityController.API)
                .content(getResourceAsString("/json/onlyForBob.json"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String getResourceAsString(final String name) throws IOException {
        return new String(this.getClass().getResourceAsStream(name).readAllBytes());
    }

}
