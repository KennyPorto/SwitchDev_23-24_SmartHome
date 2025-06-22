import {useEffect, useRef, useState} from 'react';
import {fetchSunTimes} from '../utils/fetchSunTimes';

export default function useSunsetSunriseTimes(latitude, longitude) {
    const [currentTime, setCurrentTime] = useState(new Date());
    const [sunrise, setSunrise] = useState(null);
    const [sunset, setSunset] = useState(null);
    const [loading, setLoading] = useState(true);
    const intervalRef = useRef(null);

    useEffect(() => {
        const fetchSunTimesWithFallback = async () => {
            const timeout = setTimeout(() => {
                console.warn('API call timed out, using hardcoded values.');
                setHardcodedSunTimes();
            }, 1500);

            try {
                if (latitude && longitude) {
                    const { sunrise, sunset } = await fetchSunTimes(latitude, longitude);
                    clearTimeout(timeout);
                    setSunrise(sunrise);
                    setSunset(sunset);
                }
            } catch (error) {
                console.error('Error fetching sun times, using hardcoded values:', error);
                setHardcodedSunTimes();
            } finally {
                setLoading(false);
            }
        };

        fetchSunTimesWithFallback().then(r => r);
    }, [latitude, longitude]);

    useEffect(() => {
        intervalRef.current = setInterval(() => {
            setCurrentTime(new Date());
        }, 1000);

        return () => {
            clearInterval(intervalRef.current);
        };
    }, []);

    const setHardcodedSunTimes = () => {
        console.log('Using hardcoded sun times.');
        const hardcodedSunrise = new Date();
        const hardcodedSunset = new Date();
        hardcodedSunrise.setHours(7, 0, 0);
        hardcodedSunset.setHours(20, 0, 0);
        setSunrise(hardcodedSunrise);
        setSunset(hardcodedSunset);
    };

    return {
        sunrise,
        sunset,
        currentTime,
        loading,
    };
}
