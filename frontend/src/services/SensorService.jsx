import axios from "axios";
import {API_BASE_URL} from "./urls";


export const saveSensor = async (data) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/sensors`, data);

        return response.data;
    } catch (error) {
        console.error("Error saving sensor", error);
        throw error;
    }
};

export const fetchSensors = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/sensors`
        );

        return response.data;
    } catch (error) {
        console.error("Error fetching list of sensors:", error);
        throw error;
    }
};