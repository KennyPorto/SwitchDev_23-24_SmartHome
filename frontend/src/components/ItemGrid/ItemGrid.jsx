import PropTypes from "prop-types";

const ItemGrid = ({ children }) => {
	return <section className="content-grid card-grid delay-fade-in">
		{children}
	</section>;
};

ItemGrid.propTypes = {
	children: PropTypes.node,
}

export default ItemGrid;
