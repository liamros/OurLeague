import * as React from 'react';
import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    // hide last border
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));


export default function MaterialRankingTable({ rankings }) {
    var jsx = rankings.map((ranking) => {
        var mov
        if (!ranking.prevPosition)
            mov = "★"
        else if (ranking.position == ranking.prevPosition)
            mov = "="
        else if (ranking.position > ranking.prevPosition)
            mov = "▼"
        else
            mov = "▲"
        return (<StyledTableRow key={ranking.name}>
            <StyledTableCell align='center' component="th" scope="row">
                {ranking.position}
            </StyledTableCell>
            {/* <StyledTableCell align="right">{ranking.position}</StyledTableCell> */}
            <StyledTableCell align="left">{ranking.summonerName}</StyledTableCell>
            <StyledTableCell align="left">{ranking.description}</StyledTableCell>
            <StyledTableCell align="left">{mov}</StyledTableCell>
        </StyledTableRow>)
    })
    return (
        <TableContainer component={Paper}>
            <Table /*sx={{ minWidth: 700 }}*/ aria-label="customized table">
                <TableHead>
                    <TableRow>
                        <StyledTableCell align='center'>Position</StyledTableCell>
                        <StyledTableCell align="left">Name</StyledTableCell>
                        <StyledTableCell align="left">Stat</StyledTableCell>
                        <StyledTableCell align="left">Change</StyledTableCell>
                        {/* <StyledTableCell align="right">Protein&nbsp;(g)</StyledTableCell> */}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {jsx.map((elem) => elem)}
                </TableBody>
            </Table>
        </TableContainer>
    );
}
