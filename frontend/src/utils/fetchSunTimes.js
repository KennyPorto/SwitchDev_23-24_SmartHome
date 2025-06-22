export async function fetchSunTimes(latitude, longitude) {
    const apiBaseUrl = 'http://10.9.24.170:8080';
    const groupNumber = 3;

    const fetchTime = async (option) => {
        const response = await fetch(`${apiBaseUrl}/SunriseOrSunsetTime?groupNumber=${groupNumber}&latitude=${latitude}&longitude=${longitude}&option=${option}`);
        const data = await response.json();
        if (response.ok) {
            const hours = Math.floor(data.measurement);
            const minutes = Math.floor((data.measurement - hours) * 60);
            const time = new Date();
            time.setHours(hours, minutes, 0, 0);
            return time;
        } else {
            throw new Error('Failed to fetch sun times');
        }
    };

    try {
        const [sunrise, sunset] = await Promise.all([
            fetchTime('sunrise'),
            fetchTime('sunset')
        ]);

        return {sunrise, sunset};
    } catch (error) {
        console.error(error);
        throw error;
    }
}
