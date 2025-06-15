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
    requires java.persistence;
    requires jakarta.persistence;
    requires com.sun.istack.runtime;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires net.bytebuddy;

    opens com.ibdev.boavistastorage.entity to org.hibernate.orm.core;
//    opens com.ibdev.boavistastorage to javafx.fxml;
    opens com.ibdev.view to javafx.fxml;
//    opens com.ibdev.boavistastorage.entity;

    exports com.ibdev.boavistastorage.entity;

}