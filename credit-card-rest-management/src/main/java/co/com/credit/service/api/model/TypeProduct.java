package co.com.credit.service.api.model;


import io.swagger.annotations.ApiModel;

@ApiModel(value ="TYPE_PRODUCT")
public enum TypeProduct {
    ACCOUNT("980234"),
    DEPOSIT("535839"),
    CREDIT_CARD("893453"),
    DEBIT_CARD("763492"),
    LOAN("982402");

    private String id;

    TypeProduct(String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }

}
