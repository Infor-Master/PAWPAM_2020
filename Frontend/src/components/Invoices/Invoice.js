import React from 'react';

import InfoIcon from "@material-ui/icons/Info";
import IconButton from "@material-ui/core/IconButton";
import GridListTile from "@material-ui/core/GridListTile";
import GridListTileBar from "@material-ui/core/GridListTileBar";

import { useStyles } from '../Styles';
import { Link } from 'react-router-dom';


const Invoice = props => {

    const classes = useStyles();

    const invoice = {
        ID: props.id,
        name: props.name,
        image: props.image,
        info: props.info
    }

    return (

        <GridListTile key={invoice.image} style={{ padding: "20px" }}>
            <img style={{  height: "200px", width: "200px", objectFit: "cover" }} className={classes.image} src={invoice.image} alt={invoice.name}/>
            <GridListTileBar
                title={invoice.name}
                subtitle={<span>{invoice.info}</span>}
                actionIcon={
                    <Link 
                        to={{
                            pathname: "/invoice",
                            state: {
                                invoice: invoice
                            }
                        }}
                    >
                        <IconButton className={classes.icon}>
                            <InfoIcon />
                        </IconButton>
                    </Link>
                }
            />
        </GridListTile>
    );
}

export default Invoice;
