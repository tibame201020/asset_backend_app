package myself.custom.asset.service.impl;


import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.TransLog;
import myself.custom.asset.repo.TransLogRepo;
import myself.custom.asset.service.TransLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
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
        }).collect(Collectors.toList());
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
