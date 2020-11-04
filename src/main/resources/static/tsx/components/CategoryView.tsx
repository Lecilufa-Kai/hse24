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
    currency: string,
    categoryId: string
}

export interface CurrencyInfo{
    rates: {[key:string]:number}
}

const getCategoriesFromApi = (callback: (categories: Category[]) => void) => {
    axios.request({
        method: 'get',
        url: 'http://localhost:8123/hse24/categories'
    }).then(function (response: AxiosResponse<Category[]>) {
        callback(response.data);
    }).catch(reason => {
        alert(reason);
    });


}

let ratesMap: {[key:string]:number} = {};

const getCurrencyInfo = (
    callback: (currencyNames: string[]) => void
) => {
    axios.request({
        method: 'get',
        url: 'http://data.fixer.io/api/latest?access_key=bbac5b49d9915c7e9c7d64ed15618d79',
    }).then(function (response: AxiosResponse<CurrencyInfo>) {
        const names:string[] = [];
        for(let key in response.data.rates){
            names.push(key);
        }
        ratesMap = response.data.rates;
        callback(names);
    }).catch(reason => {
        alert(reason);
    });
}

const addCategoryViaApi = (
    callback: (categories: Category[]) => void,
    existedCategories: Category[],
    newCategory: Category
) => {
    axios.request({
        method: 'post',
        url: 'http://localhost:8123/hse24/categories',
        data: newCategory
    }).then(function (response: AxiosResponse<Category>) {
        callback([...existedCategories, response.data]);
    }).catch(reason => {
        alert(reason);
    });
}

const deleteCategoryViaApi = (
    callback: (categories: Category[]) => void,
    existedCategories: Category[],
    id: string
) => {
    axios.request({
        method: 'delete',
        url: 'http://localhost:8123/hse24/categories/' + id,
    }).then(function (response: AxiosResponse<Category>) {
        const newCategories = existedCategories.filter(c => c.id != id);
        callback([...newCategories]);
    }).catch(reason => {
        alert(reason);
    });
}

const addProductViaApi = (
    callback: (categories: Category[]) => void,
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
    }).catch(reason => {
        alert(reason);
    });
}

const deleteProductViaApi = (
    callback: (categories: Category[]) => void,
    existedCategories: Category[],
    existedCategory: Category,
    productId: string
) => {
    axios.request({
        method: 'delete',
        url: 'http://localhost:8123/hse24/products/' + productId,
    }).then(function (response: AxiosResponse<Product>) {
        const newProducts = existedCategory.products.filter(product => product.id != productId);
        existedCategory.products = [...newProducts];
        callback([...existedCategories]);
    }).catch(reason => {
        alert(reason);
    });
}

const numberRegex = /^[+-]?\d+(\.\d+)?$/;

export const CategoryView: FunctionComponent
    = () => {

    const [categoryData, setCategoryData] = useState<Category[]>([]);
    const [currencyName, setCurrencyName] = useState<string[]>([]);
    const [loaded, setLoaded] = useState<boolean>(false);

    useEffect(() => {
        if (!loaded) {
            getCategoriesFromApi(setCategoryData);
            getCurrencyInfo(setCurrencyName);
            setLoaded(true);
        }
    })

    const addCategory = (categoryName: string) => {

        if(categoryName.trim() == ''){
            alert("Category can't be empty")
            return;
        }

        const existedCategory = categoryData.find(c => c.name == categoryName);
        if (!existedCategory) {
            const newCategory: Category = {
                name: categoryName,
            }
            addCategoryViaApi(setCategoryData, categoryData, newCategory);
        } else {
            alert("Category: " + categoryName + " exist")
        }
    }

    const deleteCategory = (categoryName: string) => {
        const existedCategory = categoryData.find(c => c.name == categoryName);
        if (existedCategory) {
            deleteCategoryViaApi(setCategoryData, categoryData, existedCategory.id);
        } else {
            alert("Category: " + categoryName + " not exist")
        }
    }

    const addProduct = (categoryName: string, productName: string, price: string, currency: string) => {

        if(categoryName.trim() == '' || productName.trim() == '' || price.trim() == ''){
            alert("Category or Product or Price can't be empty")
            return;
        }

        if(!numberRegex.test(price)){
            alert("Price must be a number")
            return;
        }

        const existedCategory = categoryData.find(c => c.name == categoryName);
        if (existedCategory) {
            const existedProduct = existedCategory.products.find(p => p.name == productName);
            if (!existedProduct) {

                let finalPrice = Number(price) * ratesMap[currency];

                const newProduct: Product = {
                    name: productName,
                    price: finalPrice.toString(),
                    currency: currency,
                    categoryId: existedCategory.id
                }
                addProductViaApi(setCategoryData, categoryData, existedCategory, newProduct);
            } else {
                alert("Product: " + productName + " exist")
            }
        } else {
            alert("Category not exist, please add Category first.")
        }
    }

    const deleteProduct = (categoryName: string, productName: string) => {
        const existedCategory = categoryData.find(c => c.name == categoryName);
        if (existedCategory) {
            const existedProduct = existedCategory.products.find(p => p.name == productName);
            if (existedProduct) {
                deleteProductViaApi(setCategoryData, categoryData, existedCategory, existedProduct.id);
            }
        }
    }

    return (
        <div>
            <OperationPanel addCategory={addCategory}
                            addProduct={addProduct}
                            deleteCategory={deleteCategory}
                            deleteProduct={deleteProduct}
                            currencyName={currencyName}
            />
            <CategoryDisplay categories={categoryData} openIds={categoryData.map(c => c.id)}/>
        </div>
    );
};