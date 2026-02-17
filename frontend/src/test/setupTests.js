import "@testing-library/jest-dom/vitest";

import { afterAll, afterEach, beforeAll } from "vitest";
import { server } from "./msw/server";

// Start MSW before all tests
beforeAll(() => server.listen({ onUnhandledRequest: "error" }));

// Reset handlers after each test (so tests don’t affect each other)
afterEach(() => server.resetHandlers());

// Clean up after all tests are done
afterAll(() => server.close());
