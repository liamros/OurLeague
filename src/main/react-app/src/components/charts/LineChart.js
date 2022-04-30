import { ResponsiveLine } from '@nivo/line';
import React, { useState } from 'react';

// make sure parent container have a defined height when using
// responsive component, otherwise height will be 0 and
// no chart will be rendered.
// website examples showcase many properties,
// you'll often use just a few of them.
const LineChart = ({ data }) => {

    const [chartready, setChartready] = useState(false)
    const [allpressed, setAllpressed] = useState(false)
    var [charts, setCharts] = useState({})

    if (data && !chartready) {
        let allExists = false
        data.charts.forEach(element => {
            if (element["id"] === "ALL")
                allExists = true
            element["active"] = true
        });
        if (!allExists)
            data.charts.push({ id: "ALL", data: [] })
        setCharts(data.charts)
        setChartready(true)
    }

    const foo = (e) => {
        var a = JSON.parse(JSON.stringify(charts))
        if (e.id === "ALL") {
            if (!allpressed) {
                a.forEach((elem) => {
                    elem.data.forEach((d) => d.y = 0)
                    elem.active = false
                })
            } else {
                a = JSON.parse(JSON.stringify(data))
            }
            setAllpressed(!allpressed)
            setCharts(a)
            return
        }
        var idx = 0
        while (charts[idx].id != e.id) {
            idx++
        }
        if (a[idx].active) {
            a[idx].data.forEach((d) => d.y = 0)
            a[idx].active = false
        }
        else {
            a[idx].data = data[idx].data
            a[idx].active = true
        }

        setCharts(a)
    }

    return (
        charts && 


            <ResponsiveLine
                data={charts}
                margin={{ top: 10, right: 110, bottom: 50, left: 60 }}
                theme={theme}
                xScale={{
                    type: 'point',
                }}
                yScale={{
                    type: 'linear',
                    min: data.minY,
                    max: data.maxY,
                    // stacked: true,
                    reverse: false
                }}
                yFormat={data.format}
                axisTop={null}
                axisRight={null}
                axisBottom={{
                    orient: 'bottom',
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 0,
                    legend: data.xUnit,
                    legendOffset: 36,
                    legendPosition: 'middle',
                }}
                axisLeft={{
                    orient: 'left',
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 0,
                    legend: data.yUnit,
                    legendOffset: -50,
                    textSize: '30px',
                    legendPosition: 'middle',
                    format: data.format,
                }}
                pointSize={10}
                crosshairType="cross"
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


    )
}



const theme = {
    /*background: "rgb(6, 28, 37)",*/
    textColor: "rgb(208, 168, 92)",
    grid: {
        line:
        {
            stroke: "rgb(208, 168, 92)"
        }
    },
    axis: {
        legend: {
            text: {
                fontSize: 18,
                fontWeight: 'bold'
            }
        }
    },
    tooltip: {
        container: {
            background: "rgba(6, 28, 37)",
            color: "rgb(208, 168, 92)"
        },
    },
}

export default LineChart

