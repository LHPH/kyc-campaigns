package com.kyc.campaigns.delegate;

import com.kyc.campaigns.model.OfferData;
import com.kyc.campaigns.services.CustomerOffersService;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerOffersDelegate {

    @Autowired
    private CustomerOffersService customerOffersService;

    public ResponseEntity<ResponseData<List<OfferData>>> getOffersByCustomer(RequestData<Void> req){

        return customerOffersService.getOffersByCustomer(req).toResponseEntity();
    }

    public ResponseEntity<ResponseData<OfferData>> getOfferByCustomer(RequestData<Void> req){

        return customerOffersService.getOfferByCustomer(req).toResponseEntity();
    }
}
