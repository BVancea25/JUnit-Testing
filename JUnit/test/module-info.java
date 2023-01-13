module test {
    requires main;
    requires junit;
    requires org.junit.jupiter.api;
    requires java.sql;
    exports com.test to junit;
    opens com.test to org.junit.platform.commons;


}