package com.kyc.campaigns.services;

import com.kyc.campaigns.entity.ErrorOffersEntity;
import com.kyc.campaigns.entity.OfferTemporalEntity;
import com.kyc.campaigns.mappers.ErrorDetailMapper;
import com.kyc.campaigns.model.ErrorPreOfferDetail;
import com.kyc.campaigns.model.ResultPreLoadOffers;
import com.kyc.campaigns.repositories.ErrorOffersRepository;
import com.kyc.campaigns.repositories.OfferTemporalRepository;
import com.kyc.campaigns.util.CellUtil;
import com.kyc.core.exception.KycRestException;
import com.kyc.core.model.web.MessageData;
import com.kyc.core.model.web.RequestData;
import com.kyc.core.model.web.ResponseData;
import com.kyc.core.properties.KycMessages;
import com.kyc.core.util.DateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_002;
import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_003;
import static com.kyc.campaigns.constants.AppConstants.MSG_CODE_006;
import static com.kyc.core.util.GeneralUtil.getValue;
import static com.kyc.core.util.GeneralUtil.toInt;

@Service
public class PreCampaignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreCampaignService.class);

    @Autowired
    private OfferTemporalRepository offerTemporalRepository;

    @Autowired
    private ErrorOffersRepository errorOffersRepository;

    @Autowired
    private ErrorDetailMapper errorDetailMapper;

    @Autowired
    private KycMessages kycMessages;

    @Transactional
    public ResponseData<ResultPreLoadOffers> uploadFile(RequestData<MultipartFile> req){

        MultipartFile file = req.getBody();
        String keyPreCampaign = UUID.randomUUID().toString();

        List<OfferTemporalEntity> preOffers = processExcelFile(file,keyPreCampaign);

        LOGGER.info("It was processed from excel {} offers",preOffers.size());
        ResultPreLoadOffers response = new ResultPreLoadOffers();
        response.setKeyPreCampaign(keyPreCampaign);
        if(!preOffers.isEmpty()){

           try{

               offerTemporalRepository.saveAll(preOffers);

               List<ErrorOffersEntity> errors = errorOffersRepository.getErrors(keyPreCampaign);
               List<ErrorPreOfferDetail> listErrors = errors
                       .stream()
                       .map(e -> errorDetailMapper.mapperToModel(e))
                       .collect(Collectors.toList());

               response.setSuccessfullyLoadedPreOffers(preOffers.size()-listErrors.size());
               response.setUnsuccessfullyLoadedPreOffers(errors.size());
               response.setErrorPreOffers(listErrors);
               return ResponseData.of(response);
           }
           catch(DataAccessException ex){
               MessageData messageData = kycMessages.getMessage(MSG_CODE_003);
               throw KycRestException.builderRestException()
                       .errorData(messageData)
                       .status(HttpStatus.SERVICE_UNAVAILABLE)
                       .exception(ex)
                       .build();
           }
        }
        MessageData messageData = kycMessages.getMessage(MSG_CODE_006);
        throw KycRestException.builderRestException()
                .errorData(messageData)
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    private List<OfferTemporalEntity> processExcelFile(MultipartFile file, String keyPreCampaign){

        try(InputStream in = file.getInputStream()){

            Workbook workbook  = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();

            List<OfferTemporalEntity> preOffers = new ArrayList<>();
            while(rows.hasNext()){

                Row row = rows.next();
                int rowNum = row.getRowNum();
                if(rowNum>0){

                    Cell[] cells = CellUtil.getCells(row);

                    if(CellUtil.allNullOrBlankCells(cells)){
                        continue;
                    }

                    OfferTemporalEntity record = new OfferTemporalEntity();
                    record.setKeyPreCampaign(keyPreCampaign);
                    record.setRecordExcel(--rowNum);

                    LOGGER.info("Processing row num {}",rowNum);

                    Cell cellCustomerNumber = cells[0];
                    Double customerNumber = getValue(cellCustomerNumber,Cell::getNumericCellValue);
                    record.setCustomerNumber(toInt(customerNumber,null));

                    Cell cellCustomerEmail =  cells[1];
                    record.setCustomerEmail(getValue(cellCustomerEmail,Cell::getStringCellValue));

                    Cell cellOfferName =  cells[2];
                    record.setOfferName(getValue(cellOfferName,Cell::getStringCellValue));

                    Cell cellOfferDesc =  cells[3];
                    record.setOfferDescription(getValue(cellOfferDesc,Cell::getStringCellValue));

                    Cell cellPromotionalCode =  cells[4];
                    record.setPromotionalCode(getValue(cellPromotionalCode,Cell::getStringCellValue));

                    Cell cellDiscount =  cells[5];
                    Double discount = getValue(cellDiscount,Cell::getNumericCellValue);
                    record.setDiscount(toInt(discount,null));

                    Cell cellReward =  cells[6];
                    record.setReward(getValue(cellReward,Cell::getStringCellValue));

                    Cell cellStartDate = cells[7];
                    String startDate = getValue(cellStartDate,Cell::getStringCellValue);
                    record.setStartDate(DateUtil.stringToDate(startDate,"yyyy-MM-dd"));

                    Cell cellEndDate =  cells[8];
                    String endDate = getValue(cellEndDate,Cell::getStringCellValue);
                    record.setEndDate(DateUtil.stringToDate(endDate,"yyyy-MM-dd"));

                    Cell cellTermAndConditions =  cells[9];
                    record.setTermAndConditionsLink(getValue(cellTermAndConditions,Cell::getStringCellValue));

                    record.setProcessed(false);
                    preOffers.add(record);

                }

            }
           return preOffers;
        }
        catch(IOException ioex){
            MessageData messageData = kycMessages.getMessage(MSG_CODE_002);
            throw KycRestException.builderRestException()
                    .errorData(messageData)
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .exception(ioex)
                    .inputData(file)
                    .build();
        }
    }

    @Transactional
    public ResponseData<Boolean> cleanPreOffers(){

        Map<String,Object> result = offerTemporalRepository.cleanTempOffers(5);
        LOGGER.info("{}",result);
        if(result.get("P_ERROR_CODE")==null){
            return ResponseData.of(true);
        }
        MessageData messageData = kycMessages.getMessage(MSG_CODE_003);
        throw KycRestException.builderRestException()
                .errorData(messageData)
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .outputData(result)
                .build();
    }
}
