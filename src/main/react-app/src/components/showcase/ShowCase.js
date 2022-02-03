import ShowCaseDetail from "./ShowCaseDetail"
import * as React from 'react';
import { Container } from "@mui/material";

class ShowCase extends React.Component {

    constructor(props) {
        super(props)
    }

    render() {

        var list = [{ statName: "KDA", summonerName: "Shakobi", value: 2.7, description: "kekw" }, { statName: "WinRate", summonerName: "Cavendish31", value: 55, description: "kekw" }]
        return (
            <Container>{
                list.map(element => {
                    return <ShowCaseDetail key={element.statName} stats={element} />
                })
            }
            </Container>

        )


    }
}

export default ShowCase