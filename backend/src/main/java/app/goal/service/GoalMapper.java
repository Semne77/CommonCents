package app.goal.service;

import app.goal.dto.CreateGoalRequest;
import app.goal.dto.GoalResponse;
import app.goal.dto.UpdateGoalRequest;
import app.goal.model.Goal;

import java.time.LocalDate;

public final class GoalMapper {
    private GoalMapper() {}

    public static Goal toEntity(CreateGoalRequest req) {
        Goal g = new Goal();
        g.setUserId(req.userId());
        g.setCategory(req.category());
        g.setGoalType(req.goalType());
        g.setPeriod(req.period());
        g.setTargetAmount(req.targetAmount());
        g.setStartDate(req.startDate());
        g.setEndDate(req.endDate());
        g.setCreatedAt(LocalDate.now());
        return g;
    }

    public static void applyUpdate(Goal g, UpdateGoalRequest req) {
        // userId should not typically change, but we’ll keep it consistent with your current design:
        g.setUserId(req.userId());
        g.setCategory(req.category());
        g.setGoalType(req.goalType());
        g.setPeriod(req.period());
        g.setTargetAmount(req.targetAmount());
        g.setStartDate(req.startDate());
        g.setEndDate(req.endDate());
    }

    public static GoalResponse toResponse(Goal g) {
        return new GoalResponse(
                g.getGoalId(),
                g.getUserId(),
                g.getCategory(),
                g.getGoalType(),
                g.getPeriod(),
                g.getTargetAmount(),
                g.getStartDate(),
                g.getEndDate(),
                g.getCreatedAt()
        );
    }
}
