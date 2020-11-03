import {FunctionComponent, useState} from "react";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import ChevronRightIcon from "@material-ui/icons/ChevronRight";
import {TreeItem, TreeView} from "@material-ui/lab";
import * as React from "react";
import {makeStyles, Theme, withStyles} from "@material-ui/core/styles";
import {Category} from "./CategoryView";
import {Tooltip, Typography} from "@material-ui/core";

interface CategoryTreeProp {
    categories: Category[],
    openIds: string[]
}

const useStyles = makeStyles({
    root: {
        height: 216,
        flexGrow: 1,
        maxWidth: 130,
    },
});

const HtmlTooltip = withStyles((theme: Theme) => ({
    tooltip: {
        backgroundColor: '#f5f5f9',
        color: 'rgba(0, 0, 0, 0.87)',
        maxWidth: 220,
        fontSize: theme.typography.pxToRem(12),
        border: '1px solid #dadde9',
    },
}))(Tooltip);

export const CategoryDisplay: FunctionComponent<CategoryTreeProp> = ({categories,openIds}) => {
    const classes = useStyles();

    return (
        openIds.length > 0 &&
        <TreeView
            className={classes.root}
            defaultExpanded={openIds}
            defaultCollapseIcon={<ExpandMoreIcon/>}
            defaultExpandIcon={<ChevronRightIcon/>}
            multiSelect>
            {
                categories.map(c =>
                    <TreeItem nodeId={c.id} label={c.name}>
                        {
                            c.products.map(p =>
                                <HtmlTooltip
                                    placement="right"
                                    title={
                                        <React.Fragment>
                                            <Typography color="inherit">Product: {p.name}</Typography>
                                            <Typography color="inherit">Price: {p.price} {p.currency}</Typography>
                                        </React.Fragment>
                                    }
                                >
                                    <TreeItem nodeId={p.id} label={p.name}/>
                                </HtmlTooltip>
                            )
                        }
                    </TreeItem>
                )
            }
        </TreeView>
    );
}