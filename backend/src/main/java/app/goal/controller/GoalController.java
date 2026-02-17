package app.goal.controller;

import app.goal.dto.CreateGoalRequest;
import app.goal.dto.GoalResponse;
import app.goal.dto.UpdateGoalRequest;
import app.goal.service.GoalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:5173") // tighter than "*"
public class GoalController {

    private final GoalService service;

    public GoalController(GoalService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public List<GoalResponse> getGoalsByUserId(@PathVariable Long userId) {
        return service.getGoalsByUserId(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GoalResponse createGoal(@RequestBody CreateGoalRequest req) {
        return service.createGoal(req);
    }

    @PutMapping("/{goalId}")
    public GoalResponse updateGoal(@PathVariable Long goalId, @RequestBody UpdateGoalRequest req) {
        return service.updateGoal(goalId, req);
    }

    @DeleteMapping("/{goalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGoal(@PathVariable Long goalId) {
        service.deleteGoal(goalId);
    }
}
