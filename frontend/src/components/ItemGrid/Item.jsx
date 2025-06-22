import PropTypes from "prop-types";
import {Link} from "react-router-dom";
import styles from "./Item.module.css"

const Item = ({itemName, itemDescription, navigateTo = undefined, backgroundImage}) => {
    const style = backgroundImage ? {backgroundImage: `url(${backgroundImage})`} : {};
    return (
        <Link to={navigateTo} className={styles.boxImage}
              style={style}>
            <p>
                {itemName}
                <small>{itemDescription && itemDescription}</small>
            </p>
        </Link>
    )
}

Item.propTypes = {
    itemName: PropTypes.string.isRequired,
    navigateTo: PropTypes.string,
    itemDescription: PropTypes.string,
    backgroundImage: PropTypes.string,
}

export default Item
