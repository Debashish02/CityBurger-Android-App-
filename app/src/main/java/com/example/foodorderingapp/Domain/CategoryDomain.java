package com.example.foodorderingapp.Domain;

public class CategoryDomain {
    private String tittle;
    private String pic;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public CategoryDomain(String tittle, String pic) {
        this.tittle = tittle;
        this.pic = pic;
    }
}
