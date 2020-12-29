import { makeStyles } from '@material-ui/core/styles';

export const useStyles = makeStyles((theme) => ({
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
    root: {
        display: "flex",
        flexWrap: "wrap",
        justifyContent: "space-around",
        overflow: "none",
    },
    gridList: {
        height: "auto"
    },
    icon: {
        color: "rgba(255, 255, 255, 0.54)"
    },
    image: {
        "&:hover": {
          opacity: 0.25
        }

    }
}));