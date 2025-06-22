import PropTypes from "prop-types";
import styles from "./Pill.module.css"

const Pill = ({children, altStyle = false}) => {
    return (
        <div className={`${styles.pill} + ${altStyle ? styles.alt : ""}`}>
            {children}
        </div>
    )
}
export default Pill

Pill.propTypes = {
    altStyle: PropTypes.bool,
    children: PropTypes.node
}