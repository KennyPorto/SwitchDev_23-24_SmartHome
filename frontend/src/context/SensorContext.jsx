import {createContext, useContext, useEffect, useState} from "react";
import PropTypes from "prop-types";
import {fetchSensors} from "../services/SensorService.jsx";
import {fetchImages} from "../services/ImageService.jsx";

const SensorContext = createContext(null);

export const SensorProvider = ({children}) => {
    const [sensors, setSensors] = useState([]);

    const addData = async (sensor) => {
        const image = await fetchImages(sensor.name);
        const sensorWithImage = {...sensor, imageUrl: image[0]?.src?.medium}
        setSensors((oldSensors) => [...oldSensors, sensorWithImage]);
    };

    useEffect(() => {
        const getData = async () => {
            const res = await fetchSensors();
            const sensorWithImages = await Promise.all(
                res.map(async (sensor) => {
                    let images = await fetchImages(sensor.name);

                    return {...sensor, imageUrl: images[0]?.src?.medium};
                })
            );
            setSensors(sensorWithImages);
        };
        getData();
    }, []);

    return <SensorContext.Provider
        value={{sensors, addData}}>{children}</SensorContext.Provider>;
};

export function useSensor() {
    return useContext(SensorContext);
}

SensorProvider.propTypes = {
    children: PropTypes.node.isRequired,
};