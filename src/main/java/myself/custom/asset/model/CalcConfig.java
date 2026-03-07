package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Setter
@Getter
@ToString
@Schema(description = "試算配置")
public class CalcConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "配置鍵名不能為空")
    @Schema(description = "配置鍵名", example = "rate")
    private String key;

    @Schema(description = "用途說明", example = "匯率")
    private String purpose;

    @NotNull(message = "配置數值不能為空")
    @Schema(description = "配置數值", example = "31.5")
    private Double value;

    @Schema(description = "備註描述", example = "美元兌台幣匯率")
    private String description;
}
