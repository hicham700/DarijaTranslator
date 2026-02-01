// Identifiants Basic Auth
const USERNAME = "admin";
const PASSWORD = "12345";

// Encodage Base64
const AUTH_HEADER = "Basic " + btoa(USERNAME + ":" + PASSWORD);

// Bouton Traduire
document.getElementById('translateBtn').addEventListener('click', async () => {
    const text = document.getElementById('inputText').value;
    const outputDiv = document.getElementById('output');

    if (!text) {
        outputDiv.textContent = "Please enter text to translate.";
        return;
    }

    outputDiv.textContent = "Translating...";

    try {
        const response = await fetch("http://localhost:8080/translator/translate", {
            method: "POST",
            headers: {
                "Content-Type": "text/plain",
                "Authorization": AUTH_HEADER   // 🔐 ICI
            },
            body: text
        });

        if (!response.ok) {
            throw new Error("REST API error: " + response.status);
        }

        const translation = await response.text();
        outputDiv.textContent = translation;

    } catch (err) {
        console.error(err);
        outputDiv.textContent = "Error: " + err.message;
    }
});

// Bouton Lire (Text-to-Speech)
document.getElementById("speakBtn").addEventListener("click", () => {
    const text = document.getElementById("output").textContent;

    if (!text) {
        alert("No translation to read.");
        return;
    }

    const utterance = new SpeechSynthesisUtterance(text);
    utterance.lang = "ar-MA";
    speechSynthesis.cancel();
    speechSynthesis.speak(utterance);
});
