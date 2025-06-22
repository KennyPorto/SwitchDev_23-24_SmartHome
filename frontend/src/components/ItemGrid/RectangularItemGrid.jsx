import PropTypes from "prop-types";

const RectangularItemGrid = ({children}) => {
    return <section className="content-grid rectangular_card_grid delay-fade-in">
        {children}
    </section>;
};

RectangularItemGrid.propTypes = {
    children: PropTypes.node,
}

export default RectangularItemGrid;