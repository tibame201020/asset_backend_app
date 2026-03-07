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
@Schema(description = "運動類型定義")
public class ExerciseType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "運動名稱不能為空")
    @Schema(description = "運動名稱", example = "跑步")
    private String name;

    @Schema(description = "圖示 (Emoji)", example = "🏃")
    private String icon;

    @Schema(description = "預設時長 (分鐘)", example = "30.0")
    private Double defaultDuration;

    @Schema(description = "每小時消耗卡路里", example = "500.0")
    private Double kcalPerHour;
}
