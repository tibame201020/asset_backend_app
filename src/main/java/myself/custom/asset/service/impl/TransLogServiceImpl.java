package myself.custom.asset.service.impl;


import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.repo.TransLogRepo;
import myself.custom.asset.service.TransLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class TransLogServiceImpl implements TransLogService {

    @Autowired
    private TransLogRepo transLogRepo;


    @Override
    public boolean saveTransLog(TransLog transLog) {
        try {
            transLog.setLogTime(new Timestamp(System.currentTimeMillis()));
            transLogRepo.save(transLog);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<TransLog> queryTransLogBetweenDate(DateRange dateRange) {
        String type = dateRange.getType();
        List<TransLog> transLogList = transLogRepo.findByTransDateBetweenOrderByTransDate(dateRange.getStart(), dateRange.getEnd());

        return transLogList.stream().filter(transLog -> {
            if (type.equals("expand") && transLog.getType().equals("支出")) {
                return true;
            }
            if (type.equals("income") && transLog.getType().equals("收入")) {
                return true;
            }
            if (type.equals("all")) {
                return true;
            }
            return false;
        }).sorted((o1, o2) -> {
            String o1Name = o1.getName().contains("早")?"A" + o1.getName() : o1.getName().contains("中") ? "B" + o1.getName() :o1.getName();
            String o2Name = o2.getName().contains("早")?"A" + o2.getName() : o2.getName().contains("中") ? "B" + o2.getName() :o2.getName();
            return o1Name.compareTo(o2Name);
        }).sorted(Comparator.comparing(TransLog::getCategory))
                .sorted(Comparator.comparing(TransLog::getTransDate))
                .sorted(Comparator.comparing(TransLog::getType))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTransLogById(long id) {
        try {
            transLogRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
