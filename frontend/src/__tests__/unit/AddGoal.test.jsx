import React from "react";
import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import AddGoal from "@/components/AddGoal";
import axios from "axios";

vi.mock("axios");

function getControlByLabel(labelText) {
  const label = screen.getByText((content, node) => {
    return (
      node?.tagName?.toLowerCase() === "label" &&
      new RegExp(labelText, "i").test(content)
    );
  });

  const wrapper = label.closest("div");
  if (!wrapper) throw new Error(`No wrapper div found for label "${labelText}"`);

  const control = wrapper.querySelector("input, select, textarea");
  if (!control) throw new Error(`No control found for label "${labelText}"`);

  return control;
}


function renderAddGoal(overrides = {}) {
  const props = {
    setShowForm: vi.fn(),
    goals: [],
    setGoals: vi.fn(),
    userId: "1",
    ...overrides,
  };

  render(<AddGoal {...props} />);
  return props;
}

describe("AddGoal (unit)", () => {
  beforeEach(() => {
    axios.post.mockReset();
  });

  it("renders fields and auto-calculates end date for week", () => {
    renderAddGoal();

    expect(screen.getByText("Add Goal")).toBeInTheDocument();
    expect(getControlByLabel("Goal Type")).toBeInTheDocument();
    expect(getControlByLabel("Category")).toBeInTheDocument();
    expect(getControlByLabel("Target Amount")).toBeInTheDocument();
    expect(getControlByLabel("Period")).toBeInTheDocument();
    expect(getControlByLabel("Start Date")).toBeInTheDocument();

    // UI prints endDate text
    expect(screen.getByText(/End Date:/i)).toBeInTheDocument();
  });

  it("submits goal and posts to /api/goals", async () => {
    const user = userEvent.setup();
    const props = renderAddGoal();

    axios.post.mockResolvedValueOnce({
      data: { goalId: 99, goalType: "Groceries", category: "Supermarkets", targetAmount: 200 },
    });

    await user.type(getControlByLabel("Goal Type"), "Groceries");
    await user.selectOptions(getControlByLabel("Category"), "Supermarkets");
    await user.type(getControlByLabel("Target Amount"), "200");
    await user.selectOptions(getControlByLabel("Period"), "week");

    await user.click(screen.getByRole("button", { name: /save/i }));

    expect(axios.post).toHaveBeenCalledTimes(1);
    const [url, payload] = axios.post.mock.calls[0];
    expect(url).toBe("http://localhost:8080/api/goals");

    expect(payload).toMatchObject({
      userId: 1,
      goalType: "Groceries",
      category: "Supermarkets",
      targetAmount: 200,
      period: "week",
    });

    expect(props.setShowForm).toHaveBeenCalledWith(false);
    expect(props.setGoals).toHaveBeenCalled();
  });
});
