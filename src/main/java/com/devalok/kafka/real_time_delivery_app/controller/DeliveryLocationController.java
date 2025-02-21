package com.devalok.kafka.real_time_delivery_app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.devalok.kafka.real_time_delivery_app.kafka.DeliveryPartnerLocationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "http://localhost:3000")
public class DeliveryLocationController {

    @Autowired
    private DeliveryPartnerLocationProducer deliveryPartnerLocationProducer;

    @GetMapping
    public ResponseEntity<Map<String, String>>  getCurrentLocation() {
        // Simulating a location between New York and New Jersey
        double nycLat = 40.7128;
        double nycLon = -74.0060;
        double njLat = 40.7357;
        double njLon = -74.1724;

        double randomLat = nycLat + Math.random() * (njLat - nycLat);
        double randomLon = nycLon + Math.random() * (njLon - nycLon);

        String location = randomLat + " , " + randomLon;

        return new ResponseEntity<>(Map.of("location", location), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity updateLocation() throws InterruptedException {

        double nycLat = Math.toRadians(40.7128);
        double nycLon = Math.toRadians(-74.0060);
        double njLat = Math.toRadians(40.7357);
        double njLon = Math.toRadians(-74.1724);

        int numPoints = 1; // Number of points to generate
        List<String> path = interpolateGreatCircle(nycLat, nycLon, njLat, njLon, numPoints);

        for (String coord : path) {
            deliveryPartnerLocationProducer.updateLocation(coord);
            Thread.sleep(1000);
        }

        return ResponseEntity.ok(String.format("Location updated"));
    }

    private static List<String> interpolateGreatCircle(double lat1, double lon1, double lat2, double lon2, int numPoints) {
        List<String> path = new ArrayList<>();
        for (int i = 0; i <= numPoints; i++) {
            double fraction = (double) i / numPoints;
            double A = Math.sin((1 - fraction) * angularDistance(lat1, lon1, lat2, lon2)) / Math.sin(angularDistance(lat1, lon1, lat2, lon2));
            double B = Math.sin(fraction * angularDistance(lat1, lon1, lat2, lon2)) / Math.sin(angularDistance(lat1, lon1, lat2, lon2));

            double x = A * Math.cos(lat1) * Math.cos(lon1) + B * Math.cos(lat2) * Math.cos(lon2);
            double y = A * Math.cos(lat1) * Math.sin(lon1) + B * Math.cos(lat2) * Math.sin(lon2);
            double z = A * Math.sin(lat1) + B * Math.sin(lat2);

            double newLat = Math.toDegrees(Math.atan2(z, Math.sqrt(x * x + y * y)));
            double newLon = Math.toDegrees(Math.atan2(y, x));

            path.add(newLat + " , " + newLon);
        }
        return path;
    }

    private static double angularDistance(double lat1, double lon1, double lat2, double lon2) {
        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;
        return 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(deltaLon / 2), 2)));
    }
}