module me.chat.chatclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jdk.jsobject;
    requires lombok;

    opens me.chat.chatclient to javafx.fxml;
    exports me.chat.chatclient;
}