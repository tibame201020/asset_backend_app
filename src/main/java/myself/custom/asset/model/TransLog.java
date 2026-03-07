package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@jakarta.persistence.Table(indexes = {
        @jakarta.persistence.Index(name = "idx_trans_log_date", columnList = "transDate")
})
@Schema(description = "交易紀錄 (收支)")
public class TransLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "交易類型不能為空")
    @Schema(description = "交易類型 (收入/支出)", example = "支出")
    private String type;

    @NotBlank(message = "分類不能為空")
    @Schema(description = "分類", example = "飲食")
    private String category;

    @NotNull(message = "交易日期不能為空")
    @Schema(description = "交易日期")
    private Timestamp transDate;

    @NotBlank(message = "項目名稱不能為空")
    @Schema(description = "項目名稱", example = "午餐")
    private String name;

    @NotNull(message = "金額不能為空")
    @Schema(description = "金額", example = "120.0")
    private Double value;

    @Schema(description = "備註", example = "團購便當")
    private String ps;

    @Schema(description = "紀錄時間")
    private Timestamp logTime;
}
