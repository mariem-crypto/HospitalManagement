package tn.hopital.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatBotController {

    @FXML
    private TextArea txtConversation;

    @FXML
    private TextField txtQuestion;

    @FXML
    private void onSend() {
        String question = txtQuestion.getText().trim();
        if (question.isEmpty()) {
            return;
        }

        txtConversation.appendText("👤 Vous : " + question + "\n");

        // Réponse simple de test
        String reponse = "Je suis un chatbot de test. Tu as dit : " + question;
        txtConversation.appendText("🤖 Bot : " + reponse + "\n\n");

        txtQuestion.clear();
    }
}
