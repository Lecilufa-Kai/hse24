import * as React from 'react';
import {FunctionComponent, useEffect, useState} from 'react';
import {OperationPanel} from "./OperationPanel";
import {CategoryDisplay} from "./CategoryDisplay";
import axios, {AxiosResponse} from 'axios';

export interface Category {
    id: string,
    name: string,
    products: Product[]
}

export interface Product {
    id: string,
    name: string
}


const getCategoriesFromApi = (callback: (c: Category[]) => void) => {

    axios.request({
        method: 'get',
        url: 'http://localhost:8123/exchange-rate-service/testData'
    }).then(function (response: AxiosResponse<Category[]>) {
        callback(response.data);
    });
}


export const CategoryView: FunctionComponent
    = () => {

    const [categoryData, setCategoryData] = useState<Category[]>([]);
    const [loaded, setLoaded] = useState<boolean>(false);

    useEffect(() => {
        if(!loaded){
            getCategoriesFromApi(setCategoryData);
            setLoaded(true);
        }
    })

    const addCategory = (cName: string) => {

        const existedCategory = categoryData.find(c => c.name == cName);
        if (!existedCategory) {
            let newCategory: Category = {
                id: Math.random().toString(),
                name: cName,
                products: []
            }
            setCategoryData([...categoryData, newCategory]);
        } else {
            alert("Category: " + cName + " exists")
        }
    }

    const addProduct = (cName: string, pName: string) => {
        const existedCategory = categoryData.find(c => c.name == cName);
        if (existedCategory) {
            const existedProduct = existedCategory.products.find(p => p.name == pName);
            if (!existedProduct) {
                let newProduct: Product = {
                    id: Math.random().toString(),
                    name: pName
                }
                existedCategory.products.push(newProduct);
                setCategoryData([...categoryData]);
            } else {
                alert("Product: " + pName + " exists")
            }
        } else {
            alert("Category: " + cName + " not exists, please add Category first.")
        }
    }

    return (
        <div>
            <OperationPanel addCategory={addCategory} addProduct={addProduct}/>
            <CategoryDisplay categories={categoryData} openIds={categoryData.map(c => c.id)} />
        </div>
    );
};