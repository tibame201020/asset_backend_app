package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
@Schema(description = "應用程式設定 (鍵值對)")
public class AppSetting implements Serializable {
    @Id
    @Schema(description = "設定鍵名 (主鍵)", example = "balance_goal")
    private String keyName;

    @Schema(description = "設定值", example = "2000")
    private String value;
}
