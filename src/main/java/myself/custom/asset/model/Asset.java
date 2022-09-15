package myself.custom.asset.model;

import lombok.*;
import myself.custom.asset.model.domain.Stock;
import myself.custom.asset.model.pk.CustomPk;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Asset implements Serializable {
    @EmbeddedId
    private CustomPk customPk;
    @Lob
    private Stock[] stocks;
    private double cash;

}
