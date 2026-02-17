package app.goal.service;

import app.goal.dto.CreateGoalRequest;
import app.goal.dto.GoalResponse;
import app.goal.dto.UpdateGoalRequest;
import app.goal.model.Goal;
import app.goal.repo.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private final GoalRepository repo;

    public GoalService(GoalRepository repo) {
        this.repo = repo;
    }

    public List<GoalResponse> getGoalsByUserId(Long userId) {
        return repo.findByUserId(userId).stream()
                .map(GoalMapper::toResponse)
                .toList();
    }

    public GoalResponse createGoal(CreateGoalRequest req) {
        Goal saved = repo.save(GoalMapper.toEntity(req));
        return GoalMapper.toResponse(saved);
    }

    public GoalResponse updateGoal(Long goalId, UpdateGoalRequest req) {
        Goal existing = repo.findByGoalIdAndUserId(goalId, req.userId())
                .orElseThrow(() -> new IllegalArgumentException("Goal not found for this user"));

        GoalMapper.applyUpdate(existing, req);
        Goal saved = repo.save(existing);
        return GoalMapper.toResponse(saved);
    }

    public void deleteGoal(Long goalId) {
        repo.deleteById(goalId);
    }
}
