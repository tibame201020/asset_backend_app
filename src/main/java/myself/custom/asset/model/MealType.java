package myself.custom.asset.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Schema(description = "飲食類型定義")
public class MealType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "類型名稱", example = "早餐")
    private String name;

    @Schema(description = "圖示 (Emoji)", example = "🍳")
    private String icon;

    @Schema(description = "預設卡路里", example = "400.0")
    private Double defaultCalories;
}
