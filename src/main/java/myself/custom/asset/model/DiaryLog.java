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
        @jakarta.persistence.Index(name = "idx_diary_log_date", columnList = "transDate")
})
@Schema(description = "日記紀錄")
public class DiaryLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "主鍵 ID (自動產生)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "日記標題不能為空")
    @Schema(description = "日記標題", example = "今天的心得")
    private String title;

    @NotBlank(message = "日記內容不能為空")
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    @Schema(description = "日記內容 (大型文字)", example = "今天天氣很好...")
    private String content;

    @NotNull(message = "日記日期不能為空")
    @Schema(description = "日記日期")
    private Timestamp transDate;

    @Schema(description = "紀錄建立/更新時間")
    private Timestamp logTime;

    @Schema(description = "心情 (Emoji 或短文字)", example = "😊")
    private String mood;
}
