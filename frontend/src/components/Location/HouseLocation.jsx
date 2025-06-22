import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
import "leaflet/dist/leaflet.css";
import "../../context/HouseContext.jsx";

const HouseLocation = ({latitude, longitude, zoom}) => {
    if (!latitude || !longitude) {
        return <div>Loading map...</div>;
    }

    const position = [latitude, longitude];

    return (
        <MapContainer center={position} zoom={zoom}>
            <TileLayer
                attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Marker position={position}>
                <Popup>You are here!</Popup>
            </Marker>
        </MapContainer>
    );
};
export default HouseLocation;
