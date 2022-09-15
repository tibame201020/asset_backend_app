package myself.custom.asset.model.requestBean;

import lombok.Getter;
import lombok.Setter;
import myself.custom.asset.model.domain.Stock;

import java.io.Serializable;

@Getter
@Setter
public class ReqBean implements Serializable {
    private String lineId;
    private Stock stock;
}
