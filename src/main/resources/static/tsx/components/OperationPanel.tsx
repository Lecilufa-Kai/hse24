import * as React from 'react';
import {FunctionComponent, useState} from 'react';
import {createStyles, Fab, TextField, Theme} from "@material-ui/core";
import {Add, Edit} from "@material-ui/icons";
import makeStyles from "@material-ui/core/styles/makeStyles";
import {Category} from "./CategoryView";

export interface OperationPanelProp {
    addData:(c_name:string)=>void
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        iconStyle: {
            margin: theme.spacing(1),
        },
        extendedIcon: {
            marginRight: theme.spacing(1),
        },
    }),
);

export const OperationPanel: FunctionComponent<OperationPanelProp>
    = ({
           addData
       }) => {
    const classes = useStyles();

    const [category, setCategory] = useState('')


    return (
        <div>
            <TextField id="category_name" label="Category" onChange={event => setCategory(event.target.value)} />
            <Fab size="small" color="primary" aria-label="add category" className={classes.iconStyle}
                 onClick={() => addData(category)}>
                <Edit/>
            </Fab>

            <TextField id="product_name" label="Product"/>
            <Fab size="small" color="primary" aria-label="add product" className={classes.iconStyle}>
                <Edit/>
            </Fab>
            <br/><br/>
        </div>
    );
};