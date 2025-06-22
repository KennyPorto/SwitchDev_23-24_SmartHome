import PropTypes from "prop-types";
import styles from "./RectangularItem.module.css"

const Item = ({itemName, itemDescription, backgroundImage}) => {
    const style = backgroundImage ? {backgroundImage: `url(${backgroundImage})`} : {};
    return (
        <div className={styles.boxImage} style={style}>
            <p>
                {itemName}
                <small>{itemDescription && itemDescription}</small>
            </p>
        </div>
    )
}

Item.propTypes = {
    itemName: PropTypes.string.isRequired,
    itemDescription: PropTypes.string,
    backgroundImage: PropTypes.string,
}

export default Item