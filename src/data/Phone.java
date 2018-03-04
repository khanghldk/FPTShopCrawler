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
@Table(name = "Phone", catalog = "FPTShop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Phone.findAll", query = "SELECT p FROM Phone p")
    , @NamedQuery(name = "Phone.findById", query = "SELECT p FROM Phone p WHERE p.id = :id")
    , @NamedQuery(name = "Phone.findByBrand", query = "SELECT p FROM Phone p WHERE p.brand = :brand")
    , @NamedQuery(name = "Phone.findByName", query = "SELECT p FROM Phone p WHERE p.name = :name")
    , @NamedQuery(name = "Phone.findByPrice", query = "SELECT p FROM Phone p WHERE p.price = :price")
    , @NamedQuery(name = "Phone.findByRealPrice", query = "SELECT p FROM Phone p WHERE p.realPrice = :realPrice")
    , @NamedQuery(name = "Phone.findByCurrency", query = "SELECT p FROM Phone p WHERE p.currency = :currency")
    , @NamedQuery(name = "Phone.findByScreen", query = "SELECT p FROM Phone p WHERE p.screen = :screen")
    , @NamedQuery(name = "Phone.findByCell", query = "SELECT p FROM Phone p WHERE p.cell = :cell")
    , @NamedQuery(name = "Phone.findByCpu", query = "SELECT p FROM Phone p WHERE p.cpu = :cpu")
    , @NamedQuery(name = "Phone.findByCamera", query = "SELECT p FROM Phone p WHERE p.camera = :camera")
    , @NamedQuery(name = "Phone.findByRam", query = "SELECT p FROM Phone p WHERE p.ram = :ram")
    , @NamedQuery(name = "Phone.findByOs", query = "SELECT p FROM Phone p WHERE p.os = :os")})
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "brand", length = 20)
    private String brand;
    @Column(name = "name", length = 255)
    private String name;
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
    @Column(name = "cpu", length = 255)
    private String cpu;
    @Column(name = "camera", length = 255)
    private String camera;
    @Column(name = "ram", length = 255)
    private String ram;
    @Column(name = "os", length = 255)
    private String os;

    public Phone() {
    }

    public Phone(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
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
        if (!(object instanceof Phone)) {
            return false;
        }
        Phone other = (Phone) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.name + " " + this.price + " " + this.screen;
    }
    
}
