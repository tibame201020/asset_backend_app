package myself.custom.asset.service;

import myself.custom.asset.model.DateRange;
import myself.custom.asset.model.TransLog;

import java.util.List;

public interface TransLogService {

    TransLog saveTransLog(TransLog transLog);

    List<TransLog> queryTransLogBetweenDate(DateRange dateRange);

    boolean deleteTransLogById(long id);
}
