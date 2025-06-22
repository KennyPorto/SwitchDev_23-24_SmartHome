import {useEffect, useState} from 'react';
import {fetchInstantaneousTemperature} from "../services/HouseService.jsx";
import {DateTime} from 'luxon';



const GROUP_NUMBER = 3;

const useTemperature = (latitude, longitude) => {
    const [temperature, setTemperature] = useState(null);

    useEffect(() => {
        const fetchTemperature = async () => {
            const localTime = DateTime.now();
            const hour = localTime.hour;
            const result = await fetchInstantaneousTemperature(GROUP_NUMBER, latitude, longitude, hour);
            setTemperature(result.measurement);
        };

        fetchTemperature();

        const interval = setInterval(fetchTemperature, 15 * 60 * 1000);

        return () => clearInterval(interval);
    }, [latitude, longitude]);

    return temperature;
};

export default useTemperature;
