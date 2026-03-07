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
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@jakarta.persistence.Table(indexes = {
        @jakarta.persistence.Index(name = "idx_meal_log_date", columnList = "transDate")
})
@Schema(description = "飲食紀錄")
public class MealLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "餐點名稱不能為空")
    @Schema(description = "餐點名稱", example = "雞排便當")
    private String mealName;

    @Schema(description = "卡路里", example = "650.0")
    private Double calories;

    @NotNull(message = "用餐日期不能為空")
    @Schema(description = "用餐日期")
    private Timestamp transDate;

    @Schema(description = "備註", example = "外帶")
    private String ps;

    @Schema(description = "紀錄時間")
    private Timestamp logTime;
}
