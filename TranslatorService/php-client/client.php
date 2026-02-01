<?php
$url = "http://localhost:8080/translator/translate";
$textToTranslate = "Hello, how are you?";

// Initialiser cURL
$ch = curl_init($url);
curl_setopt($ch, CURLOPT_POST, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, $textToTranslate);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, ["Content-Type: text/plain"]);

$response = curl_exec($ch);
curl_close($ch);

echo "Texte original : $textToTranslate\n";
echo "Traduction : $response\n";
?>
