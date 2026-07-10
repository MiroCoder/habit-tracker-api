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

loadDailyPhrasesAdmin();