package com.kyc.campaigns.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.campaigns.delegate.PreCampaignDelegate;
import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.util.TestsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PreCampaignController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class PreCampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PreCampaignDelegate delegate;

    JacksonTester<Object> jacksonTester;

    @BeforeEach
    public void setUp(){

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this,objectMapper);
    }

    @Test
    public void uploadPreOffers_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        MockMultipartFile file = new MockMultipartFile("file", "file.xlsx", "multipart/form-data", "bar".getBytes());

        ResultPreLoadOffers result = new ResultPreLoadOffers();
        ResponseEntity<ResponseData<ResultPreLoadOffers>> response = TestsUtil.getResponseTest(result);

        when(delegate.uploadFileOfPreOffers(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(multipart("/pre-offers")
                .file(file)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void cleanness_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        ResponseEntity<ResponseData<Boolean>> response = TestsUtil.getResponseTest(true);

        when(delegate.cleanness()).thenReturn(response);

        mockMvc.perform(post("/pre-offers/cleanness")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
