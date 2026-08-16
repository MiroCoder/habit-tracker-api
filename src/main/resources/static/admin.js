function escapeHtml(value) {
    return String(value ?? "")
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

async function request(url, options) {
    const response = await fetch(url, options);

    if (!response.ok) {
        const message = (await response.text()).trim();
        throw new Error(message || `Request failed with status ${response.status}`);
    }

    if (response.status === 204) {
        return null;
    }

    return response.json();
}

function renderDailyPhrases(phrases) {
    const container = document.getElementById("adminDailyPhrases");

    if (phrases.length === 0) {
        container.innerHTML = '<p class="empty-state">No phrases found.</p>';
        return;
    }

    container.innerHTML = phrases.map(phrase => {
        const id = Number(phrase.id);
        const nextActive = !phrase.active;

        return `
            <div class="habit${phrase.active ? "" : " inactive"}">
                <div class="phrase-copy">
                    <strong>${escapeHtml(phrase.phrase)}</strong>
                    <div class="meta">${escapeHtml(phrase.author)}</div>
                    <span class="status-badge ${phrase.active ? "status-active" : "status-inactive"}">
                        ${phrase.active ? "Active" : "Inactive"}
                    </span>
                </div>
                <div class="actions">
                    <button type="button" class="button-secondary"
                            onclick="setDailyPhraseActive(${id}, ${nextActive})">
                        ${phrase.active ? "Disable" : "Enable"}
                    </button>
                    <button type="button" onclick="editDailyPhraseAdmin(${id})">Edit</button>
                    <button type="button" class="button-danger"
                            onclick="deleteDailyPhraseAdmin(${id})">Delete</button>
                </div>
            </div>
        `;
    }).join("");
}

async function loadDailyPhrasesAdmin() {
    const author = document.getElementById("authorFilter").value;
    const url = author
        ? `/daily-phrases/search?author=${encodeURIComponent(author)}`
        : "/daily-phrases";

    renderDailyPhrases(await request(url));
}

async function loadDailyPhraseCount() {
    const count = await request("/daily-phrases/count");
    document.getElementById("adminPhraseCount").textContent = count;
}

async function loadAuthorFilter() {
    const select = document.getElementById("authorFilter");
    const selectedAuthor = select.value;
    const authors = await request("/daily-phrases/authors");

    select.replaceChildren(new Option("All authors", ""));
    authors.forEach(author => select.add(new Option(author, author)));

    if (authors.includes(selectedAuthor)) {
        select.value = selectedAuthor;
    }
}

async function refreshAdmin() {
    await loadAuthorFilter();
    await Promise.all([
        loadDailyPhrasesAdmin(),
        loadDailyPhraseCount()
    ]);
}

async function addDailyPhraseAdmin() {
    const phraseInput = document.getElementById("adminPhrase");
    const authorInput = document.getElementById("adminAuthor");
    const phrase = phraseInput.value.trim();
    const author = authorInput.value.trim();

    if (!phrase) {
        phraseInput.focus();
        return;
    }

    if (!author) {
        authorInput.focus();
        return;
    }

    try {
        await request("/daily-phrases", {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({phrase, author})
        });

        phraseInput.value = "";
        authorInput.value = "";
        await refreshAdmin();
    } catch (error) {
        alert(`Failed to add phrase: ${error.message}`);
    }
}

async function editDailyPhraseAdmin(id) {
    try {
        const phrase = await request(`/daily-phrases/${id}`);
        const newPhrase = prompt("Edit phrase:", phrase.phrase);

        if (newPhrase === null) {
            return;
        }

        const newAuthor = prompt("Edit author:", phrase.author);

        if (newAuthor === null) {
            return;
        }

        if (!newPhrase.trim() || !newAuthor.trim()) {
            alert("Phrase and author are required.");
            return;
        }

        await request(`/daily-phrases/${id}`, {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                phrase: newPhrase.trim(),
                author: newAuthor.trim()
            })
        });

        await refreshAdmin();
    } catch (error) {
        alert(`Failed to update phrase: ${error.message}`);
    }
}

async function deleteDailyPhraseAdmin(id) {
    if (!confirm("Delete this phrase?")) {
        return;
    }

    try {
        await request(`/daily-phrases/${id}`, {method: "DELETE"});
        await refreshAdmin();
    } catch (error) {
        alert(`Failed to delete phrase: ${error.message}`);
    }
}

async function setDailyPhraseActive(id, active) {
    try {
        await request(`/daily-phrases/${id}/active`, {
            method: "PATCH",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({active})
        });

        await loadDailyPhrasesAdmin();
    } catch (error) {
        alert(`Failed to change phrase status: ${error.message}`);
    }
}

async function filterPhrasesByAuthor() {
    try {
        await loadDailyPhrasesAdmin();
    } catch (error) {
        alert(`Failed to filter phrases: ${error.message}`);
    }
}

refreshAdmin().catch(error => {
    document.getElementById("adminDailyPhrases").innerHTML =
        `<p class="empty-state">Failed to load phrases: ${escapeHtml(error.message)}</p>`;
});
