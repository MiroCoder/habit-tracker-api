async function loadHabits() {
    const response = await fetch("/habits");
    const habits = await response.json();

    const container = document.getElementById("habits");
    container.innerHTML = "";

    for (const [index, habit] of habits.entries()) {
        const div = document.createElement("div");
        div.className = habit.requiredToday ? "habit required" : "habit";

        let currentStreak = 0;

        try {
            const streakResponse = await fetch(`/habits/${habit.id}/streak`);
            const streakData = await streakResponse.json();
            currentStreak = streakData.currentStreak;
        } catch (error) {
            console.error("Failed to load streak", error);
        }

        let streakText;

        if (currentStreak === 0) {
            streakText = "No streak yet";
        } else if (currentStreak === 1) {
            streakText = "🔥 1 day";
        } else {
            streakText = `🔥 ${currentStreak} days`;
        }

        div.innerHTML = `
            <div>
                <strong class="${habit.completed ? "done" : ""}">
                    ${habit.name}
                    ${habit.requiredToday ? "🔥 " : ""}

                </strong>
                <div>#${index + 1} | ${habit.priority} | completed: ${habit.completed}</div>
                <div>${streakText}</div>
            </div>

            <div class="actions">
                <button onclick="completeHabit(${habit.id})">Done</button>
                <button onclick="uncompleteHabit(${habit.id})">Undo</button>
                <button onclick="deleteHabit(${habit.id})">Delete</button>
            </div>
        `;

        container.appendChild(div);
    }
}

let systemTime;

async function loadSystemTime() {
    const response = await fetch("/system/time");
    const data = await response.json();

    systemTime = new Date(data.currentMillis);

    renderSystemTime();

    setInterval(() => {
        systemTime = new Date(systemTime.getTime() + 1000);
        renderSystemTime();
    }, 1000);
}

function renderSystemTime() {
    document.getElementById("systemTime").textContent =
        systemTime.toLocaleString();
}

async function loadStats() {
    const response = await fetch("/habits/stats");
    const stats = await response.json();

    document.getElementById("stats").innerHTML = `
        Total: ${stats.total}<br>
        Completed: ${stats.completed}<br>
        Not completed: ${stats.notCompleted}<br>
        Progress: ${stats.percent.toFixed(1)}%<br>
        Day type: ${stats.dayType}
    `;
}

async function addHabit() {
    const name = document.getElementById("habitName").value;
    const priority = document.getElementById("habitPriority").value;
    const requiredToday = document.getElementById("habitRequiredToday").checked;
    await fetch("/habits", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            completed: false,
            priority: priority,
            requiredToday: requiredToday
        })
    });

    document.getElementById("habitName").value = "";
    document.getElementById("habitRequiredToday").checked = false;

    await refresh();
}

async function completeHabit(id) {
    await fetch(`/habits/${id}/complete`, {
        method: "PATCH"
    });

    await refresh();
}

async function uncompleteHabit(id) {
    await fetch(`/habits/${id}/uncomplete`, {
        method: "PATCH"
    });

    await refresh();
}

async function deleteHabit(id) {
    await fetch(`/habits/${id}`, {
        method: "DELETE"
    });

    await refresh();
}


async function loadNotCompletedHabits() {
    const response = await fetch("/habits/not-completed");
    const habits = await response.json();

    const container = document.getElementById("notCompletedHabits");
    container.innerHTML = "";

    habits.forEach((habit, index) => {
        const div = document.createElement("div");
        div.className = "habit";

        div.innerHTML = `
            <div>
                <strong>${habit.name}</strong>
                <div>#${index + 1} | ${habit.priority} | completed: ${habit.completed}</div>
            </div>

            <div class="actions">
                <button onclick="completeHabit(${habit.id})">Done</button>
            </div>
        `;


        container.appendChild(div);
       });
}



async function loadHistoryStats() {
    const response = await fetch("/stats/history");
        const history = await response.json();
        const visibleHistory = history.slice(0, 7);

        const container = document.getElementById("historyStats");
        container.innerHTML = "";

        visibleHistory.forEach(day => {
            const div = document.createElement("div");
            div.className = "habit";

            div.innerHTML = `
                <div>
                    <strong>${day.statDate}</strong>
                    <div>
                        Total: ${day.total} |
                        Completed: ${day.completed} |
                        Not completed: ${day.notCompleted} |
                        ${day.percent.toFixed(1)}% |
                        ${day.dayType}
                    </div>
                </div>
            `;

            container.appendChild(div);
        });
}

async function loadWeeklySummary() {
    const response = await fetch("/stats/summary?days=7");
    const summary = await response.json();

    document.getElementById("weeklySummary").innerHTML = `
        Days recorded: ${summary.days}<br>
        Average: ${summary.averagePercent.toFixed(1)}%<br>
        Perfect days: ${summary.perfectDays}<br>
        Strong days: ${summary.strongDays}<br>
        System days: ${summary.systemDays}<br>
        Recovery days: ${summary.recoveryDays}<br>
        Zero days: ${summary.zeroDays}
    `;
}

async function loadDaysSince() {
    const response = await fetch("/days-since");
    const items = await response.json();

    const container = document.getElementById("daysSince");
    container.innerHTML = "";

    items.forEach(item => {
        const div = document.createElement("div");
        div.className = "habit";



        div.innerHTML = `
            <div>
                <strong>${item.name}</strong>
                <div>${item.daysCount} days</div>
            </div>

            <div class="actions">
                <input
                    type="date"
                    value="${new Date().toISOString().split("T")[0]}"
                    max="${new Date().toISOString().split("T")[0]}"
                    onchange="updateDaysSinceStartDate(${item.id}, this.value)"
                >
                <button onclick="deleteDaysSince(${item.id})">Delete</button>
            </div>
            `;

        container.appendChild(div);
    });
}

async function deleteDaysSince(id) {
     if (!confirm("Delete this counter?")) {
            return;
        }

    await fetch(`/days-since/${id}`, {
        method: "DELETE"
    });



    await refresh();
}

async function addDaysSince() {
    const name = document.getElementById("daysSinceName").value;
    const startDate = document.getElementById("daysSinceStartDate").value;
    await fetch("/days-since", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            startDate: startDate
        })
    });

    document.getElementById("daysSinceName").value = "";

    await refresh();
}

async function updateDaysSinceStartDate(id, startDate) {
    if (!startDate) {
        return;
    }

    await fetch(`/days-since/${id}/start-date`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            startDate: startDate
        })
    });

    await refresh();
}

async function loadDayStatus() {
    const response = await fetch("/system/day-status");
    const status = await response.text();

    document.getElementById("dayStatus").textContent = status;
    }


async function refresh() {
    await loadHabits();
    await loadStats();
    await loadNotCompletedHabits();
    await loadHistoryStats();
    await loadWeeklySummary();
    await loadDaysSince();
    await loadDayStatus();
}

loadSystemTime();
refresh();