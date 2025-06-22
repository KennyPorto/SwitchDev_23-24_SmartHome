import {IconContext} from "@phosphor-icons/react";
import styles from "./IconInfo.module.css";
import PropTypes from "prop-types";

const IconInfo = ({icon, label, value}) => {
    return (
        <IconContext.Provider value={{
            weight: "duotone",
            color: "var(--primary-color)",
            size: 26
        }}>
           <section className={styles.container}>
               {icon}
               <div className={styles.info}>
                   <strong>{label}</strong>
                   {value}
               </div>
           </section>
        </IconContext.Provider>
    )
}

IconInfo.propTypes = {
    icon: PropTypes.element,
    label: PropTypes.string,
    value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
}

export default IconInfo
