import React from "react";
import { describe, it, expect, vi } from "vitest";
import { render, screen } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import Dashboard from "../../pages/Dashboard";

// Mock Sidebar + TransactionChart so test focuses on data loading (not chart rendering)
vi.mock("../../components/Sidebar", () => ({
  default: () => <div data-testid="sidebar" />,
}));

vi.mock("../../components/TransactionChart", () => ({
  default: () => <div data-testid="chart" />,
}));

describe("Dashboard (integration)", () => {
  it("loads user + goals + transactions using API and renders welcome name", async () => {
    // Simulate logged in user
    localStorage.setItem("userId", "1");

    render(
      <MemoryRouter initialEntries={["/dashboard/1"]}>
        <Routes>
          <Route path="/dashboard/:id" element={<Dashboard />} />
        </Routes>
      </MemoryRouter>
    );

    // Welcome should eventually include first name from GET /users/:id
    expect(await screen.findByText(/Welcome to your dashboard/i)).toBeInTheDocument();
    expect(await screen.findByText(/Semyon/i)).toBeInTheDocument();

    // MSW returns one goal, so the goal type should be visible on the dashboard.
    expect(await screen.findByText("Groceries")).toBeInTheDocument();

    // And transaction merchant appears:
    expect(await screen.findByText("Publix")).toBeInTheDocument();
  });

  it("blocks unauthorized user if localStorage userId != params.id", async () => {
    localStorage.setItem("userId", "999");

    render(
      <MemoryRouter initialEntries={["/dashboard/1"]}>
        <Routes>
          <Route path="/dashboard/:id" element={<Dashboard />} />
        </Routes>
      </MemoryRouter>
    );

    expect(await screen.findByText(/Access Denied/i)).toBeInTheDocument();
  });
});
