import React, { useEffect, useState } from "react";
import { GoogleMap, Marker, LoadScript } from "@react-google-maps/api";
import vehicleIcon from "./vehicle.png"; // Custom vehicle icon

const containerStyle = {
  width: "100%",
  height: "400px",
};

// Center of the map, somewhere between New York and New Jersey
const center = {
  lat: 40.71836,
  lng: -74.1040,
};

const MapComponent = () => {
  const [currentLocation, setCurrentLocation] = useState(null);

  // Function to fetch current location from the backend
  const fetchLocation = () => {
    fetch("http://localhost:8080/location")
      .then((response) => response.json())
      .then((data) => {
        const [lat, lng] = data.location.split(",").map(Number);
        setCurrentLocation({ lat, lng });
      })
      .catch((error) => console.error("Error fetching location:", error));
  };

  useEffect(() => {
    // Fetch the location every second to simulate real-time movement
    const interval = setInterval(fetchLocation, 1000);
    return () => clearInterval(interval);
  }, []);

  return (
    <LoadScript googleMapsApiKey="AIzaSyALhiFrhW1sS3RYy6uLFGzpcKQmpxfpxN4">
      <GoogleMap mapContainerStyle={containerStyle} center={center} zoom={10}>
        {currentLocation && (
          <Marker
            position={currentLocation}
            icon={{
              url: vehicleIcon,
              scaledSize: new window.google.maps.Size(50, 50),
            }}
          />
        )}
      </GoogleMap>
    </LoadScript>
  );
};

export default MapComponent;