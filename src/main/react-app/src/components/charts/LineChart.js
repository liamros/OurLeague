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
    const [chart, setChart] = useState(data)

    if (data && !chartready) {
        let allExists = false
        data.forEach(element => {
            if (element["id"] === "ALL")
                allExists = true
            element["active"] = true
        });
        if (!allExists)
            data.push({ id: "ALL", data: [] })
        setChart(data)
        setChartready(true)
    }

    const foo = (e) => {
        var a = JSON.parse(JSON.stringify(chart))
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
            a[idx].data = data[idx].data
            a[idx].active = true
        }

        setChart(a)
    }

    return (
        chart && 


            <ResponsiveLine
                data={chart}
                margin={{ top: 10, right: 110, bottom: 50, left: 60 }}
                theme={theme}
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
                    legendPosition: 'middle',
                }}
                axisLeft={{
                    orient: 'left',
                    tickSize: 5,
                    tickPadding: 5,
                    tickRotation: 0,
                    legend: 'winrate',
                    legendOffset: -50,
                    textSize: '30px',
                    legendPosition: 'middle',
                    format: '>-.0%',
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

