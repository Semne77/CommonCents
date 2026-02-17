package app.goal.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateGoalRequest(
        Long userId,
        String category,
        String goalType,
        String period,
        BigDecimal targetAmount,
        LocalDate startDate,
        LocalDate endDate
) {}
