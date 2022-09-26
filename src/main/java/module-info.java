module io.assignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires static lombok;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens io.assignment to javafx.fxml;
    exports io.assignment;
    exports io.assignment.model;
    opens io.assignment.model to javafx.fxml;
    exports io.assignment.controller;
    opens io.assignment.controller to javafx.fxml;
}