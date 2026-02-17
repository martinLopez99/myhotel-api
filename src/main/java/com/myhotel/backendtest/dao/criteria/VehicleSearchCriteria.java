package com.myhotel.backendtest.dao.criteria;

/**
 * POJO con filtros opcionales para búsqueda avanzada de vehículos.
 * Todos los campos son opcionales (null = no filtrar por ese campo).
 *
 * @author Martin Lopez
 */
public class VehicleSearchCriteria {

    private String brand;
    private String model;
    private Integer yearFrom;
    private Integer yearTo;
    /** Tipo discriminador: "Car" o "Truck" */
    private String dtype;
    /** Búsqueda parcial por patente (LIKE %value%) */
    private String plateLike;
    /** Si es null, por defecto se filtran los no eliminados */
    private Boolean deleted;

    public VehicleSearchCriteria() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getPlateLike() {
        return plateLike;
    }

    public void setPlateLike(String plateLike) {
        this.plateLike = plateLike;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
