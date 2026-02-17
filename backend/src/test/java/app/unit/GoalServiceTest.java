package app.unit;

import app.goal.repo.GoalRepository;
import app.goal.service.GoalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private GoalService goalService;

    @Test
    void serviceWiresUp() {
        // This confirms the test + DI setup is correct.
        // Add real tests once you decide which GoalService methods to cover.
        assertNotNull(goalService);
    }
}
