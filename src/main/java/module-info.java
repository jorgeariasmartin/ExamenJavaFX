module controllers.examen {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens controladores to javafx.fxml;
    opens modelos to javafx.base;
    exports controladores;
}