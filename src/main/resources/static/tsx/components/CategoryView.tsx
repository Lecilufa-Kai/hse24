import * as React from 'react';
import {FunctionComponent, useState} from 'react';

import {makeStyles} from '@material-ui/core/styles';

import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import {TreeItem, TreeView} from "@material-ui/lab";
import {OperationPanel} from "./OperationPanel";


const useStyles = makeStyles({
    root: {
        height: 216,
        flexGrow: 1,
        maxWidth: 400,
    },
});



const categories: Category[] = [
    {
        id: "c1",
        name: "Applications",
        products: [
            {
                id: "p1",
                name: "Calendar"
            },
            {
                id: "p2",
                name: "Chrome"
            },
            {
                id: "p3",
                name: "Webstorm"
            }
        ]
    },
    {
        id: "c2",
        name: "Documents",
        products: [
            {
                id: "p4",
                name: "index.js"
            },
            {
                id: "p5",
                name: "tree-view.js"
            }
        ]
    }
]

export interface Category {
    id: string,
    name: string,
    products: Product[]
}

export interface Product {
    id: string,
    name: string
}

export const CategoryView: FunctionComponent
    = () => {

    const classes = useStyles();
    // const [expanded, setExpanded] = React.useState<string[]>([]);
    // const [selected, setSelected] = React.useState<string[]>([]);
    //
    // const handleToggle = (event: React.ChangeEvent<{}>, nodeIds: string[]) => {
    //     setExpanded(nodeIds);
    // };
    //
    // const handleSelect = (event: React.ChangeEvent<{}>, nodeIds: string[]) => {
    //     setSelected(nodeIds);
    // };

    const [categoryData, setCategoryData] = useState<Category[]>(categories);

    const [m, setM] = useState<string>('0');

    const addCategory = (c_name:string) => {
        let newCategory:Category = {
            id: "c3",
            name: c_name,
            products: [
                {
                    id: "p11",
                    name: "Calendar"
                },
                {
                    id: "p22",
                    name: "Chrome"
                },
                {
                    id: "p33",
                    name: "Webstorm"
                }
            ]
        }

        setCategoryData([...categoryData,newCategory]);
    }


    return (
        <div>
            <OperationPanel addData={addCategory} />

            <TreeView
                className={classes.root}
                // defaultExpanded={categories.map(c => c.id)}
                defaultCollapseIcon={<ExpandMoreIcon/>}
                defaultExpandIcon={<ChevronRightIcon/>}
                multiSelect>
                {
                    categoryData.map(c =>
                        <TreeItem nodeId={c.id} label={c.name}>
                            {c.products.map(p => <TreeItem nodeId={p.id} label={p.name}/>)}
                        </TreeItem>
                    )
                }
            </TreeView>
        </div>
    );
};