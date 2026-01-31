package myself.custom.asset.service;

import myself.custom.asset.model.DiaryLog;
import myself.custom.asset.repo.DiaryLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DiaryService {

    @Autowired
    private DiaryLogRepository diaryLogRepository;

    public List<DiaryLog> getAllLogs() {
        return diaryLogRepository.findAll();
    }

    public List<DiaryLog> getLogsByRange(Timestamp start, Timestamp end) {
        return diaryLogRepository.findByTransDateBetweenOrderByTransDateDesc(start, end);
    }

    public DiaryLog saveLog(DiaryLog log) {
        if (log.getLogTime() == null) {
            log.setLogTime(new Timestamp(System.currentTimeMillis()));
        }
        return diaryLogRepository.save(log);
    }

    public void deleteLog(Long id) {
        diaryLogRepository.deleteById(id);
    }
}
