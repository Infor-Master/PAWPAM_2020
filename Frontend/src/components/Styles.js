import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles((theme) => ({
    places: {
        width: '100%',
        maxWidth: '80%',
        margin: 'auto',
        backgroundColor: theme.palette.background.paper,
    },
    backdrop: {
        zIndex: theme.zIndex.drawer + 1,
        color: '#fff',
    },
    authTextFileds: {
        '& .MuiTextField-root': {
            margin: theme.spacing(1),
            width: '25ch',
        },
    },
}));