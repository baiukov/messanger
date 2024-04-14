module me.chat.chatclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.chat.chatclient to javafx.fxml;
    exports me.chat.chatclient;
}