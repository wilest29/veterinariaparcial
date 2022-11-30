package co.edu.uts.veterinaria.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "flights")
public class Flight
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", nullable = false)
    private Integer id;
    @Column(name = "flight_source", nullable = false)
    private String source;
    @Column(name = "flight_destiny", nullable = false)
    private String destiny;
    @Column(name = "flight_airline", nullable = false)
    private String airline;
    @Column(name = "flight_date", nullable = false)
    private Date date;
    @Column(name = "flight_time", nullable = false)
    private String time;
    @Column(name = "flight_type", nullable = false)
    private String type;
    @Column(name = "flight_price", nullable = false)
    private Integer price;
    @ManyToOne(cascade = CascadeType.ALL)
    private Reservation reservation;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getDestiny() {
        return destiny;
    }
    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }
    public String getAirline() {
        return airline;
    }
    public void setAirline(String airline) {
        this.airline = airline;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Reservation getReservation() {
        return reservation;
    }
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString()
    {
        return "Flight{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", destiny='" + destiny + '\'' +
                ", airline='" + airline + '\'' +
                ", date=" + date +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", reservation=" + reservation +
                '}';
    }
}