module com.ibdev.boavistastorage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.desktop;

    opens com.ibdev.boavistastorage.entity to org.hibernate.orm.core;
    opens com.ibdev.view to javafx.fxml;
    opens com.ibdev.boavistastorage.controller to javafx.fxml;

    exports com.ibdev.boavistastorage.entity;
    exports com.ibdev.boavistastorage.main;
}