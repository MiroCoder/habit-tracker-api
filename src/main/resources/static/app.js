function escapeHtml(value) {
    return String(value)
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function formatDate(dateValue) {
    return new Intl.DateTimeFormat("en", {
        day: "2-digit",
        month: "short",
        year: "numeric"
    }).format(new Date(`${dateValue}T00:00:00`));
}

function renderEmpty(container, message) {
    container.innerHTML = `<p class="empty-state">${message}</p>`;
}

async function loadHabits() {
    const response = await fetch("/habits");
    const habits = await response.json();
    const container = document.getElementById("habits");

    if (habits.length === 0) {
        renderEmpty(container, "No habits yet.");
        return;
    }

    const habitsWithStreaks = await Promise.all(
        habits.map(async habit => {
            try {
                const streakResponse = await fetch(`/habits/${habit.id}/streak`);
                const streakData = await streakResponse.json();
                return {...habit, currentStreak: streakData.currentStreak};
            } catch (error) {
                console.error("Failed to load streak", error);
                return {...habit, currentStreak: 0};
            }
        })
    );

    container.innerHTML = habitsWithStreaks.map((habit, index) => {
        const streakText = habit.currentStreak === 1
            ? "1 day streak"
            : `${habit.currentStreak} day streak`;

        return `
            <div class="habit${habit.requiredToday ? " required" : ""}">
                <div>
                    <strong class="habit-name${habit.completed ? " done" : ""}">
                        ${escapeHtml(habit.name)}
                    </strong>
                    <div class="meta">
                        #${index + 1} · ${escapeHtml(habit.priority)} · ${streakText}
                        ${habit.requiredToday ? " · Must today" : ""}
                    </div>
                </div>

                <div class="actions">
                    <button type="button" onclick="completeHabit(${habit.id})" ${habit.completed ? "disabled" : ""}>Done</button>
                    <button type="button" class="button-secondary" onclick="uncompleteHabit(${habit.id})" ${habit.completed ? "" : "disabled"}>Undo</button>
                    <button type="button" class="button-danger" onclick="deleteHabit(${habit.id})">Delete</button>
                </div>
            </div>
        `;
    }).join("");
}

let systemTime;
let systemTimeInterval;

async function loadSystemTime() {
    const response = await fetch("/system/time");
    const data = await response.json();

    systemTime = new Date(data.currentMillis);
    renderSystemTime();

    clearInterval(systemTimeInterval);
    systemTimeInterval = setInterval(() => {
        systemTime = new Date(systemTime.getTime() + 1000);
        renderSystemTime();
    }, 1000);
}

function renderSystemTime() {
    document.getElementById("systemTime").textContent = systemTime.toLocaleString();
}

async function loadStats() {
    const response = await fetch("/habits/stats");
    const stats = await response.json();
    const percent = Number(stats.percent.toFixed(1));

    document.getElementById("stats").innerHTML = `
        <div class="stat-row"><span>Total</span><strong>${stats.total}</strong></div>
        <div class="stat-row"><span>Completed</span><strong>${stats.completed}</strong></div>
        <div class="stat-row"><span>Not completed</span><strong>${stats.notCompleted}</strong></div>
        <div class="stat-row"><span>Progress</span><strong>${percent}%</strong></div>
        <div class="stat-row"><span>Day type</span><strong>${escapeHtml(stats.dayType)}</strong></div>
    `;

    document.getElementById("progressBar").style.width = `${Math.min(100, Math.max(0, percent))}%`;
}

async function addHabit() {
    const nameInput = document.getElementById("habitName");
    const name = nameInput.value.trim();

    if (!name) {
        nameInput.focus();
        return;
    }

    const priority = document.getElementById("habitPriority").value;
    const requiredToday = document.getElementById("habitRequiredToday").checked;

    await fetch("/habits", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            name,
            completed: false,
            priority,
            requiredToday
        })
    });

    nameInput.value = "";
    document.getElementById("habitRequiredToday").checked = false;
    await refresh();
}

async function completeHabit(id) {
    await fetch(`/habits/${id}/complete`, {method: "PATCH"});
    await refresh();
}

async function uncompleteHabit(id) {
    await fetch(`/habits/${id}/uncomplete`, {method: "PATCH"});
    await refresh();
}

async function deleteHabit(id) {
    if (!confirm("Delete this habit?")) {
        return;
    }

    await fetch(`/habits/${id}`, {method: "DELETE"});
    await refresh();
}

async function loadNotCompletedHabits() {
    const response = await fetch("/habits/not-completed");
    const habits = await response.json();
    const container = document.getElementById("notCompletedHabits");

    if (habits.length === 0) {
        renderEmpty(container, "All habits are completed.");
        return;
    }

    container.innerHTML = habits.map((habit, index) => `
        <div class="habit${habit.requiredToday ? " required" : ""}">
            <div>
                <strong class="habit-name">${escapeHtml(habit.name)}</strong>
                <div class="meta">#${index + 1} · ${escapeHtml(habit.priority)}</div>
            </div>
            <div class="actions">
                <button type="button" onclick="completeHabit(${habit.id})">Done</button>
            </div>
        </div>
    `).join("");
}

async function loadHistoryStats() {
    const response = await fetch("/stats/history");
    const history = await response.json();
    const visibleHistory = history.slice(0, 7);
    const container = document.getElementById("historyStats");

    if (visibleHistory.length === 0) {
        renderEmpty(container, "No daily records yet.");
        return;
    }

    container.innerHTML = visibleHistory.map(day => `
        <div class="record">
            <div>
                <strong class="record-date">${formatDate(day.statDate)}</strong>
                <div class="meta">${day.completed}/${day.total} completed</div>
            </div>
            <div class="record-result">
                <strong>${day.percent.toFixed(1)}%</strong><br>
                ${escapeHtml(day.dayType)}
            </div>
        </div>
    `).join("");
}

async function loadWeeklySummary() {
    const response = await fetch("/stats/summary?days=7");
    const summary = await response.json();

    const summaryRows = [
        ["Days recorded", summary.days],
        ["Average", `${summary.averagePercent.toFixed(1)}%`],
        ["Perfect days", summary.perfectDays],
        ["Strong days", summary.strongDays],
        ["System days", summary.systemDays],
        ["Recovery days", summary.recoveryDays],
        ["Zero days", summary.zeroDays]
    ];

    document.getElementById("weeklySummary").innerHTML = summaryRows
        .map(([label, value]) => `
            <div class="summary-row"><span>${label}</span><strong>${value}</strong></div>
        `)
        .join("");
}

async function loadDaysSince() {
    const response = await fetch("/days-since");
    const items = await response.json();
    const container = document.getElementById("daysSince");

    if (items.length === 0) {
        renderEmpty(container, "No counters yet.");
        return;
    }

    const today = new Date().toISOString().split("T")[0];

    container.innerHTML = items.map(item => {
        const daysLabel = item.daysCount === 1 ? "day" : "days";

        return `
        <div class="counter">
            <div>
                <strong class="counter-name">${escapeHtml(item.name)}</strong>
                <span class="counter-days">${item.daysCount} ${daysLabel}</span>
            </div>

            <div class="actions">
                <input
                    type="date"
                    value="${item.startDate}"
                    max="${today}"
                    aria-label="Start date for ${escapeHtml(item.name)}"
                    onchange="updateDaysSinceStartDate(${item.id}, this.value)"
                >
                <button type="button" class="button-secondary" onclick="resetDaysSinceToday(${item.id})">Reset today</button>
                <button type="button" class="button-danger" onclick="deleteDaysSince(${item.id})">Delete</button>
            </div>
        </div>
    `;
    }).join("");
}

async function resetDaysSinceToday(id) {
    const today = new Date().toISOString().split("T")[0];
    await updateDaysSinceStartDate(id, today);
}

async function deleteDaysSince(id) {
    if (!confirm("Delete this counter?")) {
        return;
    }

    await fetch(`/days-since/${id}`, {method: "DELETE"});
    await refresh();
}

async function addDaysSince() {
    const nameInput = document.getElementById("daysSinceName");
    const dateInput = document.getElementById("daysSinceStartDate");
    const name = nameInput.value.trim();
    const startDate = dateInput.value;

    if (!name) {
        nameInput.focus();
        return;
    }

    if (!startDate) {
        dateInput.focus();
        return;
    }

    await fetch("/days-since", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({name, startDate})
    });

    nameInput.value = "";
    await refresh();
}

async function updateDaysSinceStartDate(id, startDate) {
    if (!startDate) {
        return;
    }

    await fetch(`/days-since/${id}/start-date`, {
        method: "PATCH",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({startDate})
    });

    await refresh();
}

async function loadDayStatus() {
    const response = await fetch("/system/day-status");
    document.getElementById("dayStatus").textContent = await response.text();
}

async function refresh() {
    await Promise.all([
        loadHabits(),
        loadStats(),
        loadNotCompletedHabits(),
        loadHistoryStats(),
        loadWeeklySummary(),
        loadDaysSince(),
        loadDayStatus()
    ]);
}

document.getElementById("daysSinceStartDate").max = new Date().toISOString().split("T")[0];
document.getElementById("habitName").addEventListener("keydown", event => {
    if (event.key === "Enter") {
        addHabit();
    }
});

loadSystemTime();
refresh();
