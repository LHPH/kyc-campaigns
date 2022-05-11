package com.kyc.campaigns.delegate;

import com.kyc.campaigns.model.CampaignData;
import com.kyc.campaigns.model.CampaignOfferData;
import com.kyc.campaigns.services.CampaignService;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CampaignDelegateTest {

    @InjectMocks
    private CampaignDelegate delegate;

    @Mock
    private CampaignService service;

    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(CampaignDelegateTest.class);
    }

    @Test
    public void confirmCampaign_passRequest_processResponse(){
        
        when(service.confirmCampaign(any(RequestData.class))).thenReturn(ResponseData.of(true));

        delegate.confirmCampaign(RequestData.<CampaignData>builder().build());

        verify(service,times(1)).confirmCampaign(any(RequestData.class));
    }

    @Test
    public void getCampaigns_passRequest_processResponse(){

        when(service.getCampaigns(any(RequestData.class))).thenReturn(ResponseData.of(new ArrayList<>()));

        delegate.getCampaigns(RequestData.<Void>builder().build());

        verify(service,times(1)).getCampaigns(any(RequestData.class));
    }

    @Test
    public void activateCampaign_passRequest_processResponse(){

        when(service.activateCampaign(any(RequestData.class))).thenReturn(ResponseData.of(true));

        delegate.activateCampaign(RequestData.<Void>builder().build());

        verify(service,times(1)).activateCampaign(any(RequestData.class));
    }

    @Test
    public void getOffersByCampaign_pass_Request_processResponse(){

        when(service.getOffersByCampaign(any(RequestData.class))).thenReturn(ResponseData.of(new CampaignOfferData()));

        delegate.getOffersByCampaign(RequestData.<Void>builder().build());

        verify(service,times(1)).getOffersByCampaign(any(RequestData.class));
    }
}
