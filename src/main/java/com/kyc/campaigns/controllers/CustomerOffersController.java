package com.kyc.campaigns.controllers;

import com.kyc.campaigns.delegate.CustomerOffersDelegate;
import com.kyc.campaigns.model.OfferData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerOffersController {

    @Autowired
    private CustomerOffersDelegate delegate;

    @GetMapping("/{customerId}/offers")
    public ResponseEntity<ResponseData<List<OfferData>>> getOffersByCustomer(@PathVariable("customerId") Integer customerId){

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(Collections.singletonMap("customerId",customerId))
                .build();
        return delegate.getOffersByCustomer(req);
    }

    @GetMapping("/{customerId}/offers/{offerId}")
    public ResponseEntity<ResponseData<OfferData>> getOfferByCustomer(@PathVariable("customerId") Integer customerId,
                                                                      @PathVariable("offerId") Integer offerId){

        Map<String,Object> pathParams = new HashMap<>();
        pathParams.put("customerId",customerId);
        pathParams.put("offerId",offerId);

        RequestData<Void> req = RequestData.<Void>builder()
                .pathParams(pathParams)
                .build();
        return delegate.getOfferByCustomer(req);
    }
}
