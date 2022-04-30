import { Container } from "@nivo/core";
import React from "react";
import LineChart from "./LineChart";
import { Button, ButtonGroup } from "@mui/material";
import { fetchHomeLineCharts } from '../../actions';

const HomeLineChartContainer = ({ data }) => {

    React.useEffect(() => {
        fetchHomeLineCharts()
    }, [])

    return (
        data &&
        <Container>
            <ButtonGroup variant="contained" aria-label="outlined primary button group">
                {
                    Array.from(new Map(Object.entries(data))).map((element => {
                        <Button id={element[0]}>{element[0]}</Button>
                    }))
                }
            </ButtonGroup>
            <LineChart data={data["Winrate"]} />
        </Container>
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