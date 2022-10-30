package pe.client.custom.app.service;

import pe.client.custom.app.dto.TransferRequestDto;
import pe.client.custom.app.dto.TransferResponseDto;

public interface BankPaymentService {
    TransferResponseDto transfer(TransferRequestDto request);
}
