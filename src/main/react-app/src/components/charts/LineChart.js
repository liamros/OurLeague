import { ResponsiveLine } from '@nivo/line';
import React, { useState } from 'react';
import { connect } from "react-redux";
import { fetchWrLineChart } from '../../actions';

// make sure parent container have a defined height when using
// responsive component, otherwise height will be 0 and
// no chart will be rendered.
// website examples showcase many properties,
// you'll often use just a few of them.
const LineChart = ({ wrLineChart, fetchWrLineChart }) => {

    const [chartready, setChartready] = useState(false)
    const [allpressed, setAllpressed] = useState(false)
    const [chart, setChart] = useState(wrLineChart)

    if (wrLineChart && !chartready) {
        let allExists = false
        wrLineChart.forEach(element => {
            if (element["id"] === "ALL")
                allExists = true
            element["active"] = true
        });
        if (!allExists)
            wrLineChart.push({ id: "ALL", data: [] })
        setChart(wrLineChart)
        setChartready(true)
    }



    React.useEffect(() => {
        fetchWrLineChart()
    }, [])

    const foo = (e) => {
        var a = JSON.parse(JSON.stringify(chart))
        if (e.id === "ALL") {
            if (!allpressed) {
                a.forEach((elem) => {
                    elem.data.forEach((d) => d.y = 0)
                    elem.active = false
                })
            } else {
                a = JSON.parse(JSON.stringify(wrLineChart))
            }
            setAllpressed(!allpressed)
            setChart(a)
            return
        }
        var idx = 0
        while (chart[idx].id != e.id) {
            idx++
        }
        if (a[idx].active) {
            a[idx].data.forEach((d) => d.y = 0)
            a[idx].active = false
        }
        else {
            a[idx].data = wrLineChart[idx].data
            a[idx].active = true
        }

        setChart(a)
        console.log(
            chart
        );
    }

    return (
        chart && <div style={{ height: "100%", /*border: "3px solid rgb(208, 168, 92)", borderRadius: "3px", background: "rgb(6, 28, 37)"*/ }}>


            <ResponsiveLine
                data={chart}
                margin={{ top: 50, right: 110, bottom: 50, left: 60 }}
                theme={{ /*background: "rgb(6, 28, 37)",*/ textColor: "rgb(208, 168, 92)" }}
                xScale={{
                    type: 'point',
                }}
                yScale={{
                    type: 'linear',
                    min: '0',
                    max: '1',
                    // stacked: true,
                    reverse: false
                }}
                yFormat=">-.2%"
                axisTop={null}
                axisRight={null}
                axisBottom={{
                    orient: 'bottom',
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 0,
                    legend: 'minutes',
                    legendOffset: 36,
                    legendPosition: 'middle'
                }}
                axisLeft={{
                    orient: 'left',
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 0,
                    legend: 'winrate',
                    legendOffset: -40,
                    legendPosition: 'middle'
                }}
                pointSize={10}
                colors={{ scheme: 'category10' }}
                pointColor={{ theme: 'background' }}
                pointBorderWidth={2}
                pointBorderColor={{ from: 'serieColor' }}
                pointLabelYOffset={-12}
                useMesh={true}
                enableArea={true}
                // motionConfig="wobbly"
                legends={[
                    {
                        anchor: 'bottom-right',
                        direction: 'column',
                        justify: false,
                        translateX: 100,
                        translateY: 0,
                        itemsSpacing: 0,
                        itemDirection: 'left-to-right',
                        itemWidth: 80,
                        itemHeight: 20,
                        itemOpacity: 0.75,
                        symbolSize: 12,
                        symbolShape: 'circle',
                        onClick: foo,
                        symbolBorderColor: 'rgba(0, 0, 0, .5)',
                        effects: [
                            {
                                on: 'hover',
                                style: {
                                    itemBackground: 'rgba(0, 0, 0, .03)',
                                    itemOpacity: 1
                                }
                            }
                        ]
                    }
                ]}
            />

        </div>
    )
}




function mapStateToProps(state) {
    return {
        wrLineChart: state.winrateLineChart.wrLineChart,
        isFetching: state.isFetching,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        fetchWrLineChart: () => dispatch(fetchWrLineChart())
    }
}

export default connect(
    mapStateToProps,
    mapDispatchToProps
)(LineChart)

