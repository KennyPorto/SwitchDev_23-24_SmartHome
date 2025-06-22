import {createContext, useContext, useEffect, useState} from "react";
import PropTypes from "prop-types";
import {fetchDevices} from "../services/DeviceService";
import {fetchImages} from "../services/ImageService";

const DeviceContext = createContext(null);

export const DeviceProvider = ({children}) => {
    const [devices, setDevices] = useState([]);

    const addData = async (device) => {

        let images = await fetchImages(device.deviceName);

        setDevices((oldDev) => [...oldDev, {
            ...device,
            imageUrl: images[0]?.src?.medium
        }]);
    };

    const updateDeviceStatus = (deviceId) => {
        setDevices(devices.map((device) =>
             device.deviceID.toString() === deviceId ? {...device, isActive : false} : device) 
        )
    }
    
    useEffect(() => {
        const getData = async () => {
            const res = await fetchDevices();
            setDevices(res);
        };
        getData().then();
    }, []);

    useEffect(() => {
        const getData = async () => {
            const res = await fetchDevices();
            const devicesWithImages = await Promise.all(
                res.map(async (device) => {
                    let images = await fetchImages(device.deviceName);

                    return {...device, imageUrl: images[0]?.src?.medium};
                })
            );
            setDevices(devicesWithImages);
        };
        getData().then();
    }, []);

    return (
        <DeviceContext.Provider value={{devices, addData, updateDeviceStatus}}>
            {children}
        </DeviceContext.Provider>
    );
};

export function useDevice() {
    return useContext(DeviceContext);
}

DeviceProvider.propTypes = {
    children: PropTypes.node.isRequired,
};

