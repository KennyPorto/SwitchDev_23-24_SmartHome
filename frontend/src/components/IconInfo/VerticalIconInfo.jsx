import {IconContext} from "@phosphor-icons/react"
import styles from "./VerticalIconInfo.module.css";
import PropTypes from "prop-types";

const VerticalIconInfo = ({icon, text}) => {
    return (
        <IconContext.Provider value={{
            weight: "duotone",
            color: "var(--primary-color)",
            size: 36
        }}>
            <section className={styles.container}>
                {icon}
                {text}
            </section>
        </IconContext.Provider>
    )
}

VerticalIconInfo.propTypes = {
    icon: PropTypes.element,
    text: PropTypes.string,
}

export default VerticalIconInfo