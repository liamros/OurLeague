import { Button, ButtonGroup } from "@mui/material";
import StyledEngineProvider from '@mui/material/StyledEngineProvider';
import { motion } from "framer-motion";
import React, { useState } from "react";
import { connect } from "react-redux";
import { fetchHomeLineCharts } from '../../actions';
import LineChart from "./LineChart";

const HomeLineChartContainer = ({ data, fetchHomeLineCharts, selectedQueue }) => {

    const [activecharts, setActivecharts] = React.useState()

    var [selected, setSelected] = useState("Games/Minute")

    React.useEffect(() => {
        fetchHomeLineCharts()
    }, [])

    React.useEffect(() => {
        if (activecharts || !data)
            return
        const obj = {}
        Object.keys(data).forEach((key) => {
            Object.keys(data[key]).forEach((subKey) => {
                data[key][subKey].charts.forEach((e) => {
                    obj[e.id] = true
                })
            })
        })
        setActivecharts(obj)
    }, [data])

    const onClick = (e) => {
        if (selected !== e.currentTarget.id)
            setSelected(e.currentTarget.id)
    }

    const linechartCallback = (chartState) => {
        if (chartState)
            setActivecharts(chartState)
    }

    var map = null
    if (data && selectedQueue)
        map = new Map(Object.entries(data[selectedQueue]))
    var jsx = []
    if (map)
        map.forEach(((_, key) => {
            var style = styles.button
            if (key === selected)
                style = styles.selectedButton
            jsx.push(
                <Button
                    id={key}
                    style={style}
                    className="typography"
                    key={key}
                    onClick={onClick}>
                    {key}
                </Button>)
        })
        )

    const variants = {
        visible: {
            opacity: 1,
            transition: {
                delay: 2.0,
                duration: 1,
            },
        },
        hidden: { opacity: 0 },
    }

    return (
        data && selectedQueue ?
        <StyledEngineProvider injectFirst>
            <motion.div
                className={"container chart"}
                initial="hidden"
                animate="visible"
                variants={variants}
            >
                <ButtonGroup className="typography button-group" variant="contained" aria-label="outlined primary button group">
                    {jsx}
                </ButtonGroup>
                <div className="typography-title">{data[selectedQueue][selected].name}</div>
                <LineChart data={data[selectedQueue][selected]} activeCharts={activecharts} callback={linechartCallback} />
            </motion.div>
        </StyledEngineProvider> : <></>
    )

}

const styles = {
    button: {
        color: "rgb(208, 168, 92)",
        backgroundColor: "rgba(6, 28, 37)",
        borderColor: "rgb(208, 168, 92)",
    },
    selectedButton: {
        color: "rgba(6, 28, 37)",
        backgroundColor: "rgb(208, 168, 92)",
        borderColor: "rgb(208, 168, 92)",
    }
}


function mapStateToProps(state) {
    return {
        data: state.homeLineCharts.data,
        selectedQueue: state.showCaseRankings.selected,
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