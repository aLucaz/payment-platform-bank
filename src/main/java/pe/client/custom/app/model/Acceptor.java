package pe.client.custom.app.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class Acceptor {
    @Size(min = 9)
    private String idCode;
    private Address address;
    private String name;
    private String terminalId;
    private String bankId;
    private Integer categoryCode;
}
