package com.kyc.campaigns.services;

import com.kyc.campaigns.entity.OfferEntity;
import com.kyc.campaigns.mappers.OfferMapper;
import com.kyc.campaigns.model.OfferData;
import com.kyc.campaigns.repositories.OfferRepository;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.properties.KycMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;

import javax.xml.ws.Response;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerOfferServiceTest {

    @InjectMocks
    private CustomerOffersService service;

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferMapper offerMapper;

    @Mock
    private KycMessages kycMessages;

    @BeforeAll
    public static void init(){
        MockitoAnnotations.openMocks(CustomerOfferServiceTest.class);
    }

    @Test
    public void getOffersByCustomer_gettingTheOffer_returnTheCustomerOffer(){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("customerId",1))
                .build();

        when(offerRepository.getOffersFromActiveCampaignForCustomer(1))
                .thenReturn(Collections.singletonList(new OfferEntity()));
        when(offerMapper.toOfferData(any(OfferEntity.class))).thenReturn(new OfferData());

        ResponseData<List<OfferData>> response = service.getOffersByCustomer(req);

        Assertions.assertEquals(HttpStatus.OK,response.getHttpStatus());
        Assertions.assertFalse(response.getData().isEmpty());
    }

    @Test
    public void getOffersByCustomer_unavailableDatabase_returnError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            RequestData<Void> req = RequestData.<Void>builder()
                    .pathParams(Collections.singletonMap("customerId",1))
                    .build();

            when(offerRepository.getOffersFromActiveCampaignForCustomer(1))
                    .thenThrow(new InvalidDataAccessResourceUsageException("test db error"));

            service.getOffersByCustomer(req);
        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }

    @Test
    public void getOfferByCustomer_gettingOneOffer_returnOffer(){

        Map<String,Object> pathParams = new HashMap<>();
        pathParams.put("customerId",1);
        pathParams.put("offerId",1);

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(pathParams)
                .build();

        when(offerRepository.getCustomerOffer(1,1)).thenReturn(Optional.of(new OfferEntity()));
        when(offerMapper.toOfferData(any(OfferEntity.class))).thenReturn(new OfferData());

        ResponseData<OfferData> responseData = service.getOfferByCustomer(req);
        Assertions.assertEquals(HttpStatus.OK,responseData.getHttpStatus());
    }

    @Test
    public void getOfferByCustomer_noOffer_returnZeroOffers(){

        Map<String,Object> pathParams = new HashMap<>();
        pathParams.put("customerId",1);
        pathParams.put("offerId",1);

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(pathParams)
                .build();

        when(offerRepository.getCustomerOffer(1,1)).thenReturn(Optional.empty());

        ResponseData<OfferData> responseData = service.getOfferByCustomer(req);
        Assertions.assertEquals(HttpStatus.NO_CONTENT,responseData.getHttpStatus());
    }

    @Test
    public void getOfferByCustomer_unavailableDatabase_returnError(){

        KycRestException ex = Assertions.assertThrows(KycRestException.class,()->{

            Map<String,Object> pathParams = new HashMap<>();
            pathParams.put("customerId",1);
            pathParams.put("offerId",1);

            RequestData<Void> req = RequestData.<Void>builder()
                    .pathParams(pathParams)
                    .build();

            when(offerRepository.getCustomerOffer(1,1))
                    .thenThrow(new InvalidDataAccessResourceUsageException("test db error"));

            service.getOfferByCustomer(req);
        });
        Assertions.assertEquals(HttpStatus.SERVICE_UNAVAILABLE,ex.getStatus());
    }


}
