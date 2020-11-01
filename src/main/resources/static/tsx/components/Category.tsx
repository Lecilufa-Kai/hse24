import * as React from 'react';
import {FunctionComponent} from 'react';

import {makeStyles} from '@material-ui/core/styles';

import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import {TreeItem, TreeView} from "@material-ui/lab";


const useStyles = makeStyles({
    root: {
        height: 216,
        flexGrow: 1,
        maxWidth: 400,
    },
});

export const Category: FunctionComponent = () => {

    const classes = useStyles();
    const [expanded, setExpanded] = React.useState<string[]>([]);
    const [selected, setSelected] = React.useState<string[]>([]);

    const handleToggle = (event: React.ChangeEvent<{}>, nodeIds: string[]) => {
        setExpanded(nodeIds);
    };

    const handleSelect = (event: React.ChangeEvent<{}>, nodeIds: string[]) => {
        setSelected(nodeIds);
    };

    return (
        <TreeView
            className={classes.root}
            defaultCollapseIcon={<ExpandMoreIcon/>}
            defaultExpandIcon={<ChevronRightIcon/>}
            multiSelect
        >
            <TreeItem nodeId="1" label="Applications">
                <TreeItem nodeId="2" label="Calendar"/>
                <TreeItem nodeId="3" label="Chrome"/>
                <TreeItem nodeId="4" label="Webstorm"/>
            </TreeItem>
            <TreeItem nodeId="5" label="Documents">
                <TreeItem nodeId="8" label="index.js"/>
                <TreeItem nodeId="9" label="tree-view.js"/>
            </TreeItem>
        </TreeView>
    );
};