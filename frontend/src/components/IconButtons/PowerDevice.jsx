import {useState} from "react";
import styles from "./iconButtons.module.css";
import {Power} from "@phosphor-icons/react";
import {deactivateDevice} from "../../services/DeviceService.jsx";
import PropTypes from "prop-types";
import {useDevice} from "../../context/DeviceContext.jsx"
import {Bounce, toast, ToastContainer} from "react-toastify";

const PowerDevice = ({deviceId, isActive}) => {
    const initialState = {
        state: isActive ? "on" : "off",
        color: isActive ? "green" : "grey",
        action: isActive ? "DEACTIVATE" : "DEACTIVATED"
    }

    const [state, setState] = useState(initialState);
    const {updateDeviceStatus} = useDevice();

    const toggleLight = () => {
        if (state.state === "on") {

            const postRequest = deactivateDevice(deviceId)
            toast.promise(
                postRequest,
                {
                    pending: 'The device is being turned off...',
                    success: 'The Device has been turned off!',
                    error: 'An Error has occurred!'
                }, {
                    autoClose: 3000,
                    theme: "colored",
                    transition: Bounce,
                }).then(() => {
                updateDeviceStatus(deviceId)
                setState({
                    state: "off",
                    color: "grey",
                    action: "DEACTIVATED"
                });
            });
        }
    };

    const disabledStyle = {
        cursor: "not-allowed",
        color: "grey"
    }

    return (
        <div className={styles.buttonContainer}>
            <button id="power_button" onClick={toggleLight}
                    className={styles.buttonStyle}
                    disabled={state.state === "off"}>
                <Power weight="duotone" color={state.color} size={35}/>
            </button>
            <label htmlFor="power_button"
                   style={state.state === "off" ? disabledStyle : {}}>
                <strong>{state.action}</strong>
            </label>
            <ToastContainer className="toast-container"/>
        </div>
    );
};

PowerDevice.propTypes = {
    deviceId: PropTypes.oneOfType([
        PropTypes.string,
        PropTypes.number
    ]).isRequired,
    isActive: PropTypes.bool.isRequired
}

export default PowerDevice;