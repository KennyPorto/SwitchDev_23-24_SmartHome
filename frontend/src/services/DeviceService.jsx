import axios from "axios";
import {API_BASE_URL} from "./urls";

export const fetchDevices = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/devices`);

        return response.data;
    } catch (error) {
        console.error("Error fetching list of devices:", error);
        throw error;
    }
};

export const deactivateDevice = async (deviceId) => {
    try {
        const response = await axios.patch(`${API_BASE_URL}/devices/${deviceId}/deactivate`);
        return response.data;
    } catch (error) {
        console.error("Error deactivating device:", error);
        throw error;
    }
};

export const closeBlindRoller = async (deviceId, actuatorId, measurement) => {
    try {
        const response = await axios
            .patch(`${API_BASE_URL}/devices/${deviceId}/close-blinder`, {
                actuatorId: actuatorId,
                measurement: measurement
            });

        return response.data;
    } catch (error) {
        console.error("Error closing blind roller:", error);
        throw error;
    }
};

export const saveDevice = async (data) => {
    try {
        const response = await axios.post(`${API_BASE_URL}/devices`, data);
        return response.data;
    } catch (error) {
        console.error(error.response.data)
        throw error
    }
};