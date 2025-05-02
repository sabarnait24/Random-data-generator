package org.example;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Data
public class PeriodicIngestor {
    private DbClient dbClient;

    public PeriodicIngestor( DbClient dbClient ){
         this.dbClient = dbClient;

    }

    public void makeCallPeriodically() throws Exception {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(() -> {

            try{
                dbClient.dbConnection();

                List<Map<String, Object>> records = new MockerooClient().fetchMockData(10);

                dbClient.insertData(records, "users");

                System.out.println("Data inserted successfully.");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }, 0, 1, TimeUnit.MINUTES);


    }


}
