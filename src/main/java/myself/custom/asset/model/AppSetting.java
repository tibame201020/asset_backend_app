package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
@Schema(description = "應用程式設定 (鍵值對)")
public class AppSetting implements Serializable {
    @Id
    @NotBlank(message = "設定鍵名不能為空")
    @Schema(description = "設定鍵名 (主鍵)", example = "balance_goal")
    private String keyName;

    @NotBlank(message = "設定值不能為空")
    @Schema(description = "設定值", example = "2000")
    private String value;
}
