package pe.client.custom.app.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import pe.client.custom.app.util.constant.Visa;

import java.util.Map;

@FeignClient(
    name = "visa-client",
    url = "${visa.api.base.url}"
)
public interface VisaClient {

    @PostMapping(
        value = Visa.VISA_FUNDS_TRANSFER,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    JsonNode fundsTransfer(@RequestBody Map<String, Object> body, @RequestHeader Map<String, String> headers);
}
