package myself.custom.asset.model.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Stock implements Serializable {
    String code;
    double quantity;
    double price;
}
