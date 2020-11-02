import * as React from 'react';
import {FunctionComponent, useState} from 'react';
import {createStyles, Fab, TextField, Theme} from "@material-ui/core";
import {Add} from "@material-ui/icons";
import makeStyles from "@material-ui/core/styles/makeStyles";
import RemoveIcon from '@material-ui/icons/Remove';

export interface OperationPanelProp {
    addCategory: (cName: string) => void,
    deleteCategory: (cName: string) => void,
    addProduct: (cName: string, pName: string,price:string) => void
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
           addCategory,
           deleteCategory,
           addProduct
       }) => {
    const classes = useStyles();

    const [category, setCategory] = useState('')
    const [productName, setProductName] = useState('')
    const [productPrice, setProductPrice] = useState('')

    return (
        <div>
            <TextField id="category_name" label="Category" onChange={event => setCategory(event.target.value)}/>
            <Fab size="small" color="primary" aria-label="add category" className={classes.iconStyle}
                 onClick={() => addCategory(category)}>
                <Add/>
            </Fab>
            <Fab size="small" color="primary" aria-label="remove category" className={classes.iconStyle}
                 onClick={() => deleteCategory(category)}>
                <RemoveIcon style={{color:"white"}}/>
            </Fab>

            <TextField id="product_name" label="Product Name" onChange={event => setProductName(event.target.value)}/>
            &nbsp;&nbsp;
            <TextField id="product_price" label="Product Price" onChange={event => setProductPrice(event.target.value)}/>
            <Fab size="small" color="primary" aria-label="add product" className={classes.iconStyle}
                 onClick={() => addProduct(category,productName,productPrice)} >
                <Add/>
            </Fab>
            <Fab size="small" color="primary" aria-label="remove product" className={classes.iconStyle}
                 onClick={() => { }}>
                <RemoveIcon style={{color:"white"}}/>
            </Fab>
            <br/><br/>
        </div>
    );
};