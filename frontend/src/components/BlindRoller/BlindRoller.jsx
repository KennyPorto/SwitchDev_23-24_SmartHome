import {useState} from "react";
import Form from "../Form/Form";
import styles from "./blindRoller.module.css";
import {closeBlindRoller} from "../../services/DeviceService.jsx";
import {useActuator} from "../../context/ActuatorContext.jsx";
import PropTypes from "prop-types";
import {Bounce, toast} from "react-toastify";
import {useDeviceLogs} from "../../context/LogsContext.jsx";

const BlindRoller = ({deviceId, blindRollerActuators}) => {
    const [percentage, setPercentage] = useState(100);
    const [actuator, setActuator] = useState(blindRollerActuators[0].name);
    const {actuators, getData} = useActuator();
    const {getDeviceLogs} = useDeviceLogs(deviceId);

    const handleSubmit = (event) => {
        event.preventDefault();
        const actuatorObject = blindRollerActuators.find(br => br.name === actuator);
        const executeClose = async () => {
            try {
                await closeBlindRoller(deviceId, actuatorObject.actuatorId, percentage);
                getData();
                getDeviceLogs(deviceId);
                toast.success("The blind roller has been closed/open!", {
                    autoClose: 3000,
                    theme: "colored",
                    transition: Bounce,
                    closeOnClick: true,
                    toastId: 1
                })
            } catch (err) {
                toast.error("An Error has occurred!", {
                    autoClose: 3000,
                    theme: "colored",
                    transition: Bounce
                })
            }
        }

        executeClose().then()
    };

    const handleChange = (event) => {
        setPercentage(parseInt(event.target.value));
    };

    const handleActuatorChange = (event) => {
        setActuator(event.target.value);
    };

    const handleCancelAction = () => {
        const actuatorObject = blindRollerActuators.find(br => br.name === actuator);
        setPercentage(actuatorObject.currentValue)
    };

    const dropdownOptions = blindRollerActuators.map(actuator =>
        <option value={actuator.name} key={actuator.actuatorId}
                onClick={handleActuatorChange}>{actuator.name}</option>
    )

    const currentPercentage = actuators.find(act => act.name === actuator).currentValue;

    return (
        <section className={styles.formContainer}>
            <h3 className="sideline">Configure Blind Roller</h3>
            <div>
                <label htmlFor="actuator_chooser">Choose an actuator:</label>
                <section id={"actuator_chooser"} className={styles.selectActuator}
                         onChange={handleActuatorChange}>
                    <select name="Actuator Chooser">
                        {dropdownOptions}
                    </select>
                    <p className={styles.currentActuatorPercentage}>
                        Current: {currentPercentage}%
                    </p>
                </section>
                <Form onSubmit={handleSubmit} cancelText="Reset" cancelAction={handleCancelAction} >
                    <label htmlFor="percentage">New Measurement: {percentage}% </label>
                    <div className={styles.rangeInputContainer}>
                        <input
                            type="range"
                            id="percentage"
                            name="percentage"
                            min="0"
                            max="100"
                            value={percentage}
                            onChange={handleChange}
                        />
                    </div>
                </Form>
            </div>
        </section>
    );
};

BlindRoller.propTypes = {
    deviceId: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.number
    ]).isRequired,
    blindRollerActuators: PropTypes.array
}

export default BlindRoller;