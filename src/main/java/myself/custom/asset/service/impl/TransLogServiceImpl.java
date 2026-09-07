package myself.custom.asset.service.impl;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.repo.TransLogRepo;
import myself.custom.asset.service.TransLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransLogServiceImpl implements TransLogService {

    @Autowired
    private TransLogRepo transLogRepo;

    @Override
    public TransLog saveTransLog(TransLog transLog) {
        transLog.setLogTime(new Timestamp(System.currentTimeMillis()));
        return transLogRepo.save(transLog);
    }

    @Override
    public List<TransLog> queryTransLogBetweenDate(DateRange dateRange) {
        String type = dateRange.getType();
        List<TransLog> transLogList = transLogRepo.findByTransDateBetweenOrderByTransDate(dateRange.getStart(),
                dateRange.getEnd());
        final String keyword = dateRange.getKeyword();
        transLogList = (keyword == null || keyword.isEmpty()) ? transLogList
                : transLogList.stream().filter(transLog -> transLog.toString().contains(keyword))
                        .collect(Collectors.toList());

        return transLogList.stream().filter(transLog -> {
            if (type == null || type.equals("all")) {
                return true;
            }
            if (type.equals("expand") && transLog.getType().equals("支出")) {
                return true;
            }
            return type.equals("income") && transLog.getType().equals("收入");
        }).sorted(
                Comparator.comparing(TransLog::getTransDate).reversed()
                        .thenComparing(TransLog::getType)
                        .thenComparing(TransLog::getCategory)
                        .thenComparing((o1, o2) -> {
                            String o1Name = o1.getName().contains("早") ? "A" + o1.getName()
                                    : o1.getName().contains("中") ? "B" + o1.getName() : o1.getName();
                            String o2Name = o2.getName().contains("早") ? "A" + o2.getName()
                                    : o2.getName().contains("中") ? "B" + o2.getName() : o2.getName();
                            return o1Name.compareTo(o2Name);
                        }))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTransLogById(long id) {
        transLogRepo.deleteById(id);
        return true;
    }
}
