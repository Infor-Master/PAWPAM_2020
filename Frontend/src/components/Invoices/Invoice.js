import React from 'react';

import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';

function ListItemLink(props) {
    return <ListItem button component="a" {...props} />;
}

const Invoice = props => {

    const invoice = {
        ID: props.id,
        name: props.name,
        image: props.image,
    }

    return (
        <div>
            <ListItemLink>
                <ListItemText onClick={(event) => props.selectPlace(event, invoice)}><strong>{props.name}</strong></ListItemText>
            </ListItemLink>
        </div >
    );
}

export default Invoice;