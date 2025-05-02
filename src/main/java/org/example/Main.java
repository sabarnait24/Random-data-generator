package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {

        String filePath = "src/main/resources/Config.yaml";

        DBConfig dbConfig = YamlLoader.loadSection(filePath, "dbConfig", DBConfig.class);

        DbClient dbClient = new DbClient(dbConfig);

        PeriodicIngestor periodicIngestor = new PeriodicIngestor(dbClient);

        periodicIngestor.makeCallPeriodically();

    }
}