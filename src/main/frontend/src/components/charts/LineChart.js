import { ResponsiveLine } from '@nivo/line';
import React, { useState } from 'react';

// make sure parent container have a defined height when using
// responsive component, otherwise height will be 0 and
// no chart will be rendered.
// website examples showcase many properties,
// you'll often use just a few of them.
const LineChart = ({ data, activeCharts, callback }) => {

    const [charts, setCharts] = useState(data["charts"])

    React.useEffect(() => {
        data.charts.map((item, idx) => {
            item.color = colors[idx%colors.length]
        })
        var a = JSON.parse(JSON.stringify(data.charts))
        setCharts(a)
        a.forEach(elem => {
            if (!activeCharts[elem.id])
                elem.data.forEach((d) => d.y = 0)
        });
        return () => {
            callback(activeCharts)
        }
    }, [data])

    const onClick = (e) => {
        var a = JSON.parse(JSON.stringify(charts))
        if (e.id === "ALL") {
            if (activeCharts["ALL"]) {
                a.forEach((elem) => {
                    elem.data.forEach((d) => d.y = 0)
                    activeCharts[elem.id] = false
                })
                activeCharts["ALL"] = false
            } else {
                a = JSON.parse(JSON.stringify(data.charts))
                a.forEach((elem) => activeCharts[elem.id] = true)
                activeCharts["ALL"] = true
            }
            setCharts(a)
            return
        }
        var idx = 0
        while (charts[idx].id != e.id) {
            idx++
        }
        if (activeCharts[e.id]) {
            a[idx].data.forEach((d) => d.y = 0)
            activeCharts[e.id] = false
        }
        else {
            a[idx].data = data.charts[idx].data
            activeCharts[e.id] = true
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
                // min: data.minY,
                // max: data.maxY,
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
            colors={colors}
            pointColor={{ theme: 'background' }}
            pointBorderWidth={2}
            pointBorderColor={{ from: 'serieColor' }}
            pointLabelYOffset={-12}
            useMesh={true}
            enableArea={true}
            curve="monotoneX"
            motionConfig="gentle"
            legends={[
                {
                    data: charts.map((item, _) => {
                        var color = activeCharts[item.id] && item.data.length > 0 ? item.color : "transparent"
                        return {
                        id: item.id,
                        label: item.id,
                        color: color,
                        style : {
                            itemBackground: 'rgba(208, 168, 92, .03)',
                            itemOpacity: 1
                        }
                      }
                    }).reverse(),
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
                    onClick: onClick,
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

// const colors = ["#b0c4de","#add8e6","#87ceeb","#6495ed","#1e90ff","#4169e1","#0067a5","#004f98","#00308f","#002e63"]

const colors = ["#fff100","#ff8c00","#e81123","#68217a","#00188f","#00bcf2","#00b294","#009e49","#ec008c","#FFFF"]


const theme = {
    /*background: "rgb(6, 28, 37)",*/
    textColor: "rgb(208, 168, 92)",
    fontFamily: "Nunito",
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

