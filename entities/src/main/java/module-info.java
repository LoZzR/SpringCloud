module com.entities.entities {
    requires java.persistence;
    requires spring.context;
    requires java.validation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    exports com.entities.base;
    exports com.entities.person;
    exports com.entities.detective;
}