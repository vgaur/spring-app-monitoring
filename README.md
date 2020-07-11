# spring-app-monitoring
Monitor Spring apps

## Capture metrics and health of the application and expose metrics which can be visualized using visualization tool

# How to run the application

1. sh ./start.sh
   This will create three docker containers , one for application, one for prometheus and one for grafana.
   
   i. app-monitor
   ii. monitoring_prometheus
   iii. monitoring_grafana
   
Once the application is up , you can check the status by visiting this url http://localhost:8061/

Now we can go to prometheus and check if the target is setup and is ready.

Go to http://localhost:9090/targets/ , you should see a target status as up with url (http://app-monitor:8061/)

Now we have to go to Grafana and add a dashboard to start visualizing the stats.

Go to http://localhost:3000 , login using admin/admin.

Add this dashboard is already there you will have to use import option. https://grafana.com/grafana/dashboards/4701

Now you can see the application status.

Let's run some test and see the graphs.

1. http://localhost:8061/loadfile/small
2. http://localhost:8061/loadfile/large
3. http://localhost:8061/loadfile/camel/small
4. http://localhost:8061/loadfile/camel/large


You can click on each link , this will in turn load a csv file in embedded nitrate DB. You can monitor the stats.

# How to stop the application

1. sh ./stop.sh
