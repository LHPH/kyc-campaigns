package com.kyc.campaigns.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyc.campaigns.delegate.CampaignDelegate;
import com.kyc.campaigns.model.CampaignData;
import com.kyc.campaigns.model.CampaignOfferData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.util.TestsUtil;
import org.apache.commons.math3.stat.inference.TestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CampaignController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class CampaignControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignDelegate delegate;

    JacksonTester<Object> jacksonTester;

    @BeforeEach
    public void setUp(){

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this,objectMapper);
    }


    @Test
    public void confirmCampaign_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        CampaignData campaignData = new CampaignData();
        ResponseEntity<ResponseData<Boolean>> response = TestsUtil.getResponseTest(true);

        String req = jacksonTester.write(campaignData).getJson();

        when(delegate.confirmCampaign(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(post("/campaigns/{key}","key")
                .content(req)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void activationCampaign_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        ResponseEntity<ResponseData<Boolean>> response = TestsUtil.getResponseTest(true);

        when(delegate.activateCampaign(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(post("/campaigns/activation/{id}",1)
                .contentType(MediaType.APPLICATION_JSON).param("active","true"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCampaigns_passRequestToDelegate_returnSuccessfulResponse() throws Exception{


        ResponseEntity<ResponseData<List<CampaignData>>> response = TestsUtil.getResponseTest(new ArrayList<>());

        when(delegate.getCampaigns(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(get("/campaigns")
                .contentType(MediaType.APPLICATION_JSON).param("campaignId","1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getOffersByCampaign_passRequestToDelegate_returnSuccessfulResponse() throws Exception{

        ResponseEntity<ResponseData<CampaignOfferData>> response = TestsUtil.getResponseTest(new CampaignOfferData());

        when(delegate.getOffersByCampaign(any(RequestData.class))).thenReturn(response);

        mockMvc.perform(get("/campaigns/{id}/offers",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


}
