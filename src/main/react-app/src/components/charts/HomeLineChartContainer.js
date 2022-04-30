import { Button, ButtonGroup } from "@mui/material";
import React from "react";
import { connect } from "react-redux";
import { fetchHomeLineCharts } from '../../actions';
import LineChart from "./LineChart";

const HomeLineChartContainer = ({ data, fetchHomeLineCharts }) => {

    React.useEffect(() => {
        fetchHomeLineCharts()
    }, [])

    var map = null
    if (data)
        map = new Map(Object.entries(data))
    var jsx = []
    if (map)
        map.forEach(((_, key) => jsx.push(<Button style={{color: "rgb(208, 168, 92)", backgroundColor: "rgba(6, 28, 37)"}} key={key}>{key}</Button>)))

    return (
        data &&
        <>
            <ButtonGroup variant="contained" aria-label="outlined primary button group">
                {jsx}
            </ButtonGroup>
            <LineChart data={data["Winrate"]} />
        </>
    )

}




function mapStateToProps(state) {
    return {
        data: state.homeLineCharts.data,
        isFetching: state.isFetching,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        fetchHomeLineCharts: () => dispatch(fetchHomeLineCharts())
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(HomeLineChartContainer)