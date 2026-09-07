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
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@Schema(description = "餐次類型定義，用於飲食分析的固定分類維度")
public class MealType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "餐次名稱不能為空")
    @Schema(description = "餐次名稱", example = "午餐")
    private String name;

    @Schema(description = "圖示 (Emoji)", example = "🍱")
    private String icon;
}
