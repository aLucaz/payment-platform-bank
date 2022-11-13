package pe.client.custom.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransferRequestDto {
    @JsonProperty(value = "exp_month")
    private String expMonth;
    @JsonProperty(value = "exp_year")
    private String expYear;
    @JsonProperty(value = "card_number")
    private String cardNumber;
    @JsonProperty(value = "client_id")
    private String clientId;
    @JsonProperty(value = "amount")
    private double amount;
    @JsonProperty(value = "business_application_id")
    private String businessApplicationId;
    @JsonProperty(value = "currency")
    private String currency;
}
