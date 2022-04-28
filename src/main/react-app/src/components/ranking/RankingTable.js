import * as React from 'react';


const RankingTable = ({ rankings }) => {

    return ( 

        rankings.map((ranking) => {
            var mov
            if (!ranking.prevPosition)
                mov = "★"
            else if (ranking.position == ranking.prevPosition)
                mov = "="
            else if (ranking.position > ranking.prevPosition)
                mov = "▼"
            else
                mov = "▲"
            return (
                <tr key={ranking.summonerName}>
                    <td>{ranking.position}</td>
                    <td>{ranking.summonerName}</td>
                    <td>{ranking.description}</td>
                    <td>{mov}</td>
                </tr>
            )

        })

    )
}
export default RankingTable