import styles from "./iconButtons.module.css";
import {Notebook} from "@phosphor-icons/react";
import {useNavigate} from "react-router-dom";

const SeeLogsButton = () => {

    const redirect = useNavigate();

    return (
        <div className={styles.buttonContainer}>
            <button id="logs_button" onClick={() => redirect("logs")}
                    className={styles.buttonStyle}>
                <Notebook weight="duotone" size={35} color={"var(--primary-color)"}/>
            </button>
            <label htmlFor="logs_button">
                <strong>DEVICE LOGS</strong>
            </label>
        </div>
    );
};

export default SeeLogsButton;