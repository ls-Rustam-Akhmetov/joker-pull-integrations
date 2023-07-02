package ru.bcs.perseus.bloomberg.in;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bcs.perseus.bloomberg.model.db.RequestLog;
import ru.bcs.perseus.bloomberg.model.db.RequestLogStatus;
import ru.bcs.perseus.bloomberg.service.RequestLogService;

import java.util.List;

@RestController
@RequestMapping("support")
@AllArgsConstructor
public class RequestLogController {

    private final RequestLogService requestLogService;

    @GetMapping("logs")
    public List<RequestLog> getLogs(
            @RequestParam("requestLogStatuses") List<RequestLogStatus> requestLogStatuses,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "page_size", defaultValue = "10") int pageSize
    ) {
        return requestLogService
                .getRequestLogsByStatusesSortedByLastTryDate(requestLogStatuses, page, pageSize);
    }
}
