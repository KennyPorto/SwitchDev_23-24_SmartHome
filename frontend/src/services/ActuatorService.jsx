import axios from "axios";
import {API_BASE_URL} from "./urls";

export const saveActuator = async (data) => {
    try {
        console.log(data);
        const response = await axios.post(`${API_BASE_URL}/actuators`, data);

        return response.data;
    } catch (error) {
        console.error("Error saving actuator", error);
        throw error;
    }
};

export const fetchActuators = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/actuators`
        );

        return response.data;
    } catch (error) {
        console.error("Error fetching list of actuators:", error);
        throw error;
    }
};