import * as React from 'react';
import {FunctionComponent, useEffect, useState} from 'react';
import {OperationPanel} from "./OperationPanel";
import {CategoryDisplay} from "./CategoryDisplay";
import axios, {AxiosResponse} from 'axios';

export interface Category {
    id?: string,
    name: string,
    products?: Product[]
}

export interface Product {
    id?: string,
    name: string,
    price: string,
    categoryId: string
}


const getCategoriesFromApi = (callback: (c: Category[]) => void) => {
    axios.request({
        method: 'get',
        url: 'http://localhost:8123/hse24/categories'
    }).then(function (response: AxiosResponse<Category[]>) {
        callback(response.data);
    });
}

const addCategoryViaApi = (
    callback: (c: Category[]) => void,
    existedCategories: Category[],
    newCategory: Category
) => {
    axios.request({
        method: 'post',
        url: 'http://localhost:8123/hse24/categories',
        data: newCategory
    }).then(function (response: AxiosResponse<Category>) {
        callback([...existedCategories, response.data]);
    });
}

const deleteCategoryViaApi = (
    callback: (c: Category[]) => void,
    existedCategories: Category[],
    id: string
) => {
    axios.request({
        method: 'delete',
        url: 'http://localhost:8123/hse24/categories/' + id,
    }).then(function (response: AxiosResponse<Category>) {
        const newCategories = existedCategories.filter(c => c.id != id);
        callback([...newCategories]);
    });
}

const addProductViaApi = (
    callback: (c: Category[]) => void,
    existedCategories: Category[],
    existedCategory: Category,
    newProduct: Product
) => {
    axios.request({
        method: 'post',
        url: 'http://localhost:8123/hse24/products',
        data: newProduct
    }).then(function (response: AxiosResponse<Product>) {
        existedCategory.products.push(response.data);
        callback([...existedCategories]);
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
            const newCategory: Category = {
                name: cName,
            }
            addCategoryViaApi(setCategoryData,categoryData,newCategory);
        } else {
            alert("Category: " + cName + " exist")
        }
    }

    const deleteCategory = (cName: string) => {
        const existedCategory = categoryData.find(c => c.name == cName);
        if (existedCategory) {
            deleteCategoryViaApi(setCategoryData,categoryData,existedCategory.id);
        } else {
            alert("Category: " + cName + " not exist")
        }
    }

    const addProduct = (cName: string, pName: string, price: string) => {
        const existedCategory = categoryData.find(c => c.name == cName);
        if (existedCategory) {
            const existedProduct = existedCategory.products.find(p => p.name == pName);
            if (!existedProduct) {
                const newProduct: Product = {
                    name: pName,
                    price: price,
                    categoryId: existedCategory.id
                }
                addProductViaApi(setCategoryData,categoryData,existedCategory,newProduct);
            } else {
                alert("Product: " + pName + " exist")
            }
        } else {
            alert("Category: " + cName + " not exist, please add Category first.")
        }
    }

    return (
        <div>
            <OperationPanel addCategory={addCategory} addProduct={addProduct} deleteCategory={deleteCategory}/>
            <CategoryDisplay categories={categoryData} openIds={categoryData.map(c => c.id)} />
        </div>
    );
};