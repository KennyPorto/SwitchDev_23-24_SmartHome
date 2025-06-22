import PropTypes from 'prop-types';
import styles from './sunrotation.module.css';
import useSunsetSunriseTimes from '../../hooks/useSunRotation';
import Loading from '../Loading/Loading';

const calculateSunPercentage = ({sunset, sunrise, currentTime}) => {
    const totalDaylight = sunset - sunrise;
    const timeSinceSunrise = currentTime - sunrise;
    return Math.min(Math.max(timeSinceSunrise / totalDaylight, 0), 1) * 100;
};

const calculateSunCoordinates = (sunPercentage) => {
    const t = sunPercentage / 100;
    const sunCx = calculateSunCx(t);
    const sunCy = calculateSunCy(t);
    return {sunCx, sunCy};
};

const calculateMoonPercentage = ({sunrise, sunset, currentTime}) => {
    const nextDaySunrise = new Date(sunrise);
    nextDaySunrise.setDate(nextDaySunrise.getDate() + 1);

    const totalNightTime = nextDaySunrise - sunset;
    let timeSinceSunset = currentTime - sunset;

    if (timeSinceSunset < 0) {
        timeSinceSunset += 24 * 60 * 60 * 1000;
    }

    return Math.min(Math.max(timeSinceSunset / totalNightTime, 0), 1) * 100;
};

const calculateMoonCoordinates = (moonPercentage) => {
    const t = moonPercentage / 100;
    const moonCx = calculateSunCx(t);
    const moonCy = calculateSunCy(t);
    return {moonCx, moonCy};
};

const calculateSunCy = (t) => {
    const p0 = 200;
    const p1 = 0;
    const p2 = 200;
    return (1 - t) * (1 - t) * p0 + 2 * (1 - t) * t * p1 + t * t * p2;
};

const calculateSunCx = (t) => 50 + t * 400;

const getSunColor = (percentage) => {
    const t = percentage / 100;
    const r = Math.round(255 - (255 - 253) * Math.sin(Math.PI * t));
    const g = Math.round(103 + (213 - 103) * Math.sin(Math.PI * t));
    const b = Math.round(83 + (90 - 83) * Math.sin(Math.PI * t));
    return `rgb(${r}, ${g}, ${b})`;
};

const SunRotation = ({latitude, longitude, backgroundColor}) => {
    const {
        sunrise,
        sunset,
        currentTime,
        loading
    } = useSunsetSunriseTimes(latitude, longitude);

    if (loading || !sunrise || !sunset) {
        return <Loading/>;
    }

    const sunPercentage = calculateSunPercentage({sunrise, sunset, currentTime});
    const {sunCx, sunCy} = calculateSunCoordinates(sunPercentage);
    const sunColor = getSunColor(sunPercentage);

    const moonPercentage = calculateMoonPercentage({sunrise, sunset, currentTime});
    const {moonCx, moonCy} = calculateMoonCoordinates(moonPercentage);

    const sunRadius = 20;
    const moonRadius = 20;

    return (
        <div className={styles.sunRotationContainer}
             style={{'--background-color': backgroundColor}}>
            <svg width="100%" height="150px" viewBox="0 35 500 200">
                <path id="sun-path" d="M50 200 Q250 0 450 200" fill="transparent"
                      stroke="rgba(0, 0, 0, 0.1)"/>
                {currentTime >= sunrise && currentTime < sunset ? (
                    <circle r={sunRadius} fill={sunColor} cx={sunCx} cy={sunCy}
                            className="sun"/>
                ) : (
                    <g transform={`translate(${moonCx - moonRadius}, ${moonCy - moonRadius})`}>
                        <svg
                            fill="#000000"
                            version="1.1"
                            id="Capa_1"
                            xmlns="http://www.w3.org/2000/svg"
                            xmlns:xlink="http://www.w3.org/1999/xlink"
                            width="40px"
                            height="40px"
                            viewBox="-2.13 -2.13 34.72 34.72"
                            xml:space="preserve"
                            stroke="#000000"
                            strokeWidth="0.00030457"
                        >
                            <g id="SVGRepo_bgCarrier" strokeWidth="0"></g>
                            <g id="SVGRepo_tracerCarrier" strokeLinecap="round"
                               strokeLinejoin="round" stroke="#CCCCCC"
                               strokeWidth="0.18274200000000002"></g>
                            <g id="SVGRepo_iconCarrier">
                                <g>
                                    <path
                                        d="M29.693,14.49c-0.469-0.174-1-0.035-1.32,0.353c-1.795,2.189-4.443,3.446-7.27,3.446c-5.183,0-9.396-4.216-9.396-9.397 c0-2.608,1.051-5.036,2.963-6.835c0.366-0.347,0.471-0.885,0.264-1.343c-0.207-0.456-0.682-0.736-1.184-0.684 C5.91,0.791,0,7.311,0,15.194c0,8.402,6.836,15.238,15.238,15.238c8.303,0,14.989-6.506,15.219-14.812 C30.471,15.118,30.164,14.664,29.693,14.49z"></path>
                                </g>
                            </g>
                        </svg>
                    </g>
                )}
            </svg>

            <section className={styles.labels}>
                <div>
                    <strong>Sunrise</strong>
                    <p>{sunrise ? sunrise.toLocaleTimeString([], {
                        hour: '2-digit',
                        minute: '2-digit'
                    }) : 'Loading...'}</p>
                </div>
                <div>
                    <strong>Sunset</strong>
                    <p>{sunset ? sunset.toLocaleTimeString([], {
                        hour: '2-digit',
                        minute: '2-digit'
                    }) : 'Loading...'}</p>
                </div>
            </section>
        </div>
    );
};

SunRotation.propTypes = {
    latitude: PropTypes.number.isRequired,
    longitude: PropTypes.number.isRequired,
    backgroundColor: PropTypes.string,
};

export default SunRotation;
