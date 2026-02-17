import { http, HttpResponse } from "msw";

export const handlers = [
  // Add single transaction
  http.post("http://localhost:8080/transactions/add", async ({ request }) => {
    const body = await request.json();
    return HttpResponse.json(
      { ...body, id: 999 }, // backend might return created entity; we simulate it
      { status: 201 }
    );
  }),

  // Bulk upload transactions
  http.post("http://localhost:8080/transactions/bulk", async ({ request }) => {
    const body = await request.json();
    // Return what backend would return: saved transactions (with IDs)
    const saved = body.map((t, idx) => ({ ...t, id: 1000 + idx }));
    return HttpResponse.json(saved, { status: 200 });
  }),

  // Add goal
  http.post("http://localhost:8080/api/goals", async ({ request }) => {
    const body = await request.json();
    return HttpResponse.json(
      { ...body, goalId: 777 },
      { status: 201 }
    );
  }),

  // Dashboard fetches user
  http.get("http://localhost:8080/users/:id", ({ params }) => {
    return HttpResponse.json({
      id: Number(params.id),
      firstName: "Semyon",
      lastName: "Tsyrenov",
      email: "tsyrenov0209@gmail.com",
      phone: "4704425949",
    });
  }),

  // Dashboard fetches transactions
  http.get("http://localhost:8080/transactions/:id", ({ params }) => {
    return HttpResponse.json([
      {
        id: 1,
        userId: Number(params.id),
        merchant: "Publix",
        category: "Supermarkets",
        transactionDate: new Date("2026-02-01").toISOString(),
        amount: -25.5,
      },
    ]);
  }),

  // Dashboard fetches goals
  http.get("http://localhost:8080/api/goals/:id", ({ params }) => {
    return HttpResponse.json([
      {
        goalId: 10,
        userId: Number(params.id),
        goalType: "Groceries",
        category: "Supermarkets",
        targetAmount: 200,
        period: "month",
        startDate: "2026-02-01",
        endDate: "2026-03-01",
      },
    ]);
  }),
];
