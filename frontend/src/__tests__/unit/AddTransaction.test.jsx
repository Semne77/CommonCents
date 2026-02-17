import React, { useState } from "react";
import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import AddTransaction from "@/components/AddTransaction";
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

function renderAddTransaction(overrides = {}) {
  const defaultProps = {
    setShowForm: vi.fn(),
    transactions: [{ merchant: "Publix" }, { merchant: "Target" }],
    setTransactions: vi.fn(),
    incomeCategories: ["Wages", "Other Income"],
    expenseCategories: ["Supermarkets", "Rent"],
    userId: "1",
  };

  const initialFormData = overrides.formData ?? {
    type: "Expense",
    merchant: "",
    category: "",
    transactionDate: "2026-02-17",
    amount: "",
  };

  const Wrapper = () => {
    const [formData, setFormData] = useState(initialFormData);

    return (
      <AddTransaction
        {...defaultProps}
        {...overrides}
        formData={formData}
        setFormData={setFormData}
      />
    );
  };

  render(<Wrapper />);
  return { ...defaultProps, ...overrides };
}

describe("AddTransaction (unit)", () => {
  beforeEach(() => {
    axios.post.mockReset();
  });

  it("renders required fields", () => {
    renderAddTransaction();

    expect(screen.getByText("Add Transaction")).toBeInTheDocument();
    expect(getControlByLabel("Merchant")).toBeInTheDocument();
    expect(getControlByLabel("Category")).toBeInTheDocument();
    expect(getControlByLabel("Date")).toBeInTheDocument();
    expect(getControlByLabel("Amount")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: /save/i })).toBeInTheDocument();
  });

  it("shows expense categories by default, switches to income categories", async () => {
    const user = userEvent.setup();
    renderAddTransaction();

    // Expense default
    expect(screen.getByRole("option", { name: "Supermarkets" })).toBeInTheDocument();
    expect(screen.queryByRole("option", { name: "Wages" })).not.toBeInTheDocument();

    // Switch to Income
    await user.click(screen.getByRole("radio", { name: /income/i }));

    // Wait for rerender
    expect(await screen.findByRole("option", { name: "Wages" })).toBeInTheDocument();
    expect(screen.queryByRole("option", { name: "Supermarkets" })).not.toBeInTheDocument();
  });

  it("submits and posts a NEGATIVE amount for Expense", async () => {
    const user = userEvent.setup();
    const props = renderAddTransaction();

    axios.post.mockResolvedValueOnce({ data: {} });

    await user.type(getControlByLabel("Merchant"), "Publix");
    await user.selectOptions(getControlByLabel("Category"), "Supermarkets");

    // Explicitly set date (required)
    await user.clear(getControlByLabel("Date"));
    await user.type(getControlByLabel("Date"), "2026-02-17");

    await user.clear(getControlByLabel("Amount"));
    await user.type(getControlByLabel("Amount"), "12.50");

    await user.click(screen.getByRole("button", { name: /save/i }));

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledTimes(1);
    });

    const [url, payload] = axios.post.mock.calls[0];
    expect(url).toBe("http://localhost:8080/transactions/add");

    expect(payload).toMatchObject({
      userId: 1,
      merchant: "Publix",
      category: "Supermarkets",
      amount: -12.5,
    });

    // closes modal
    expect(props.setShowForm).toHaveBeenCalledWith(false);
  });

  it("bulk upload parses TSV and posts to /transactions/bulk", async () => {
    renderAddTransaction();

    const tsv = `10\t2026-02-01\tPublix\tSupermarkets
-5\t2026-02-02\tTarget\tMerchandise`;

    const file = new File([tsv], "tx.tsv", { type: "text/tab-separated-values" });

    axios.post.mockResolvedValueOnce({
      data: [
        { userId: 1, amount: 10, merchant: "Publix", category: "Supermarkets" },
        { userId: 1, amount: -5, merchant: "Target", category: "Merchandise" },
      ],
    });

    const fileInput = document.querySelector('input[type="file"]');
    expect(fileInput).toBeTruthy();

    fireEvent.change(fileInput, { target: { files: [file] } });

    await waitFor(() => {
      expect(axios.post).toHaveBeenCalledTimes(1);
    });

    const [url, payload] = axios.post.mock.calls[0];
    expect(url).toBe("http://localhost:8080/transactions/bulk");

    expect(payload).toHaveLength(2);
    expect(payload[0]).toMatchObject({
      userId: 1,
      merchant: "Publix",
      category: "Supermarkets",
      amount: 10,
    });
  });
});


