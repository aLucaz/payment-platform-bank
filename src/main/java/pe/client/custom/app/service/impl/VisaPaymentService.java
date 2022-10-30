package pe.client.custom.app.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import pe.client.custom.app.client.VisaClient;
import pe.client.custom.app.config.properties.ClientsDetail;
import pe.client.custom.app.dto.TransferRequestDto;
import pe.client.custom.app.dto.TransferResponseDto;
import pe.client.custom.app.exception.BadRequestException;
import pe.client.custom.app.exception.InternalServerException;
import pe.client.custom.app.model.Acceptor;
import pe.client.custom.app.service.BankPaymentService;
import pe.client.custom.app.util.constant.Format;
import pe.client.custom.app.util.constant.Header;
import pe.client.custom.app.util.constant.Message;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VisaPaymentService implements BankPaymentService {

    @Value("${visa.api.authorization.token}")
    private String visaAuthorizationToken;
    private final VisaClient visaClient;
    private final ClientsDetail clientsDetail;

    public VisaPaymentService(VisaClient visaClient, ClientsDetail clientsDetail) {
        this.visaClient = visaClient;
        this.clientsDetail = clientsDetail;
    }

    @Override
    public TransferResponseDto transfer(TransferRequestDto request) {
        Map<String, Object> body = getVisaPullFundsRequestBody(request);
        Map<String, String> headers = getVisaHeaders();
        JsonNode response = this.callVisaTransfer(body, headers);
        return TransferResponseDto.fromVisaTransferResponse(response);
    }

    @Retryable(
        value = {FeignException.ServiceUnavailable.class},
        maxAttemptsExpression = "${retry.config.maxattempts}",
        backoff = @Backoff(delayExpression = "${retry.config.backoffdelay}")
    )
    public JsonNode callVisaTransfer(Map<String, Object> body, Map<String, String> headers) {
        try {
            var response = this.visaClient.fundsTransfer(body, headers);
            log.info("Visa payment completed successfully");
            return response;
        } catch (FeignException.ServiceUnavailable ex) {
            log.error("Visa API error: service unavailable");
            log.error(ex.getMessage(), ex);
            throw ex;
        } catch (FeignException.BadRequest ex) {
            log.error("Visa API error: bad request");
            log.error(ex.getMessage(), ex);
            throw new BadRequestException(Message.INVALID_REQUEST_DATA_MSG);
        } catch (Exception ex) {
            log.error(Message.UNKNOWN_ERROR_LOG);
            log.error(ex.getMessage(), ex);
            throw new InternalServerException("An Unknown error occurred while validating token");
        }
    }

    private Map<String, String> getVisaHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put(Header.X_AUTHORIZATION, this.visaAuthorizationToken);
        headers.put(Header.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return headers;
    }

    private Map<String, Object> getVisaPullFundsRequestBody(TransferRequestDto request) {
        Acceptor acceptor = getAcceptor(request.getClientId());
        Map<String, Object> body = new HashMap<>();
        body.put("acquirerCountryCode", "PER");
        body.put("acquiringBin", acceptor.getBankId());
        body.put("amount", request.getAmount());
        body.put("businessApplicationId", request.getBusinessApplicationId());
        Map<String, Object> cardAcceptor = new HashMap<>();
        cardAcceptor.put("address", acceptor.getAddress());
        cardAcceptor.put("idCode", acceptor.getIdCode());
        cardAcceptor.put("name", acceptor.getName());
        cardAcceptor.put("terminalId", acceptor.getTerminalId());
        body.put("cardAcceptor", cardAcceptor);
        body.put("localTransactionDateTime", LocalDateTime.now().format(Format.DATE_TIME_FORMATTER));
        body.put("merchantCategoryCode", acceptor.getCategoryCode());
        // sender information
        body.put("senderCardExpiryDate", request.getExpYear() + Format.DATE_SEPARATOR + request.getExpMonth());
        body.put("senderCurrencyCode", request.getCurrency());
        body.put("senderPrimaryAccountNumber", request.getCardNumber());
        return body;
    }

    private Acceptor getAcceptor(String clientId) {
        List<Acceptor> result = this.clientsDetail.getAcceptorInformationList()
            .stream().filter(acc -> acc.getIdCode().equals(clientId))
            .collect(Collectors.toList());
        if (result.isEmpty()) {
            throw new BadRequestException("The client is not registered as bank integration");
        }
        return result.get(0);
    }
}
