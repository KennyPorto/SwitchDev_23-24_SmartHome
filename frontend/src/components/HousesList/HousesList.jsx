import PropTypes from 'prop-types';
import {useNavigate} from 'react-router-dom';
import styles from './HouseList.module.css';
import HouseLocation from '../../components/Location/HouseLocation';
import SunRotation from '../SunRotation/SunRotation';
import useTemperature from '../../hooks/useTemperature';
import VerticalIconInfo from "../IconInfo/VerticalIconInfo.jsx";
import {CalendarDots, MapPin, Thermometer} from "@phosphor-icons/react";
import {getCurrentDate} from "../../utils/dateFunctions.js";

const currentDate = getCurrentDate();

const HousesList = ({house}) => {
    const navigate = useNavigate();
    const temperature = useTemperature(house.latitude, house.longitude);

    const handleCardClick = () => {
        navigate(`/houses/${house.houseId}`);
    };

    return (
        <section className={styles.houseCard} onClick={handleCardClick}>
            <div className={styles.houseCardMap}>
                <HouseLocation latitude={house.latitude} longitude={house.longitude}
                               zoom={14}/>
            </div>
            <div className={styles.houseCardInfo}>
                <h3 className="sideline">My Smart Home {house.houseId}</h3>

                <div className="sidebarFlex">
                    <VerticalIconInfo icon={<MapPin/>} text={house.city}/>
                    <VerticalIconInfo icon={<CalendarDots/>} text={currentDate}/>
                    <VerticalIconInfo icon={<Thermometer/>} text={`${temperature} °C`}/>
                </div>
            </div>

            <SunRotation latitude={house.latitude} longitude={house.longitude}
                         backgroundColor="var(--box-color)"/>
        </section>
    );
};

HousesList.propTypes = {
    house: PropTypes.object.isRequired,
};

export default HousesList;
