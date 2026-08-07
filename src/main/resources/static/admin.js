async function loadDailyPhrasesAdmin() {
    const response = await fetch("/daily-phrases");
    const phrases =await response.json();
    const container = document.getElementById("adminDailyPhrases");

    container.innerHTML = phrases.map(phrase =>`
                                                   <div class="habit">
                                                       <div>
                                                           <strong>${phrase.phrase}</strong>
                                                           <div class="meta">${phrase.author}</div>

                                                           <button type="button" onclick="editDailyPhraseAdmin(${phrase.id})">
                                                           Edit
                                                           </button>

                                                           <button type="button" onclick="deleteDailyPhraseAdmin(${phrase.id})">
                                                               Delete
                                                           </button>
                                                       </div>
                                                   </div>
                                               `).join("");
}

async function loadDailyPhraseCount() {
    const response = await fetch("/daily-phrases/count");
    const count = await response.json();

    const adminPhraseCount = document.getElementById("adminPhraseCount");

    adminPhraseCount.textContent = count;
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
    loadDailyPhraseCount();
}

async function editDailyPhraseAdmin(id) {
    const response = await fetch(`/daily-phrases/${id}`);
    const phrase = await response.json();

    const newPhrase = prompt("Edit phrase:", phrase.phrase);
    const newAuthor = prompt("Edit author:", phrase.author);

    if (newPhrase === null || newAuthor === null) return;

    const updateResponse =  await fetch(`/daily-phrases/${id}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json"
          },
          body: JSON.stringify({
          phrase: newPhrase.trim(),
          author: newAuthor.trim()
          })
    });

    if (!updateResponse.ok) {
        alert("Failed to update phrase");
        return;
    }

    await loadDailyPhrasesAdmin();
}

async function deleteDailyPhraseAdmin(id) {
    const confirmed = confirm("Delete this phrase?")
    if (!confirmed) return;

    const response = await fetch(`/daily-phrases/${id}`, {
                                         method: "DELETE"
                                     });

    if (!response.ok) {
           alert("Failed to delete phrase");
           return;
    }

    await loadDailyPhrasesAdmin();
    loadDailyPhraseCount()

}

async function loadAuthorFilter() {
    const response = await fetch("/daily-phrases/authors");
    const authors = await response.json();
    const select = document.getElementById("authorFilter");

    select.innerHTML += authors
        .map(author => `<option value="${author}">${author}</option>`)
        .join("");

}



loadDailyPhrasesAdmin();
loadDailyPhraseCount();
loadAuthorFilter();
