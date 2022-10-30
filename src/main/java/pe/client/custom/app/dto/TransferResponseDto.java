package pe.client.custom.app.dto;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;
import pe.client.custom.app.util.JsonUtil;

@Data
@Builder
public class TransferResponseDto {
    private String transactionIdentifier;
    private String responseCode;

    public static TransferResponseDto fromVisaTransferResponse(JsonNode visaResponse) {
        return TransferResponseDto.builder()
            .responseCode(JsonUtil.getField("responseCode", visaResponse))
            .transactionIdentifier(JsonUtil.getField("transactionIdentifier", visaResponse))
            .build();
    }
}
