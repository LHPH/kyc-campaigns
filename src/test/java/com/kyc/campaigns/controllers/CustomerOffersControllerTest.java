package com.kyc.campaigns.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.campaigns.delegate.CustomerOffersDelegate;
import com.kyc.campaigns.model.OfferData;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CustomerOffersController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CustomerOffersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerOffersDelegate delegate;

    JacksonTester<Object> jacksonTester;

    @BeforeEach
    public void setUp(){

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this,objectMapper);
    }


    @Test
    public void getOffersByCustomer_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        List<OfferData> list = new ArrayList<>();
        ResponseEntity<ResponseData<List<OfferData>>> response = TestsUtil.getResponseTest(list);

        when(delegate.getOffersByCustomer(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(get("/customer/{customerId}/offers",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOfferByCustomer_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        ResponseEntity<ResponseData<OfferData>> response = TestsUtil.getResponseTest(new OfferData());

        when(delegate.getOfferByCustomer(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(get("/customer/{customerId}/offers/{offerId}",1,1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
