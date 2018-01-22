package com.example.jorge.mytestb2w.model.ProductDetail;

/**
 * Created by jorge on 21/01/2018.
 */

public class ProductDetail {
    private ProductImage product;
    private Installment installment;

    public ProductImage getProduct() {
        return product;
    }

    public void setProduct(ProductImage product) {
        this.product = product;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
    }
}
