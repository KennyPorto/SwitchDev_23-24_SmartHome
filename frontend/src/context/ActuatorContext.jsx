import {createContext, useContext, useEffect, useState} from "react";
import PropTypes from "prop-types";
import {fetchActuators} from "../services/ActuatorService.jsx";
import {fetchImages} from "../services/ImageService.jsx";

const ActuatorContext = createContext(null);

export const ActuatorProvider = ({children}) => {
    const [actuators, setActuators] = useState([]);

    const getData = async () => {
        const res = await fetchActuators();
        const actuatorsWithImages = await Promise.all(
            res.map(async (actuator) => {
                let images = await fetchImages(actuator.name);

                return {...actuator, imageUrl: images[0]?.src?.medium};
            })
        );
        setActuators(actuatorsWithImages);
    };

    const addData = async (actuator) => {
        const image = await fetchImages(actuator.name);
        const sensorWithImage = {...actuator, imageUrl: image[0]?.src?.medium}
        setActuators((oldActuators) => [...oldActuators, sensorWithImage]);
    };

    useEffect(() => {
        getData();
    }, []);

    return <ActuatorContext.Provider value={{actuators, addData, getData}}>
                {children}
            </ActuatorContext.Provider>;
};

export function useActuator() {
    return useContext(ActuatorContext);
}

ActuatorProvider.propTypes = {
    children: PropTypes.node.isRequired,
};