import * as React from 'react';
import {FunctionComponent, useState} from 'react';
import {createStyles, Fab, MenuItem, TextField, Theme} from "@material-ui/core";
import {Add} from "@material-ui/icons";
import makeStyles from "@material-ui/core/styles/makeStyles";
import RemoveIcon from '@material-ui/icons/Remove';
import Select from '@material-ui/core/Select';

export interface OperationPanelProp {
    addCategory: (categoryName: string) => void,
    deleteCategory: (categoryName: string) => void,
    addProduct: (categoryName: string, productName: string,price:string,currency:string) => void,
    deleteProduct: (categoryName: string, productName: string) => void,
    currencyName: string[]
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        iconStyle: {
            margin: theme.spacing(1),
        },
        extendedIcon: {
            marginRight: theme.spacing(1),
        },
        selectStyle:{
            position:'relative',
            top: '5px'
        }
    }),
);

export const OperationPanel: FunctionComponent<OperationPanelProp>
    = ({
           addCategory,
           deleteCategory,
           addProduct,
           deleteProduct,
           currencyName
       }) => {
    const classes = useStyles();

    const [category, setCategory] = useState('')
    const [productName, setProductName] = useState('')
    const [productPrice, setProductPrice] = useState('')
    const [currency, setCurrency] = useState('EUR')

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
            &nbsp;&nbsp;
            <Select
                className={classes.selectStyle}
                labelId="currency-select-label"
                id="currency-select"
                value={currency}
                onChange={event => setCurrency(event.target.value as string)}
            >
                {
                    currencyName.map(cn =>
                        <MenuItem value={cn}>{cn}</MenuItem>
                    )
                }
            </Select>
            <Fab size="small" color="primary" aria-label="add product" className={classes.iconStyle}
                 onClick={() => addProduct(category,productName,productPrice,currency)} >
                <Add/>
            </Fab>
            <Fab size="small" color="primary" aria-label="remove product" className={classes.iconStyle}
                 onClick={() => {deleteProduct(category,productName)}}>
                <RemoveIcon style={{color:"white"}}/>
            </Fab>
            <br/><br/>
        </div>
    );
};