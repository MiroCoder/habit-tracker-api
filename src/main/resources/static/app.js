async function loadHabits() {
    const response = await fetch("/habits");
    const habits = await response.json();

    const container = document.getElementById("habits");
    container.innerHTML = "";

    habits.forEach((habit, index) => {
        const div = document.createElement("div");
        div.className = "habit";

        div.innerHTML = `
            <div>
                <strong class="${habit.completed ? "done" : ""}">
                    ${habit.name}
                </strong>
                <div>#${index + 1} | ${habit.priority} | completed: ${habit.completed}</div>
            </div>

            <div class="actions">
                <button onclick="completeHabit(${habit.id})">Done</button>
                <button onclick="deleteHabit(${habit.id})">Delete</button>
            </div>
        `;

        container.appendChild(div);
    });
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

    await fetch("/habits", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            completed: false,
            priority: priority
        })
    });

    document.getElementById("habitName").value = "";

    await refresh();
}

async function completeHabit(id) {
    await fetch(`/habits/${id}/complete`, {
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


async function refresh() {
    await loadHabits();
    await loadStats();
    await loadNotCompletedHabits();
}

refresh();