package pe.client.custom.app.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.client.custom.app.dto.TransferRequestDto;
import pe.client.custom.app.service.BankPaymentService;
import pe.client.custom.app.service.impl.VisaPaymentService;
import pe.client.custom.app.util.constant.Api;

@RestController
@RequestMapping(path = Api.API_BASE_PATH)
public class BankController {

    private final BankPaymentService bankPaymentService;

    public BankController(VisaPaymentService bankPaymentService) {
        this.bankPaymentService = bankPaymentService;
    }

    @PostMapping(
        path = Api.API_TRANSFER,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> transfer(@RequestBody TransferRequestDto request) {
        var response = bankPaymentService.transfer(request);
        return ResponseEntity.ok(response);
    }
}
