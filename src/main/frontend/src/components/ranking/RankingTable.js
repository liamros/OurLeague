import * as React from 'react';


const RankingTable = ({ rankings }) => {

    return (
        <table id='rankings'>
            <tbody>
                {rankings.map((ranking) => {
                    var mov
                    if (!ranking.prevPosition)
                        mov = <td>★</td>
                    else if (ranking.position == ranking.prevPosition)
                        mov = <td>=</td>
                    else if (ranking.position > ranking.prevPosition)
                        mov = <td style={{color: "red"}}>▼ -{ranking.position-ranking.prevPosition}</td>
                    else
                        mov = <td style={{color: "green"}}>▲ +{ranking.prevPosition-ranking.position}</td>
                    return (
                        <tr key={ranking.summonerName}>
                            <td>{ranking.position}</td>
                            <td>{ranking.summonerName}</td>
                            <td>{ranking.description}</td>
                            {mov}
                        </tr>
                    )
                })}
            </tbody>
        </table>
    )
}
export default RankingTable