async function loadDailyPhrasesAdmin() {
    const response = await fetch("/daily-phrases");
    const phrases =await response.json();
    const container = document.getElementById("adminDailyPhrases");

    container.innerHTML = phrases.map(phrase =>`
                                                   <div class="habit">
                                                       <div>
                                                           <strong>${phrase.phrase}</strong>
                                                           <div class="meta">${phrase.author}</div>
                                                       </div>
                                                   </div>
                                               `).join("");
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

    const response = await fetch("/daily-phrases", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            phrase: phrase,
            author: author
        })
    });

    if (!response.ok) {
        alert("Failed to add phrase");
        return;
    }

    phraseInput.value = "";
    authorInput.value = "";

    await loadDailyPhrasesAdmin();
}

loadDailyPhrasesAdmin();

