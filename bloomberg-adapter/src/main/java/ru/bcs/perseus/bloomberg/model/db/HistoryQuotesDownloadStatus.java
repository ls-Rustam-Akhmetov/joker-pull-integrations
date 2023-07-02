package ru.bcs.perseus.bloomberg.model.db;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "instrumentId")
public class HistoryQuotesDownloadStatus {

    @Id
    private String instrumentId;

    private LocalDateTime createdDateTime;
    private LocalDateTime finishDownloadTime;

    public HistoryQuotesDownloadStatus(String instrumentId) {
        this.instrumentId = instrumentId;
    }
}
