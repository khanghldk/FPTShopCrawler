/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author haleduykhang
 */
@Entity
@Table(name = "Tablet", catalog = "FPTShop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tablet.findAll", query = "SELECT t FROM Tablet t")
    , @NamedQuery(name = "Tablet.findById", query = "SELECT t FROM Tablet t WHERE t.id = :id")
    , @NamedQuery(name = "Tablet.findByName", query = "SELECT t FROM Tablet t WHERE t.name = :name")
    , @NamedQuery(name = "Tablet.findByBrand", query = "SELECT t FROM Tablet t WHERE t.brand = :brand")
    , @NamedQuery(name = "Tablet.findByPrice", query = "SELECT t FROM Tablet t WHERE t.price = :price")
    , @NamedQuery(name = "Tablet.findByRealPrice", query = "SELECT t FROM Tablet t WHERE t.realPrice = :realPrice")
    , @NamedQuery(name = "Tablet.findByCurrency", query = "SELECT t FROM Tablet t WHERE t.currency = :currency")
    , @NamedQuery(name = "Tablet.findByScreen", query = "SELECT t FROM Tablet t WHERE t.screen = :screen")
    , @NamedQuery(name = "Tablet.findByCell", query = "SELECT t FROM Tablet t WHERE t.cell = :cell")
    , @NamedQuery(name = "Tablet.findByCamera", query = "SELECT t FROM Tablet t WHERE t.camera = :camera")
    , @NamedQuery(name = "Tablet.findByRam", query = "SELECT t FROM Tablet t WHERE t.ram = :ram")
    , @NamedQuery(name = "Tablet.findByOs", query = "SELECT t FROM Tablet t WHERE t.os = :os")})
public class Tablet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "brand", length = 20)
    private String brand;
    @Column(name = "price")
    private Integer price;
    @Column(name = "realPrice")
    private Integer realPrice;
    @Column(name = "currency", length = 5)
    private String currency;
    @Column(name = "screen", length = 255)
    private String screen;
    @Column(name = "cell", length = 255)
    private String cell;
    @Column(name = "camera", length = 255)
    private String camera;
    @Column(name = "ram", length = 255)
    private String ram;
    @Column(name = "os", length = 255)
    private String os;

    public Tablet() {
    }

    public Tablet(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Integer realPrice) {
        this.realPrice = realPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tablet)) {
            return false;
        }
        Tablet other = (Tablet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name + " " + this.screen + " " + this.price;
    }
    
}
