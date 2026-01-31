package myself.custom.asset.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@jakarta.persistence.Table(indexes = {
        @jakarta.persistence.Index(name = "idx_diary_log_date", columnList = "transDate")
})
public class DiaryLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Title of the diary entry
    private String title;

    // Main content (Large text)
    @jakarta.persistence.Column(columnDefinition = "TEXT")
    private String content;

    // The date this entry represents
    private Timestamp transDate;

    // When this record was created/updated
    private Timestamp logTime;

    // Optional mood (Emoji or short text)
    private String mood;
}
