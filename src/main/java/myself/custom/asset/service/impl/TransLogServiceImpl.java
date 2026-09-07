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
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransLogServiceImpl implements TransLogService {

    private static final Set<String> EXPENSE_CATEGORIES = Set.of("食", "衣", "住", "行", "育", "樂", "其他");
    private static final Set<String> INCOME_CATEGORIES = Set.of("薪資", "投資", "其他");

    @Autowired
    private TransLogRepo transLogRepo;

    @Override
    public TransLog saveTransLog(TransLog transLog) {
        validateTaxonomy(transLog);
        if (transLog.getValue() < 0) {
            throw new IllegalArgumentException("Transaction value must be non-negative; use type to distinguish income/expense.");
        }
        transLog.setLogTime(new Timestamp(System.currentTimeMillis()));
        return transLogRepo.save(transLog);
    }

    private void validateTaxonomy(TransLog transLog) {
        Set<String> allowed;
        if ("支出".equals(transLog.getType())) {
            allowed = EXPENSE_CATEGORIES;
        } else if ("收入".equals(transLog.getType())) {
            allowed = INCOME_CATEGORIES;
        } else {
            throw new IllegalArgumentException("Transaction type must be 收入 or 支出.");
        }
        if (!allowed.contains(transLog.getCategory())) {
            throw new IllegalArgumentException(
                    "Invalid category for " + transLog.getType() + ": " + transLog.getCategory() + ". Allowed: " + allowed);
        }
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
            if ("expand".equals(type) && "支出".equals(transLog.getType())) return true;
            if ("income".equals(type) && "收入".equals(transLog.getType())) return true;
            return "all".equals(type);
        }).sorted(
                Comparator.comparing(TransLog::getTransDate).reversed()
                        .thenComparing(TransLog::getType)
                        .thenComparing(TransLog::getCategory)
                        .thenComparing(TransLog::getName))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteTransLogById(long id) {
        transLogRepo.deleteById(id);
        return true;
    }
}
