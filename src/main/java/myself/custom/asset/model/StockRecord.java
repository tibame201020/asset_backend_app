package myself.custom.asset.model;

import lombok.*;
import myself.custom.asset.enums.RecordAction;
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
public class StockRecord implements Serializable {
    @EmbeddedId
    private CustomPk customPk;
    private RecordAction action;
    @Lob
    private Stock stock;

}
