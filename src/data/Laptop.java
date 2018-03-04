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
@Table(name = "Laptop", catalog = "FPTShop", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Laptop.findAll", query = "SELECT l FROM Laptop l")
    , @NamedQuery(name = "Laptop.findById", query = "SELECT l FROM Laptop l WHERE l.id = :id")
    , @NamedQuery(name = "Laptop.findByName", query = "SELECT l FROM Laptop l WHERE l.name = :name")
    , @NamedQuery(name = "Laptop.findByBrand", query = "SELECT l FROM Laptop l WHERE l.brand = :brand")
    , @NamedQuery(name = "Laptop.findByPrice", query = "SELECT l FROM Laptop l WHERE l.price = :price")
    , @NamedQuery(name = "Laptop.findByRealPrice", query = "SELECT l FROM Laptop l WHERE l.realPrice = :realPrice")
    , @NamedQuery(name = "Laptop.findByCurrency", query = "SELECT l FROM Laptop l WHERE l.currency = :currency")
    , @NamedQuery(name = "Laptop.findByScreen", query = "SELECT l FROM Laptop l WHERE l.screen = :screen")
    , @NamedQuery(name = "Laptop.findByRam", query = "SELECT l FROM Laptop l WHERE l.ram = :ram")
    , @NamedQuery(name = "Laptop.findByOs", query = "SELECT l FROM Laptop l WHERE l.os = :os")
    , @NamedQuery(name = "Laptop.findByCpu", query = "SELECT l FROM Laptop l WHERE l.cpu = :cpu")
    , @NamedQuery(name = "Laptop.findByVga", query = "SELECT l FROM Laptop l WHERE l.vga = :vga")
    , @NamedQuery(name = "Laptop.findByWeight", query = "SELECT l FROM Laptop l WHERE l.weight = :weight")
    , @NamedQuery(name = "Laptop.findByCamera", query = "SELECT l FROM Laptop l WHERE l.camera = :camera")
    , @NamedQuery(name = "Laptop.findByCell", query = "SELECT l FROM Laptop l WHERE l.cell = :cell")})
public class Laptop implements Serializable {

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
    @Column(name = "ram", length = 255)
    private String ram;
    @Column(name = "os", length = 255)
    private String os;
    @Column(name = "cpu", length = 255)
    private String cpu;
    @Column(name = "vga", length = 255)
    private String vga;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight", precision = 22)
    private Double weight;
    @Column(name = "camera", length = 255)
    private String camera;
    @Column(name = "cell", length = 255)
    private String cell;

    public Laptop() {
    }

    public Laptop(Integer id) {
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

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
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
        if (!(object instanceof Laptop)) {
            return false;
        }
        Laptop other = (Laptop) object;
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
